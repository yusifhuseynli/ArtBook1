package com.example.myapplication.data.model.repo

import androidx.lifecycle.LiveData
import com.example.myapplication.data.model.ImageResponse
import com.example.myapplication.roomdb.Art
import com.example.myapplication.domain.util.Resource

interface ArtRepositoryinterface {
    suspend fun insertArt(art:Art)
    suspend fun deleteArt(art: Art)
    fun grtArt():LiveData<List<Art>>
    suspend fun searchImage(imageString: String): Resource<ImageResponse>

}