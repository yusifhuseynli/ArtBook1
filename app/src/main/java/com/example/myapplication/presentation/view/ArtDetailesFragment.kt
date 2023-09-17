package com.example.myapplication.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentArtDetalsBinding
import com.example.myapplication.domain.util.Status

import com.example.myapplication.presentation.view.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailesFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_detals) {
    private lateinit var viewModel: ArtViewModel


    private var fragmentBinding: FragmentArtDetalsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        subscribeToObservers()

        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtDetalsBinding.bind(view)
        fragmentBinding = binding
        binding.artimageView2.setOnClickListener {
            findNavController().navigate(ArtDetailesFragmentDirections.actionArtDetailesFragmentToImageApiFragment())

        }

        binding.Savebutton.setOnClickListener {
            viewModel.makeArt(
                binding.nameText.text.toString(),
                binding.artisText.text.toString(),
                binding.yearText.text.toString()
            )

        }

    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artimageView2)
            }
        })
        viewModel.insertArtmessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().navigate(ArtDetailesFragmentDirections.actionArtDetailesFragmentToArtFragment())
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}