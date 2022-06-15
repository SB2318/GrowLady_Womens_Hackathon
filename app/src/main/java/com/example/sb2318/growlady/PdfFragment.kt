package com.example.sb2318.growlady

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class PdfFragment: Fragment() {

    private lateinit var pdfRecycler:RecyclerView
    private lateinit var pdfDataList: ArrayList<PdfData>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var category:String
   // private lateinit var storageRef:StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_pdf,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pdfRecycler= view.findViewById(R.id.pdf_recycler)
        pdfRecycler.layoutManager= GridLayoutManager(requireContext(),2)
        pdfRecycler.setHasFixedSize(true)
        category= activity?.intent?.getStringExtra("Data").toString()

        databaseReference = FirebaseDatabase.getInstance().reference.child("Ebboks")

        getPdfDatas()

    }

    private fun getPdfDatas() {
        //TODO("Not yet implemented")

            databaseReference!!.child(category).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // here we initialize our data list
                    pdfDataList = ArrayList<PdfData>()
                    for (dataSnapshot in snapshot.children) {
                        val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                        if (data != null) {
                            pdfDataList!!.add(data)
                        }
                        val feedAdapter = PdfAdapter(requireContext(), pdfDataList, category)
                        feedAdapter.notifyDataSetChanged()
                        //when we get the data
                        pdfRecycler!!.adapter = feedAdapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //progressBar!!.visibility = View.GONE
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }
}