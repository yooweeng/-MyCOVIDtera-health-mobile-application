package com.example.embeddedprogrammingassignment.fragments.trace

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.budiyev.android.codescanner.*
import com.example.embeddedprogrammingassignment.R
import com.example.embeddedprogrammingassignment.modal.User
import org.parceler.Parcels

private const val CAMERA_REQUEST_CODE = 101

class CheckInScanFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_trace_check_in_scan, container, false)

        var user: User? = null

        user = Parcels.unwrap<User>(
            requireArguments().getParcelable("activeUser")
        )
        val bundle = Bundle()
        bundle.putParcelable("activeUser", Parcels.wrap<User>(user))

        setupPermission()
        codeScanner(view, bundle)

        return view
    }

    private fun codeScanner(view: View, bundle: Bundle) {
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
                    // Write code here
                    val result: String = it.text
                    // if this is json file
                    if (result.contains("http://") || result.contains("www.") || result.contains("https://")) {
                        Log.i("Website", result)
                        codeScanner.stopPreview()
                        Navigation.findNavController(view).navigate(R.id.action_checkInScanFragment_to_checkInSuccessfulFragment, bundle)
                    }
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