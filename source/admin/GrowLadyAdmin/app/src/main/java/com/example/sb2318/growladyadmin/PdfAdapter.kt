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

class PdfAdapter(private val context: Context, list: ArrayList<PdfData>) :
        RecyclerView.Adapter<PdfViewAdapter>() {
        private val list: ArrayList<PdfData>
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PdfViewAdapter {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.delete_item_layout, parent, false)
            return PdfViewAdapter(view)
        }

        override fun onBindViewHolder(
            holder: PdfViewAdapter,
            position: Int
        ) {
            val currentItem: PdfData = list[position]
            holder.pdfTitle.setText(currentItem.pdfTitle)

            holder.deletePdf.setOnClickListener{
                var reference = FirebaseDatabase.getInstance().getReference("Ebboks")


                reference.child(currentItem.category).child(currentItem.uniqueKey).removeValue().addOnCompleteListener {
                    Log.d("Data Removed","Data Removed")
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Something Went Wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // to remove the data from recycler view item
                notifyItemRemoved(position)
                // to remove the data from recycler view item
              /*  val builder = AlertDialog.Builder(
                    context
                )
                builder.setMessage("Are you sure want to delete this pdf?")
                builder.setCancelable(true)

                builder.setPositiveButton(
                    "Yes"
                ) { dialog, which ->

                    deletePdfData(currentItem)
                    notifyItemRemoved(position)
                }

                builder.setNegativeButton(
                    "No"
                ) { dialog, which ->
                    dialog.cancel()
                }

                var alertDialog: AlertDialog? = null
                try {
                    alertDialog = builder.create()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                alertDialog?.show()

               */
            }
        }

    override fun getItemCount(): Int {
            return list.size
    }

    inner class PdfViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val deletePdf: ImageButton
            val pdfTitle: TextView

            init {
                deletePdf = itemView.findViewById(R.id.deletePdfBtn)
                pdfTitle = itemView.findViewById(R.id.pdfTitle)
            }
        }

        init {
            this.list = list
        }
    }
