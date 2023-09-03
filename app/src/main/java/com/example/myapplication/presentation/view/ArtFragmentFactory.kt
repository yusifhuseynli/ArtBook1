package com.example.myapplication.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.myapplication.presentation.view.Adapter.ArtRecyclerAdapter
import com.example.myapplication.presentation.view.Adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide:RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
    ):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {


        return when(className){
            ArtFragment::class.java.name-> ArtFragment(artRecyclerAdapter)
            ArtDetailesFragment::class.java.name-> ArtDetailesFragment(glide)
            ImageApiFragment::class.java.name-> ImageApiFragment(imageRecyclerAdapter)

            else ->    return super.instantiate(classLoader, className)
        }


        return super.instantiate(classLoader, className)
    }

}