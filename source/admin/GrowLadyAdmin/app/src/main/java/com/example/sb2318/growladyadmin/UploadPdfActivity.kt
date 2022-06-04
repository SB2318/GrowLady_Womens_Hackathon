package com.example.sb2318.growladyadmin

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class UploadPdfActivity : AppCompatActivity() {

    private val addPdfCard:CardView
     get()= findViewById(R.id.addPdf)

    private val spinner:Spinner
       get()= findViewById(R.id.pdfCategory)

    private val pdfTitle:EditText
      get()= findViewById(R.id.pdfTitle)

    private val uploadPdfBtn:Button
       get()= findViewById(R.id.uploadPdfBtn)

   /* private val pd:ProgressDialog
        get()= ProgressDialog(applicationContext)

    */

    private lateinit var dbRef: DatabaseReference
    private lateinit var stRef: StorageReference



    // set the spinner
    var items = arrayOf(
        "Select Class",
        "Five",
        "Six",
        "Seven",
        "Eight",
        "Nine",
        "Ten",
        "Eleven",
        "Twelve",
       "Previous Paper"
    )
    private var category = ""
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var pdfName=""


    private var pdfData: Uri? = null
    var downloadURL = ""
   // private val pd: ProgressDialog? = ProgressDialog(this)

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_pdf)

        activityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {

                pdfData = result.data!!.data
                // to show the pdfData
                // Toast.makeText(UploadEbook.this, "" + pdfData, Toast.LENGTH_LONG).show();
                if (pdfData.toString().startsWith("content://")) {

                    try {
                        var cursor: Cursor?
                        cursor =
                            pdfData?.let {
                                this.getContentResolver()
                                    .query(it, null, null, null, null)
                            }
                        if (cursor != null && cursor.moveToFirst()) {
                            // display name
                            pdfName =
                                cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else if (pdfData.toString().startsWith("file://")) {
                    // to get the name of the pdf
                    pdfName = File(pdfData.toString()).name
                }

                // set pdf name inside text view
                pdfTitle.setText(pdfName)
            }
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Ebboks")
        stRef = FirebaseStorage.getInstance().reference
        spinner.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
        )

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                category = spinner.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        addPdfCard.setOnClickListener({ pick() })

        // upload data
        uploadPdfBtn.setOnClickListener {
            title = pdfTitle.text.toString()
            if (title.isEmpty()) {
                pdfTitle.error = "Empty"
                pdfTitle.requestFocus()

            } else if (pdfData == null) {
                Toast.makeText(this, "Please Upload Pdf", Toast.LENGTH_SHORT).show()
            } else if (category == "Select Class") {
                Toast.makeText(this, "Please select your class", Toast.LENGTH_LONG)
                    .show()
            }else {
                uploadPdf()
            }
        }

    }

    private fun uploadPdf() {
        // initialize progress dialogue
       // pd!!.setTitle("Please Wait...")
       // pd.setMessage("Uploading Pdf...")
        //pd.show()
        val reference =
            stRef.child(category).child(pdfName + ": " + System.currentTimeMillis() + ".pdf") //our pdf name must be unique, so we use currentTimeMillis()
        // put file
        reference.putFile(pdfData!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                // to stop the execution of program until our task is complete
                while (!uriTask.isComplete);
                val uri = uriTask.result
                uploadData(uri.toString())
            }.addOnFailureListener {
                //pd.dismiss()
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadData(value: String) {
        // find the unique key
        val uniqueKey = dbRef.child(category).push().key

        val pdfData= PdfData()
        pdfData.pdfTitle= pdfName
        pdfData.pdfUrl= value
        pdfData.uniqueKey= uniqueKey!!
        pdfData.category= category

        dbRef.child(category).child(uniqueKey!!).setValue(pdfData).addOnCompleteListener {
            //pd!!.dismiss()
            Toast.makeText(this, "Pdf uploaded successfully.", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            //pd!!.dismiss()
            Toast.makeText(this, "Failed to upload Pdf", Toast.LENGTH_SHORT).show()
            pdfTitle.setText("")
        }
    }

    private fun pick() {
        val intent = Intent()
        intent.type = "application/pdf"
        // set action
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // launch the intent
        activityResultLauncher.launch(intent)
    }
}