package com.example.sb2318.growladyadmin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploadVideoActivity : AppCompatActivity() {

    private val videoIdText:EditText
      get()= findViewById(R.id.videoTitle)

    private val classSpinner:Spinner
       get()= findViewById(R.id.classCategory)

    private val subjectSpinner:Spinner
      get()= findViewById(R.id.subjectCategory)

    private val infoTextView:TextView
       get()= findViewById(R.id.more_info)

    private val uploadVideoBtn:Button
      get()= findViewById(R.id.uploadVideoBtn)

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video)

        dbRef = FirebaseDatabase.getInstance().getReference("Videos")

        classSpinner.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                classItems
            )
        )

        subjectSpinner.setAdapter(
            ArrayAdapter(
                this,
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
                Toast.makeText(this, "Select one subject as it is not motivation", Toast.LENGTH_SHORT).show()
            } else if (classCategory == "Select Class") {
                Toast.makeText(this, "Please select your class", Toast.LENGTH_LONG)
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
       // TODO("Not yet implemented")
        val uri= Uri.parse(url)
        startActivity(Intent(Intent.ACTION_VIEW,uri))

    }


    private fun uploadVideo() {
        //TODO("Not yet implemented")
        val uniqueKey = dbRef.child(classCategory).push().key

        val videoData= VideoData()
        videoData.videoID= videoID
        videoData.videoTitle= "${videoID}: ${subjectCategory}"
        videoData.uniqueKey= uniqueKey!!
        videoData.classCategory= classCategory
        videoData.subCategory= subjectCategory

        dbRef.child(classCategory).child(uniqueKey!!).setValue(videoData).addOnCompleteListener {
            //pd!!.dismiss()
            Toast.makeText(this, "Video uploaded successfully.", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            //pd!!.dismiss()
            Toast.makeText(this, "Failed to upload video", Toast.LENGTH_SHORT).show()
            videoIdText.setText("")
        }
    }
}