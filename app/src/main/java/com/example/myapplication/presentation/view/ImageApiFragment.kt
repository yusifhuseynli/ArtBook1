package com.example.myapplication.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.presentation.view.Adapter.ImageRecyclerAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentImageApiBinding
import com.example.myapplication.domain.util.Status
import com.example.myapplication.presentation.view.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter

) : Fragment(R.layout.fragment_image_api) {
    lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentImageApiBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding
        var job: Job? = null

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())

                    }
                }
            }

        }
        subscribeToObserves()

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    fun subscribeToObserves() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentBinding?.progresssBar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    fragmentBinding?.progresssBar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentBinding?.progresssBar?.visibility = View.VISIBLE
                }
            }
        }
    }
}