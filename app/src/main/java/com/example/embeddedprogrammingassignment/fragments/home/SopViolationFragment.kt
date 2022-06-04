package com.example.embeddedprogrammingassignment.fragments.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.embeddedprogrammingassignment.R
import com.example.embeddedprogrammingassignment.modal.User
import com.google.android.material.textfield.TextInputLayout
import org.parceler.Parcels
import java.util.*

class SopViolationFragment : Fragment() {

    lateinit var selectDate: AutoCompleteTextView
    lateinit var calDraw: TextInputLayout

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