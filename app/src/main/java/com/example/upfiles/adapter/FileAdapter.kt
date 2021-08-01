package com.example.upfiles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.upfiles.R
import kotlinx.android.synthetic.main.file_item_view.view.*
import java.io.File

class FileAdapter(private val context: Context, private val files: ArrayList<String>): RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    inner class FileViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(LayoutInflater.from(context).inflate(R.layout.file_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.itemView.file_name_item.text = file
    }

    override fun getItemCount(): Int {
        return files.size
    }
}