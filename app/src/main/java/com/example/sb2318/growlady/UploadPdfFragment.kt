package com.example.sb2318.growlady

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class UploadPdfFragment: Fragment() {


    private lateinit var addPdfCard: CardView
    private lateinit var spinner: Spinner
    private lateinit var pdfTitle: EditText
    private lateinit var uploadPdfBtn: Button

    private lateinit var dbRef: DatabaseReference
    private lateinit var stRef: StorageReference

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        // activity?.actionBar?.hide()
        return inflater.inflate(R.layout.fragment_upload_pdf,container,false)

    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPdfCard= view.findViewById(R.id.addPdf)
        spinner= view.findViewById(R.id.pdfCategory)
        pdfTitle= view.findViewById(R.id.pdfTitle)
        uploadPdfBtn= view.findViewById(R.id.uploadPdfBtn)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {

                pdfData = result.data!!.data
                // to show the pdfData
                // Toast.makeText(UploadEbook.this, "" + pdfData, Toast.LENGTH_LONG).show();
                if (pdfData.toString().startsWith("content://")) {

                    try {
                        var cursor: Cursor?
                        cursor =
                            pdfData?.let {
                                requireActivity().getContentResolver()
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
                requireContext(),
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

            activity?.title  = pdfTitle.text.toString()
            if (activity?.title?.isEmpty() == true) {
                pdfTitle.error = "Empty"
                pdfTitle.requestFocus()

            } else if (pdfData == null) {
                Toast.makeText(requireContext(), "Please Upload Pdf", Toast.LENGTH_SHORT).show()
            } else if (category == "Select Class") {
                Toast.makeText(requireContext(), "Please select your class", Toast.LENGTH_LONG)
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
                Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "Thanks for contributing", Toast.LENGTH_SHORT)
                .show()
        }.addOnFailureListener {
            //pd!!.dismiss()
            Toast.makeText(requireContext(), "Failed to upload Pdf", Toast.LENGTH_SHORT).show()
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