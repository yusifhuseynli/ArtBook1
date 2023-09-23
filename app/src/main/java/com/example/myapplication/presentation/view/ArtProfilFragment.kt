package com.example.myapplication.presentation.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentArtProfilBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID


@Suppress("DEPRECATION")
class ArtProfilFragment : Fragment() {
    private val PICK_IMAGE_REQUEST_CODE = 100
    var selectPicture: Uri? = null

    private lateinit var binding: FragmentArtProfilBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        binding = FragmentArtProfilBinding.inflate(inflater, container, false)

        binding.btnLogout.setOnClickListener {
            val message: String? = "Are you sure you want to log out? "
            showCustomDialogBox(message)

        }

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser()




        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_artProfilFragment_to_artFragment)
        }

        binding.btnSave.setOnClickListener {

            val name = binding.edittexName.text.toString()
            val surname = binding.edittexProfileSurname.text.toString()
            val phonenumber = binding.edittexPhone.text.toString()
            var img = ""
            val uuid = UUID.randomUUID()
            val imagename = "$uuid.jpg"
            val reference = Firebase.storage.reference
            val imagereference = reference.child("images/$imagename")
            imagereference.putFile(selectPicture!!).addOnSuccessListener {
                val uploadpicturereference =
                    Firebase.storage.reference.child("images/").child(imagename)
                uploadpicturereference.downloadUrl.addOnSuccessListener {
                    val dowloadUrl = it.toString()
                    img = dowloadUrl
                    val ref = Firebase.firestore.collection("user")
                        .document(Firebase.auth.currentUser!!.uid)
                    ref.update("name", name)
                    ref.update("surname", surname)
                    ref.update("phonenumber", phonenumber)
                    ref.update("img", img)

                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()


                }
            }


        }
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_artProfilFragment_to_artFragment)
        }


        binding.imageProfile.setOnClickListener {
            pickImagegalery()

        }

    }

    private fun getUser() {
        Firebase.firestore.collection("user").document(Firebase.auth.currentUser!!.uid)
            .addSnapshotListener { value, error ->
                try {
                    if (value != null) {
                        val name = value.get("name") as String
                        val surname = value.get("surname") as String
                        val phonenumber = value.get("phonenumber") as? String?
                        val img = value.get("img") as String
                        binding.edittexName.setText(name)
                        binding.edittexPhone.setText(phonenumber)
                        binding.edittexProfileSurname.setText(surname)
                        Glide.with(requireActivity()).load(img).into(binding.imageProfile)

                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
    }

    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.logmessage)
        val btnYes: Button = dialog.findViewById(R.id.yesbtn)
        val btnNo: Button = dialog.findViewById(R.id.nobtn)

        tvMessage.text = message
        btnYes.setOnClickListener {
            findNavController().navigate(R.id.action_artProfilFragment_to_singInFragment)
            dialog.dismiss()

        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun pickImagegalery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectPicture = selectedImageUri
            binding.imageProfile.setImageURI(selectedImageUri)
        }
    }

}