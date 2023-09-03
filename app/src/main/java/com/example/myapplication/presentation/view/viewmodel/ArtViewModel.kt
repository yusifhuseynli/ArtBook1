package com.example.myapplication.presentation.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ImageResponse
import com.example.myapplication.data.model.repo.ArtRepositoryinterface
import com.example.myapplication.roomdb.Art
import com.example.myapplication.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ArtViewModel @Inject constructor(
    @Named("artRepo") private val repository: ArtRepositoryinterface
) :ViewModel() {//

    //Art Fragment
    val artList=repository.grtArt()


    //Image API Fragment
    private val images=MutableLiveData<Resource<ImageResponse>>()
    val imageList:LiveData<Resource<ImageResponse>>
    get() = images
    private val selectedImage=MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
    get() = selectedImage

    //Art Detailes Fragment
    private var insertArtmsg=MutableLiveData<Resource<Art>>()
    val insertArtmessage:LiveData<Resource<Art>>
    get() = insertArtmsg

    fun resetInsertArtMsg(){
        insertArtmsg=MutableLiveData<Resource<Art>>()

    }

    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art)=viewModelScope.launch{
        repository.deleteArt(art)

    }
    fun insertArt(art:Art)=viewModelScope.launch {
        repository.insertArt(art)

    }
    fun makeArt(name: String,artistName:String,year:String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtmsg.postValue(Resource.error("Enter name,artist,year",null))
            return
        }
        val yearInt=try {
            year.toInt()
        }catch (e:Exception){
            insertArtmsg.postValue(Resource.error("Year should be number",null))
            return
        }
        val art=Art(name,artistName,yearInt,selectedImage.value?:"")
        insertArt(art)
        setSelectedImage("")
        insertArtmsg.postValue(Resource.success(art))
    }
    fun searchForImage(searchString: String){
        if (searchString.isEmpty()){
            return
        }
        images.value= Resource.loading(null)
        viewModelScope.launch {
            val response=repository.searchImage(searchString)
            images.value=response
        }
    }


   }