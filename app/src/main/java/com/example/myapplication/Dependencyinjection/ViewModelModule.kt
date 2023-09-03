package com.example.myapplication.Dependencyinjection

import com.example.myapplication.data.model.repo.ArtRepository
import com.example.myapplication.data.model.repo.ArtRepositoryinterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface ViewModelModule {

    @Binds
    @ViewModelScoped
    @Named("artRepo")
    fun provideArtRepository(repo: ArtRepository): ArtRepositoryinterface
}