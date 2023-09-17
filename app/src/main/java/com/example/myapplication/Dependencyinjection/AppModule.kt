package com.example.myapplication.Dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.data.model.api.RetrofitAPI
import com.example.myapplication.data.model.repo.ArtRepository
import com.example.myapplication.data.model.repo.ArtRepositoryinterface
import com.example.myapplication.roomdb.ArtDao
import com.example.myapplication.roomdb.ArtDatabase
import com.example.myapplication.domain.util.Util.Base_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase): ArtDao {
        return database.artDao()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(Base_URL).build()
             .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    @Named("normal")
    fun injectNormalRepo(dao: ArtDao, api: RetrofitAPI) =
        ArtRepository(dao, api) as ArtRepositoryinterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}