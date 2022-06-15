package com.example.sb2318.growlady

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoAdapter(private val context: Context, list: ArrayList<VideoData>) :
        RecyclerView.Adapter<VideoAdapter.VideoViewAdapter>() {
        private val list: ArrayList<VideoData>
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): VideoViewAdapter {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.video_item_layout, parent, false)
            return VideoViewAdapter(view)
        }

        override fun onBindViewHolder(
            holder: VideoViewAdapter,
            position: Int
        ) {
            val currentItem: VideoData = list[position]

           // context.getLifecycle().addObserver(holder.youTubePlayerView)
            (context as MainActivity).lifecycle.addObserver(holder.youTubePlayerView)

            holder.youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = currentItem.videoID

                    youTubePlayer.loadVideo(videoId, 3f)
                }
            })

        }

        override fun getItemCount(): Int {
            return list.size
        }

        inner class VideoViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val youTubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.video_player)

        }

        init {
            this.list = list
        }


    }
