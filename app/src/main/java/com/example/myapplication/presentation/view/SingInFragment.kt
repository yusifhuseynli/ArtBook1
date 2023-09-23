@file:Suppress("ControlFlowWithEmptyBody")

package com.example.myapplication.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.model.User
import com.example.myapplication.databinding.FragmentSingInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SingInFragment : Fragment() {
    private lateinit var binding: FragmentSingInBinding
    ///
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSingInBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return binding.root


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve stored user credentials from SharedPreferences
        val storedEmail = sharedPreferences.getString("email", null)
        val storedPassword = sharedPreferences.getString("password", null)
        storedEmail?.let { binding.editTextTextEmailAddress.setText(it) }
        // Set retrieved data
        storedPassword.let { binding.editTextNumberPassword.setText(it) }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            requireActivity().finish()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userDocumentRef = FirebaseFirestore.getInstance().collection("user").document(currentUser?.uid ?: "")

        userDocumentRef.addSnapshotListener { documentSnapshot, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                documentSnapshot.getString("email")
                documentSnapshot.getString("password")
                // Update UI with retrieved data
            }
        }


        binding = FragmentSingInBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                // Proceed with your app logic
            } else {
                // User is signed out
                // Handle UI changes or re-authentication if necessary
            }
        }


        binding.button.setOnClickListener {


            if (
                binding.editTextTextEmailAddress.text!!.isNotEmpty() && binding.editTextNumberPassword.text!!.isNotEmpty()
            ) {

                signinUser(binding.editTextTextEmailAddress.text.toString(),
                    binding.editTextNumberPassword.text.toString())


            }

        }

        binding.button2.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
          val password = binding.editTextNumberPassword.text.toString().trim()

            Firebase.auth.createUserWithEmailAndPassword(
                email, password
           )
                .addOnSuccessListener {
                    Firebase.firestore.collection("user").document(Firebase.auth.currentUser!!.uid)
                        .set(User("","","",email,password, "https://firebasestorage.googleapis.com/v0/b/art-book-27f36.appspot.com/o/images%2Fphoto-1511367461989-f85a21fda167.jpg?alt=media&token=33183012-a44e-44b8-954f-8a77ac56cb33"
                        ))

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()

                }
        }

    }

    private fun signinUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {


                    val editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.putBoolean("Loggedin", true)
                    editor.apply()


                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_singInFragment_to_artFragment)
                }


            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            }

    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

}