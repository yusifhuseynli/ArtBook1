package com.example.myapplication.data.model.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.myapplication.data.model.api.RetrofitAPI
import com.example.myapplication.data.model.ImageResponse
import com.example.myapplication.roomdb.Art
import com.example.myapplication.roomdb.ArtDao
import com.example.myapplication.domain.util.Resource
import javax.inject.Inject
import kotlin.math.log

class ArtRepository @Inject constructor(
    private val artDao:ArtDao,
    private val retrofitAPI: RetrofitAPI

                                        ): ArtRepositoryinterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun grtArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

   override suspend fun searchImage(imageString:String): Resource<ImageResponse> {
        return try {

            val response=retrofitAPI.imageSearch(imageString)
            Log.e("searc repo", "searchImage:  ")

            if (response.isSuccessful)
            {
              response.body()?.let {

                  return@let Resource.success(it)
              }  ?: Resource.error("Error",null)
            } else{
                Resource.error("ERROR",null)
            }
        } catch (e:Exception){
            Log.d("my message", "searchImage: ${e.message} ")
            Resource.error("No INTERNET",null)
        }
    }
}