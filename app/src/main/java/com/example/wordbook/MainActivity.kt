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
        //View変数
        val rvCategory = findViewById<RecyclerView>(R.id.rvCategory)

        //RecyclerView用のリスト
        val categoryList = mutableListOf<String>()

        //SQLiteの処理
        val helper = WordBookDatabaseHelper(this)
        val cols = arrayOf("category")
        helper.readableDatabase.use{db->
            //第一引数はdistinctするか否か
            db.query(
                true,"WordBookTable",cols,null,null,null,null,null,null
            ).use{cs->
                //カーソルの中身がなくなるまでcategoryListへ追加する
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
        //RecyclerViewのためのアダプター
        val adapter = CategoryListAdapter(categoryList)
        adapter.setOnClCategoryClickListener {
            //選択したCategoryの値をWordActivityに渡して遷移する
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