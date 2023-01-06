package com.example.wordbook

import android.animation.IntEvaluator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvCategory = findViewById<RecyclerView>(R.id.rvCategory)
        val categoryList = mutableListOf<String>()
        val helper = WordBookDatabaseHelper(this)
        val cols = arrayOf("category")
        helper.readableDatabase.use{db->
            db.query(
                true,"WordBookTable",cols,null,null,null,null,null,null
            ).use{cs->
                if(cs.moveToFirst()){
                    do{
                        Log.d("value", cs.getString(0))
                        categoryList.add(cs.getString(0))
                    }while (cs.moveToNext())
                }else{
                    Log.d("value","値はありません")
                }
            }
        }

        val adapter = CategoryListAdapter(categoryList)
        adapter.setOnClCategoryClickListener {
            //Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            val intent = Intent(this,WordActivity::class.java).apply{
                putExtra("category",it)
            }
            startActivity( intent )
        }
        rvCategory.adapter = adapter
        rvCategory.layoutManager = LinearLayoutManager(this).apply{
            orientation = LinearLayoutManager.VERTICAL
        }
    }
}