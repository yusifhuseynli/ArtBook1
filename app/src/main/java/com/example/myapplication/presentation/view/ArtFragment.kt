package com.example.myapplication.presentation.view


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.presentation.view.Adapter.ArtRecyclerAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentArtsBinding
import com.example.myapplication.presentation.view.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
):Fragment(R.layout.fragment_arts) {
    private var fragmentBinding:FragmentArtsBinding?=null

    lateinit var viewModel: ArtViewModel

    private val swipeCallBack=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition=viewHolder.layoutPosition
            val selectedArt=artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)


        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding=FragmentArtsBinding.bind(view)
        fragmentBinding=binding

        subscribeTo0bserver()
        binding.recyclerViewArt.adapter=artRecyclerAdapter
        binding.recyclerViewArt.layoutManager=LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)





    }
    private fun subscribeTo0bserver(){
        viewModel.artList.observe(viewLifecycleOwner,
            Observer{
            artRecyclerAdapter.arts=it
        })
    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()
    }
}