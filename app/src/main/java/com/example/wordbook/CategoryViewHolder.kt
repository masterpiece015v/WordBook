package com.example.wordbook

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CategoryViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    val txtCategoryItem = itemView.findViewById<TextView>(R.id.txtCategoryItem)
    val clCategory = itemView.findViewById<ConstraintLayout>(R.id.clCategory)
}