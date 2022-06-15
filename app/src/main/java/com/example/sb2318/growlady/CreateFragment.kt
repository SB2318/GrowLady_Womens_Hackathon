package com.example.sb2318.growlady

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateFragment: BottomSheetDialogFragment() {

    private lateinit var videoLayout:LinearLayout
    private lateinit var pdfLayout:LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_create,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoLayout= view.findViewById(R.id.upload_video)
        pdfLayout= view.findViewById(R.id.upload_pdf)

        videoLayout.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_uploadVideoFragment)
        }

        pdfLayout.setOnClickListener {
            findNavController().navigate(R.id.action_createFragment_to_uploadPdfFragment)
        }
    }
}