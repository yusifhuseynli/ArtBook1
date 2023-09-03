package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.presentation.view.ArtFragmentFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory
   // private lateinit var binding: ActivityMainBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   //    binding= ActivityMainBinding.inflate(layoutInflater)
   //     setContentView(binding.root)
     supportFragmentManager.fragmentFactory = fragmentFactory

      val NavView:BottomNavigationView=findViewById(R.id.bottomNavigationView)
      val navHostFragment =
          supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
      val navController = navHostFragment.navController
      NavigationUI.setupWithNavController(NavView, navController)


      navController.addOnDestinationChangedListener { _, destination, _ ->
          when (destination.id) {
              R.id.artFragment,
              R.id.artProfilFragment,
              R.id.artDetailesFragment,

              -> NavView.visibility = View.VISIBLE

              else -> NavView.visibility = View.GONE
          }
      }


     //ashaqdaki kod enter basanda yeni setre kecmeye komek edit
     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)




    }
}