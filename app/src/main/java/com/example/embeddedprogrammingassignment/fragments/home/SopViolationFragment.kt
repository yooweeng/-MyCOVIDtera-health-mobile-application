package com.example.embeddedprogrammingassignment.fragments.home

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.embeddedprogrammingassignment.R
import com.example.embeddedprogrammingassignment.modal.SopReport
import com.example.embeddedprogrammingassignment.modal.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.FirebaseStorageKtxRegistrar
import com.google.firebase.storage.ktx.storage
import org.parceler.Parcels
import java.util.*

class SopViolationFragment : Fragment() {

    lateinit var selectDate: AutoCompleteTextView
    lateinit var calDraw: TextInputLayout
    lateinit var imgUri: Uri
    lateinit var titleTv: TextView
    lateinit var descriptionTv: TextView
    lateinit var storageReference: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_home_sop_violation, container, false)

        val user: User? = Parcels.unwrap<User>(
            requireArguments().getParcelable("activeUser")
        )
        val bundle = Bundle()
        bundle.putParcelable("activeUser", Parcels.wrap<User>(user))

        val submitBtn = view?.findViewById<Button>(R.id.btnSopVioSubmit)
        val evidence = view?.findViewById<ImageView>(R.id.ivSopViolation)
        titleTv = view?.findViewById<TextView>(R.id.tvSopTitle)!!
        descriptionTv = view.findViewById(R.id.tvSopDescription)!!
        selectDate = view?.findViewById(R.id.tvSopDate)!!
        calDraw = view.findViewById(R.id.tilSopDateDrawable)
        val selectTime = view.findViewById<AutoCompleteTextView>(R.id.tvSopTime)

        selectDate.setOnClickListener {
            DatePickerPopup(it)
        }

        val timeOption = resources.getStringArray(R.array.vax_time)
        val ppvAdapterItems = ArrayAdapter(requireContext(), R.layout.vaccination_ppv_dropdown_item, timeOption)
        selectTime?.setAdapter(ppvAdapterItems)

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imgUri = it
                evidence?.setImageURI(imgUri)
            })

        submitBtn?.setOnClickListener {
            val title = titleTv.text.toString()
            val date = selectDate.text.toString()
            val time = selectTime.text.toString()
            val description = descriptionTv.text.toString()

            // Add function to save to database
            val report = SopReport(title, date, time, description)
            FirebaseDatabase.getInstance().getReference("sopReport").child(user!!.nric).child(title).setValue(report).addOnCompleteListener {
                if(it.isSuccessful) {
                    storageReference = FirebaseStorage.getInstance().getReference("reports/"+ user.nric+title)
                    storageReference.putFile(imgUri).addOnSuccessListener {

                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Fail to upload image!", Toast.LENGTH_SHORT).show()
                        it.message?.let { it1 -> Log.i("Uploadfail", it1) }
                    }
                } else {
                    Toast.makeText(requireContext(), "Fail to upload report!", Toast.LENGTH_SHORT).show()
                }
            }


            Toast.makeText(requireContext(), "Report submitted.", Toast.LENGTH_SHORT).show()
            // Navigation.findNavController(view).navigate(R.id.action_sopViolationFragment_to_homeFragment, bundle)
        }

        evidence?.setOnClickListener {
            loadImage.launch("image/*")
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun DatePickerPopup(view: View?) {

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { datePicker, year, month, day ->
                var month = month
                month += 1
                val date = "$day/$month/$year"
                selectDate.setText(date)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    companion object {
        fun newInstance() =
            SopViolationFragment().apply {

            }
    }
}