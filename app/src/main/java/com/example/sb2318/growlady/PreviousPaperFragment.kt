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

class PreviousPaperFragment: Fragment() {

        private lateinit var prevRecycler: RecyclerView
        private lateinit var prevDataList: ArrayList<PdfData>
        private lateinit var databaseReference: DatabaseReference
       // private lateinit var category:String
        // private lateinit var storageRef:StorageReference

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            //return super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.fragment_previous_paper,container,false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            prevRecycler= view.findViewById(R.id.prev_recycler)
            prevRecycler.layoutManager= GridLayoutManager(requireContext(),2)
            prevRecycler.setHasFixedSize(true)
          //  category= activity?.intent?.getStringExtra("Data").toString()

            databaseReference = FirebaseDatabase.getInstance().reference.child("Ebboks")

            getPrevPapers()

        }

        private fun getPrevPapers() {


            databaseReference!!.child("Previous Paper").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // here we initialize our data list
                    prevDataList = ArrayList<PdfData>()
                    for (dataSnapshot in snapshot.children) {
                        val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                        if (data != null) {
                            prevDataList!!.add(data)
                        }
                        val feedAdapter = PdfAdapter(requireContext(), prevDataList, "Previous Paper")
                        feedAdapter.notifyDataSetChanged()
                        //when we get the data
                        prevRecycler!!.adapter = feedAdapter
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
