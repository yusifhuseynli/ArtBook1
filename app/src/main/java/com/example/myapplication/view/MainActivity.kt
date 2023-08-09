package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.FragmentFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var fragmentFactory:ArtFragmentFactory
 //   private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)
     supportFragmentManager.fragmentFactory = fragmentFactory

     //ashaqdaki kod enter basanda yeni setre kecmeye komek edit
     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

//        binding= ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.button2.setOnClickListener {

        //    val email=binding.editTextTextEmailAddress.text.toString().trim()
        //    val password=binding.editTextNumberPassword.text.toString().trim()


//            Firebase.auth.createUserWithEmailAndPassword(
//                email,password)
//                .addOnSuccessListener {
//                val hmap= hashMapOf<String,Any>()
//                    hmap["email"]=email
//                    hmap["password"]=password
//                    Firebase.firestore.collection("Users").document(Firebase.auth.currentUser!!.uid).set(hmap)
//
//            }.addOnFailureListener {
//                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
//
//            }
//        }



//        Firebase.auth.createUserWithEmailAndPassword("yusif3@gmail.ru","12340669").addOnSuccessListener {
//            creadetData()
//            Toast.makeText(this,"User created",Toast.LENGTH_SHORT).show()
//
//        }.addOnFailureListener {
//            Toast.makeText(this,"User not created",Toast.LENGTH_SHORT).show()
//        }
//        Firebase.auth.currentUser!!.uid
//    }



//    fun creadetData(){
//        val hmap= hashMapOf<String,Any>()
//    val keyHmap=hashMapOf<String,Any>()
//    keyHmap["ic-ice-hmap"]=hmap
//        hmap["yusif"]="tenbel"
//        hmap["agabey"]=1000000
//        Firebase.firestore.collection("User").document("hmaopss").set(keyHmap, SetOptions.merge())
//            .addOnSuccessListener {
//
//            }.addOnFailureListener {
//
//            }
//    }

    }
}