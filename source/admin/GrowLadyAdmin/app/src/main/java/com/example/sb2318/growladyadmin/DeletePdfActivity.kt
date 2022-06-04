package com.example.sb2318.growladyadmin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DeletePdfActivity : AppCompatActivity() {

        private val fiveRecycler: RecyclerView
             get()= findViewById(R.id.fivePdfRecycler)

        private val sixRecycler: RecyclerView
              get()= findViewById(R.id.sixPdfRecycler)
        private val sevenRecycler: RecyclerView
               get()= findViewById(R.id.sevenPdfRecycler)

        private val eightRecycler: RecyclerView
              get()= findViewById(R.id.eightPdfRecycler)

        private val nineRecycler: RecyclerView
             get()= findViewById(R.id.ninePdfRecycler)

        private val tenRecycler: RecyclerView
             get()= findViewById(R.id.tenPdfRecycler)

         private val elevenRecycler: RecyclerView
            get()= findViewById(R.id.elevenPdfRecycler)

         private val twelveRecycler: RecyclerView
             get()= findViewById(R.id.twelvePdfRecycler)
         private val prevRecycler:RecyclerView
              get()= findViewById(R.id.prevPdfRecycler)

         private lateinit var fiveDataList: ArrayList<PdfData>
         private lateinit var sixDataList: ArrayList<PdfData>
         private lateinit var sevenDataList: ArrayList<PdfData>
         private lateinit var eightDataList: ArrayList<PdfData>
         private lateinit var nineDataList: ArrayList<PdfData>
         private lateinit var tenDataList: ArrayList<PdfData>
         private lateinit var elevenDataList: ArrayList<PdfData>
         private lateinit var twelveDataList: ArrayList<PdfData>
         private lateinit var prevDataList: ArrayList<PdfData>

         private lateinit var databaseReference: DatabaseReference
         private val TAG="MyTag"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_delete_pdf)

            fiveRecycler.layoutManager = LinearLayoutManager(this)
            sixRecycler.layoutManager = LinearLayoutManager(this)
            sevenRecycler.layoutManager = LinearLayoutManager(this)
            eightRecycler.layoutManager = LinearLayoutManager(this)
            nineRecycler.layoutManager = LinearLayoutManager(this)
            tenRecycler.layoutManager = LinearLayoutManager(this)
            elevenRecycler.layoutManager = LinearLayoutManager(this)
            twelveRecycler.layoutManager = LinearLayoutManager(this)
            prevRecycler.layoutManager = LinearLayoutManager(this)

            fiveRecycler.setHasFixedSize(true)
            sixRecycler.setHasFixedSize(true)
            sevenRecycler.setHasFixedSize(true)
            eightRecycler.setHasFixedSize(true)
            nineRecycler.setHasFixedSize(true)
            tenRecycler.setHasFixedSize(true)
            elevenRecycler.setHasFixedSize(true)
            twelveRecycler.setHasFixedSize(true)
            prevRecycler.setHasFixedSize(true)

            databaseReference = FirebaseDatabase.getInstance().reference.child("Ebboks")

        }//when we get the data


    override fun onStart() {
        super.onStart()
         Log.d(TAG,"OnStart Called")
        getfivePdfs()
        getSixPdfs()
        getSevenPdfs()
        getEightPdfs()
        getNinePdfs()
        getTenPdfs()
        getElevenPdfs()
        getTwelvePdfs()
        getPrevPdfs()
    }
    private fun getfivePdfs()
    {
            databaseReference!!.child("Five").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // here we initialize our data list
                    fiveDataList = ArrayList<PdfData>()
                    for (dataSnapshot in snapshot.children) {
                        val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                        if (data != null) {
                            fiveDataList!!.add(data)
                        }
                        val feedAdapter = PdfAdapter(applicationContext, fiveDataList)
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

    private fun getSixPdfs()
    {
        databaseReference!!.child("Six").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                sixDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {
                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        sixDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, sixDataList)
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

    private fun getSevenPdfs()
    {
        databaseReference!!.child("Seven").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // here we initialize our data list
                sevenDataList = ArrayList<PdfData>()
                for (dataSnapshot in snapshot.children) {
                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        sevenDataList!!.add(data)
                    }
                   val feedAdapter = PdfAdapter(applicationContext, sevenDataList)
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

    private fun getEightPdfs()
    {
        databaseReference!!.child("Eight").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                eightDataList = ArrayList<PdfData>()
                for (dataSnapshot in snapshot.children) {
                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        eightDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, eightDataList)
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

    private fun getNinePdfs()
    {
        databaseReference!!.child("Nine").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                nineDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {

                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        nineDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, nineDataList)
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

    private fun getTenPdfs()
    {
        databaseReference!!.child("Ten").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                tenDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {

                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        tenDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, tenDataList)
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

    private fun getElevenPdfs()
    {
        databaseReference!!.child("Eleven").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                elevenDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {

                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        elevenDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, elevenDataList)
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

    private fun getTwelvePdfs()
    {
        databaseReference!!.child("Twelve").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                twelveDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {

                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        twelveDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, twelveDataList)
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

    private fun getPrevPdfs()
    {
        databaseReference!!.child("Previous Paper").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                prevDataList = ArrayList<PdfData>()

                for (dataSnapshot in snapshot.children) {

                    val data: PdfData? = dataSnapshot.getValue(PdfData::class.java)
                    if (data != null) {
                        prevDataList!!.add(data)
                    }
                    val feedAdapter = PdfAdapter(applicationContext, prevDataList)
                    feedAdapter.notifyDataSetChanged()

                    prevRecycler!!.adapter = feedAdapter
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

