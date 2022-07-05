package com.example.embeddedprogrammingassignment.fragments.trace

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.budiyev.android.codescanner.*
import com.example.embeddedprogrammingassignment.JsonToObjectConverter
import com.example.embeddedprogrammingassignment.R
import com.example.embeddedprogrammingassignment.modal.HistoryItem
import com.example.embeddedprogrammingassignment.modal.QrCode
import com.example.embeddedprogrammingassignment.modal.QrHistory
import com.example.embeddedprogrammingassignment.modal.User
import com.google.firebase.database.*
import org.parceler.Parcels
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val CAMERA_REQUEST_CODE = 101
var rootNode: FirebaseDatabase? = null
var reference: DatabaseReference? = null
var i: Long = 0

class CheckInScanFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_trace_check_in_scan, container, false)

        setupPermission()
        codeScanner(view)

        return view
    }

    private fun codeScanner(view: View) {

        var user: User? = null
        user = Parcels.unwrap<User>(
                requireArguments().getParcelable("activeUser")
        )
        val bundle = Bundle()
        bundle.putParcelable("activeUser", Parcels.wrap<User>(user))

        val codeScannerID = view.findViewById<CodeScannerView>(R.id.scanScanCode)

        codeScanner = CodeScanner(requireContext(), codeScannerID)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {

                    val result: String = it.text
                    val qrCode: QrCode = JsonToObjectConverter.JsonToObject(result)

                    rootNode = FirebaseDatabase.getInstance()
                    reference = rootNode!!.getReference("history").child(user.nric)

                    reference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dataSnapshot in snapshot.children) {
                                Log.i("resultchildcount", snapshot.childrenCount.toString())
                                i=snapshot.childrenCount
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })

                    //get current time and date
                    val now: LocalDateTime = LocalDateTime.now()
                    val format: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss")
                    val formatDateTime: String = now.format(format)
                    val formatOnlyDate: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
                    val formatDate: String = now.format(formatOnlyDate)

                    val historyItem = HistoryItem(qrCode.isCheckIn, qrCode.location, formatDateTime)
                    val historyItems = ArrayList<HistoryItem>()
                    historyItems.add(historyItem)
                    val qrHistory = QrHistory(formatDate, qrCode.location, historyItems)

//        historyItems.add(new HistoryItem(true,"Xiamen University Malaysia", "28-March-2022 07:00:24"));
//        qrHistories.add(new QrHistory("28-March-2022","Xiamen University Malaysia",historyItems));
//        historyItems2.add(new HistoryItem(true,"Pavilion, Kuala Lumpur", "27-March-2022 12:03:14"));
//        historyItems2.add(new HistoryItem(false,"Pavilion, Kuala Lumpur", "27-March-2022 12:38:41"));
//        qrHistories.add(new QrHistory("27-March-2022","Pavilion, Kuala Lumpur",historyItems2));
//        historyItems3.add(new HistoryItem(true,"Lot 88, Kuala Lumpur", "27-March-2022 09:13:13"));
//        historyItems3.add(new HistoryItem(false,"Lot 88, Kuala Lumpur", "27-March-2022 10:07:01"));
//        qrHistories.add(new QrHistory("27-March-2022","Lot 88, Kuala Lumpur",historyItems3));

                    Handler().postDelayed({
                        reference!!.child("history"+i).setValue(qrHistory)
                        Log.i("result", i.toString())
                        Log.i("result2", result)
                        Log.i("result3", qrCode.toString())
                        Log.i("result3", formatDate.toString())
                    }, 2000)

                    codeScanner.stopPreview()
                    Navigation.findNavController(view).navigate(R.id.action_checkInScanFragment_to_checkInSuccessfulFragment, bundle)

                    // if this is json file
//                    if (result.contains("http://") || result.contains("www.") || result.contains("https://")) {
//                        codeScanner.stopPreview()
//                        Navigation.findNavController(view).navigate(R.id.action_checkInScanFragment_to_checkInSuccessfulFragment, bundle)
//                    }
                }
            }

            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }
        codeScannerID.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermission() {
        val permission: Int = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.CAMERA)
        if(permission != PackageManager.PERMISSION_GRANTED)
            makeRequest()
    }
    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

//    private val permReqLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            val granted = permissions.entries.all {
//                it.value == true
//            }
//            if (!granted) {
//                Toast.makeText(context, "You need the camera permission", Toast.LENGTH_SHORT).show()
//            }
//        }

}