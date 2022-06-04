package com.example.sb2318.growladyadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DeleteVideoActivity : AppCompatActivity() {

    private val fiveRecycler: RecyclerView
        get()= findViewById(R.id.fiveVideoRecycler)

    private val sixRecycler: RecyclerView
        get()= findViewById(R.id.sixVideoRecycler)
    private val sevenRecycler: RecyclerView
        get()= findViewById(R.id.sevenVideoRecycler)

    private val eightRecycler: RecyclerView
        get()= findViewById(R.id.eightVideoRecycler)

    private val nineRecycler: RecyclerView
        get()= findViewById(R.id.nineVideoRecycler)

    private val tenRecycler: RecyclerView
        get()= findViewById(R.id.tenVideoRecycler)

    private val elevenRecycler: RecyclerView
        get()= findViewById(R.id.elevenVideoRecycler)

    private val twelveRecycler: RecyclerView
        get()= findViewById(R.id.twelveVideoRecycler)

    private val motiveRecycler:RecyclerView
      get()= findViewById(R.id.motivationVideoRecycler)

    private lateinit var fiveDataList: ArrayList<VideoData>
    private lateinit var sixDataList: ArrayList<VideoData>
    private lateinit var sevenDataList: ArrayList<VideoData>
    private lateinit var eightDataList: ArrayList<VideoData>
    private lateinit var nineDataList: ArrayList<VideoData>
    private lateinit var tenDataList: ArrayList<VideoData>
    private lateinit var elevenDataList: ArrayList<VideoData>
    private lateinit var twelveDataList: ArrayList<VideoData>
    private lateinit var motivationList:ArrayList<VideoData>

    private lateinit var databaseReference: DatabaseReference
    private val TAG="MyTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_video)

        fiveRecycler.layoutManager = LinearLayoutManager(this)
        sixRecycler.layoutManager = LinearLayoutManager(this)
        sevenRecycler.layoutManager = LinearLayoutManager(this)
        eightRecycler.layoutManager = LinearLayoutManager(this)
        nineRecycler.layoutManager = LinearLayoutManager(this)
        tenRecycler.layoutManager = LinearLayoutManager(this)
        elevenRecycler.layoutManager = LinearLayoutManager(this)
        twelveRecycler.layoutManager = LinearLayoutManager(this)
        motiveRecycler.layoutManager = LinearLayoutManager(this)


        fiveRecycler.setHasFixedSize(true)
        sixRecycler.setHasFixedSize(true)
        sevenRecycler.setHasFixedSize(true)
        eightRecycler.setHasFixedSize(true)
        nineRecycler.setHasFixedSize(true)
        tenRecycler.setHasFixedSize(true)
        elevenRecycler.setHasFixedSize(true)
        twelveRecycler.setHasFixedSize(true)
        motiveRecycler.setHasFixedSize(true)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Videos")

    }//when we get the data


    override fun onStart() {
        super.onStart()
        Log.d(TAG,"OnStart Called")
        getfiveData()
        getSixData()
        getSevenData()
        getEightData()
        getNineData()
        getTenData()
        getElevenData()
        getTwelveData()
        getMotiveData()
    }

    private fun getMotiveData() {
       // TODO("Not yet implemented")
        databaseReference!!.child("Motivation").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                motivationList = ArrayList<VideoData>()
                for (dataSnapshot in snapshot.children) {
                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        motivationList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, motivationList)
                    feedAdapter.notifyDataSetChanged()
                    //when we get the data
                    fiveRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getfiveData()
    {
        databaseReference!!.child("Five").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                fiveDataList = ArrayList<VideoData>()
                for (dataSnapshot in snapshot.children) {
                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        fiveDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, fiveDataList)
                    feedAdapter.notifyDataSetChanged()
                    //when we get the data
                    fiveRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getSixData()
    {
        databaseReference!!.child("Six").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                sixDataList = ArrayList<VideoData>()

                for (dataSnapshot in snapshot.children) {
                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        sixDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, sixDataList)
                    feedAdapter.notifyDataSetChanged()
                    //when we get the data
                    sixRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getSevenData()
    {
        databaseReference!!.child("Seven").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                sevenDataList = ArrayList<VideoData>()
                for (dataSnapshot in snapshot.children) {
                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        sevenDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, sevenDataList)
                    feedAdapter.notifyDataSetChanged()
                    //when we get the data
                    sevenRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getEightData()
    {
        databaseReference!!.child("Eight").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                eightDataList = ArrayList<VideoData>()
                for (dataSnapshot in snapshot.children) {
                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        eightDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, eightDataList)
                    feedAdapter.notifyDataSetChanged()

                    eightRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getNineData()
    {
        databaseReference!!.child("Nine").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                nineDataList = ArrayList<VideoData>()

                for (dataSnapshot in snapshot.children) {

                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        nineDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, nineDataList)
                    feedAdapter.notifyDataSetChanged()

                    nineRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getTenData()
    {
        databaseReference!!.child("Ten").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                tenDataList = ArrayList<VideoData>()

                for (dataSnapshot in snapshot.children) {

                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        tenDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, tenDataList)
                    feedAdapter.notifyDataSetChanged()

                    tenRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getElevenData()
    {
        databaseReference!!.child("Eleven").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                elevenDataList = ArrayList<VideoData>()

                for (dataSnapshot in snapshot.children) {

                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        elevenDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, elevenDataList)
                    feedAdapter.notifyDataSetChanged()

                    elevenRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getTwelveData()
    {
        databaseReference!!.child("Twelve").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                twelveDataList = ArrayList<VideoData>()

                for (dataSnapshot in snapshot.children) {

                    val data: VideoData? = dataSnapshot.getValue(VideoData::class.java)
                    if (data != null) {
                        twelveDataList!!.add(data)
                    }
                    val feedAdapter = VideoAdapter(applicationContext, twelveDataList)
                    feedAdapter.notifyDataSetChanged()

                    twelveRecycler!!.adapter = feedAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //progressBar!!.visibility = View.GONE
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}


