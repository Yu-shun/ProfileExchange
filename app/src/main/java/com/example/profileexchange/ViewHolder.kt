package com.example.profileexchange

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.name_textView)
    val gitView: TextView = itemView.findViewById(R.id.git_textView)
    val twiView: TextView = itemView.findViewById(R.id.twi_textView)

    val twi_button: ImageButton = itemView.findViewById(R.id.imageButtonTwi)
    val git_button: ImageButton = itemView.findViewById(R.id.imageButtonGit)
    val del_button: ImageView = itemView.findViewById(R.id.imageViewDel)
}