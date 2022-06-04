package com.example.sb2318.growladyadmin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sb2318.growladyadmin.PdfAdapter.PdfViewAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class VideoAdapter(private val context: Context, list: ArrayList<VideoData>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewAdapter>() {
    private val list: ArrayList<VideoData>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoViewAdapter {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.delete_video_layout, parent, false)
        return VideoViewAdapter(view)
    }

    override fun onBindViewHolder(
        holder: VideoViewAdapter,
        position: Int
    ) {
        val currentItem: VideoData = list[position]
        holder.videoTitle.setText(currentItem.videoTitle)

        holder.deleteVideo.setOnClickListener{
            var reference = FirebaseDatabase.getInstance().getReference("Videos")


            reference.child(currentItem.classCategory).
                   child(currentItem.uniqueKey).removeValue().
            addOnCompleteListener {
                Log.d("Data Removed","Data Removed")

            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Something Went Wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class VideoViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteVideo: ImageButton
        val videoTitle: TextView

        init {
            deleteVideo = itemView.findViewById(R.id.deleteVideoBtn)
            videoTitle = itemView.findViewById(R.id.video_title)
        }
    }

    init {
        this.list = list
    }
}
