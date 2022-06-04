package com.example.sb2318.growladyadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private val uploadPdfCard:CardView
        get()= findViewById(R.id.upload_pdf_card)

    private val uploadVideoCard:CardView
        get()= findViewById(R.id.upload_video_card)

    private val deletePdfCard:CardView
        get()= findViewById(R.id.delete_pdf_card)

    private val deleteVideoCard:CardView
        get()= findViewById(R.id.delete_video_card)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uploadPdfCard.setOnClickListener {
            startActivity(Intent(this,UploadPdfActivity::class.java))
        }

        uploadVideoCard.setOnClickListener {
            startActivity(Intent(this,UploadVideoActivity::class.java))
        }

        deletePdfCard.setOnClickListener {
            startActivity(Intent(this,DeletePdfActivity::class.java))
        }

        deleteVideoCard.setOnClickListener {
            startActivity(Intent(this,DeleteVideoActivity::class.java))
        }
    }
}