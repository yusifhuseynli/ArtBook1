package com.example.myapplication.presentation.view.viewmodel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences=requireActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE)

        val handlerr=Handler(Looper.getMainLooper())
        handlerr.postDelayed({
            val Loggedin=sharedPreferences.getBoolean("Loggedin",false)
            if (Loggedin) {
                findNavController().navigate(R.id.action_splashFragment_to_artFragment2)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_singInFragment)
            }
        },3000)


    }



}