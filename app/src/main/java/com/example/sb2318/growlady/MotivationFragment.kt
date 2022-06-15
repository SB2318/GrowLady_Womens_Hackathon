package com.example.sb2318.growlady

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MotivationFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        // activity?.actionBar?.hide()
        return inflater.inflate(R.layout.fragment_motivation,container,false)

    }

        private lateinit var videoRecycler: RecyclerView
        private lateinit var videoDataList: ArrayList<VideoData>
        private lateinit var databaseReference: DatabaseReference
       // private lateinit var category:String
        private var isFragmentPause= false



        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            //super.onViewCreated(view, savedInstanceState)
            videoRecycler= view.findViewById(R.id.motive_recycler)
            videoRecycler.layoutManager= LinearLayoutManager(requireContext())
            videoRecycler.setHasFixedSize(true)
           // category= activity?.intent?.getStringExtra("Data").toString()

            databaseReference = FirebaseDatabase.getInstance().reference.child("Videos")

            getVideoDatas()

        }


        private fun getVideoDatas() {

            databaseReference!!.child("Motivation").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // here we initialize our data list
                    videoDataList = ArrayList<VideoData>()
                    for (dataSnapshot in snapshot.children) {
                        val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                        if (data != null) {
                            videoDataList!!.add(data)
                        }
                        val feedAdapter = VideoAdapter(requireContext(), videoDataList)
                        feedAdapter.notifyDataSetChanged()
                        //when we get the data
                        videoRecycler!!.adapter = feedAdapter
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
