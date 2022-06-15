package com.example.sb2318.growlady

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class PdfAdapter(private val context: Context, list: ArrayList<PdfData>, category: String) :
    RecyclerView.Adapter<PdfAdapter.PdfViewAdapter>() {
    private val list: ArrayList<PdfData>
    private val category:String


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PdfViewAdapter {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.pdf_item_layout, parent, false)
        return PdfViewAdapter(view)
    }

    override fun onBindViewHolder(
        holder: PdfViewAdapter,
        position: Int
    ) {
        val currentItem: PdfData = list[position]
        holder.pdfTitle.setText(currentItem.pdfTitle)

        holder.downloadPdf.setOnClickListener{

           downloadFiles(context,currentItem.pdfTitle,".pdf",DIRECTORY_DOWNLOADS,currentItem.pdfUrl)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun downloadFiles(context:Context, fileName:String, fileExtension:String, destinationDirectory:String, url:String){

        val downloadManager:DownloadManager= context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri= Uri.parse(url)
        val request= DownloadManager.Request(uri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context,destinationDirectory, fileName+fileExtension)
        downloadManager.enqueue(request)
    }

    inner class PdfViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val  downloadPdf: ImageView
        val pdfTitle: TextView

        init {
            downloadPdf = itemView.findViewById(R.id.download_video)
            pdfTitle = itemView.findViewById(R.id.pdf_name)
        }
    }

    init {
        this.list = list
        this.category= category
    }
}