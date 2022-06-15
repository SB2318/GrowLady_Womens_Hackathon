package com.example.sb2318.growlady

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.sb2318.growlady.R
import com.example.sb2318.growlady.VideoData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadVideoFragment : Fragment() {

    private lateinit var videoIdText:EditText
    private lateinit var classSpinner:Spinner
    private lateinit var subjectSpinner:Spinner
    private lateinit var infoTextView:TextView
    private lateinit var uploadVideoBtn:Button

    var classItems = arrayOf(
        "Select Class",
        "Five",
        "Six",
        "Seven",
        "Eight",
        "Nine",
        "Ten",
        "Eleven",
        "Twelve",
        "Motivation"
    )

    var subjectItems = arrayOf(
        "Select Subject",
        "English",
        "Math",
        "Bengali",
        "History",
        "Geography",
        "Physics",
        "Chemistry",
        "Biology"
    )

    private lateinit var videoID:String
    private lateinit var subjectCategory:String
    private lateinit var classCategory:String

    private lateinit var dbRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        // activity?.actionBar?.hide()
        return inflater.inflate(R.layout.fragment_upload_video,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoIdText= view.findViewById(R.id.videoTitle)
        classSpinner= view.findViewById(R.id.classCategory)
        subjectSpinner= view.findViewById(R.id.subjectCategory)
        infoTextView= view.findViewById(R.id.more_info)
        uploadVideoBtn= view.findViewById(R.id.uploadVideoBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Videos")

        classSpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                classItems
            )
        )

        subjectSpinner.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                subjectItems
            )
        )

        classSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                classCategory = classSpinner.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        subjectSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                subjectCategory = subjectSpinner.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        uploadVideoBtn.setOnClickListener {
            videoID = videoIdText.text.toString()
            if (videoID.isEmpty()) {

                videoIdText.error = "Empty"
                videoIdText.requestFocus()

            } else if (subjectCategory.equals("Select Subject") && !classCategory.equals("Motivation")) {
                Toast.makeText(requireContext(), "Select one subject as it is not motivation", Toast.LENGTH_SHORT).show()
            } else if (classCategory == "Select Class") {
                Toast.makeText(requireContext(), "Please select your class", Toast.LENGTH_LONG)
                    .show()
            }else {
                uploadVideo()
            }
        }

        infoTextView.setOnClickListener {
            openLink("https://www.youtube.com/watch?v=liJVSwOiiwg")
        }
            }



    private fun openLink(url: String) {

        val uri= Uri.parse(url)
        requireContext().startActivity(Intent(Intent.ACTION_VIEW,uri))

    }


    private fun uploadVideo() {

        val uniqueKey = dbRef.child(classCategory).push().key

        val videoData= VideoData()
        videoData.videoID= videoID
        videoData.videoTitle= "${videoID}: ${subjectCategory}"
        videoData.uniqueKey= uniqueKey!!
        videoData.classCategory= classCategory
        videoData.subCategory= subjectCategory

        dbRef.child(classCategory).child(uniqueKey!!).setValue(videoData).addOnCompleteListener {
            //pd!!.dismiss()
            Toast.makeText(requireContext(), "Thanks for contributing", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            //pd!!.dismiss()
            Toast.makeText(requireContext(), "Failed to upload video", Toast.LENGTH_SHORT).show()
            videoIdText.setText("")
        }
    }
}