package com.example.embeddedprogrammingassignment.fragments.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.example.embeddedprogrammingassignment.R
import com.example.embeddedprogrammingassignment.modal.User
import org.parceler.Parcels

class SopViolationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_sop_violation, container, false)

        val user: User? = Parcels.unwrap<User>(
            requireArguments().getParcelable("activeUser")
        )
        val bundle = Bundle()
        bundle.putParcelable("activeUser", Parcels.wrap<User>(user))

        val submitBtn = view?.findViewById<Button>(R.id.btnSopVioSubmit)
        val evidence = view?.findViewById<ImageView>(R.id.ivSopViolation)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                evidence?.setImageURI(it)
            })

        submitBtn?.setOnClickListener {
            // Add function to save to database

            Navigation.findNavController(view).navigate(R.id.action_sopViolationFragment_to_homeFragment, bundle)
        }

        evidence?.setOnClickListener {
            loadImage.launch("image/*")
        }

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        fun newInstance() =
            SopViolationFragment().apply {

            }
    }
}