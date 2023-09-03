package com.example.myapplication.presentation.view.viewmodel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSingInBinding
import com.example.myapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentSplashBinding.inflate(inflater,container,false)

        val handlerr=Handler(Looper.getMainLooper())
        handlerr.postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSingInFragment())
        },3000)


        return binding.root
    }


}