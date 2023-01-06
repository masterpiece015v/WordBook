package com.example.wordbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter(private val data:List<String>) : RecyclerView.Adapter<CategoryViewHolder>(){
    private var listener:((String?)->Unit)?=null

    fun setOnClCategoryClickListener(listener:((String?)->Unit)?){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val viewHolder = CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_category_item,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.txtCategoryItem.text = data[position]
        holder.clCategory.setOnClickListener {
            listener?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }

}