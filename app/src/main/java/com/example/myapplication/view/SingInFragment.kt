package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSingInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding



    fun creadetData() {
        val hmap = hashMapOf<String, Any>()
        val keyHmap = hashMapOf<String, Any>()
        keyHmap["ic-ice-hmap"] = hmap
        hmap["yusif"] = "tenbel"
        hmap["agabey"] = 1000000
        Firebase.firestore.collection("User").document("hmaopss").set(keyHmap, SetOptions.merge())
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSingInBinding.inflate(inflater,container,false)
       /**ddeishiklikler**/
       binding.button.setOnClickListener {
           val email = binding.editTextTextEmailAddress.text.toString().trim()
           val password = binding.editTextNumberPassword.text.toString().trim()

           Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
              findNavController().navigate(R.id.action_singInFragment_to_artFragment)

           }.addOnFailureListener {
               Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()

           }

       }
        // buracan
        binding.button2.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextNumberPassword.text.toString().trim()


            Firebase.auth.createUserWithEmailAndPassword(
                email, password
            )
                .addOnSuccessListener {
                    val hmap = hashMapOf<String, Any>()
                    hmap["email"] = email
                    hmap["password"] = password
                    Firebase.firestore.collection("Users").document(Firebase.auth.currentUser!!.uid)
                        .set(hmap)

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()

                }
        }
        
        // Inflate the layout for this fragment
        return binding.root

    }


}