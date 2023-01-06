package com.example.wordbook

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import android.widget.ToggleButton
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WordActivity : AppCompatActivity() {

    lateinit var toggle :ToggleButton
    lateinit var cpCount :Chip
    lateinit var swRemember : Switch
    lateinit var txtWord : TextView
    lateinit var btnBack : FloatingActionButton
    lateinit var btnClose : FloatingActionButton
    lateinit var btnNext : FloatingActionButton
    lateinit var helper : WordBookDatabaseHelper
    var index = 0
    val wordBookList = mutableListOf<WordBook>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)
        //ビュー
        toggle = findViewById(R.id.toggle)
        cpCount = findViewById(R.id.cpCount)
        swRemember = findViewById(R.id.swRemember)
        txtWord = findViewById(R.id.txtWord)
        btnBack = findViewById(R.id.btnBack)
        btnClose = findViewById(R.id.btnClose)
        btnNext = findViewById(R.id.btnNext)
        //データベースからリストを作る
        val category =  intent.getStringExtra("category")
        helper = WordBookDatabaseHelper(this)
        val cols = arrayOf("_id","category","japanese","english","remembered","count")
        helper.readableDatabase.use{db->
            db.query(
                "WordBookTable",cols,"category = ?",arrayOf(category),null,null,null,null
            ).use{cs->
                if(cs.moveToFirst()){
                    do{
                        val word = WordBook(
                            cs.getInt(0),cs.getString(1),cs.getString(2),cs.getString(3),cs.getInt(4),cs.getInt(5)
                        )
                        wordBookList.add(word)
                        Log.d("value", word.toString())
                    }while (cs.moveToNext())
                }else{
                    Log.d("value","値はありません")
                }
            }
        }
        //表示する
        Log.d("value",wordBookList.size.toString())
        viewShow(index)

        //ボタンイベント
        swRemember.setOnCheckedChangeListener { compoundButton, b ->
            if(swRemember.isChecked) {
                wordBookList[index].remembered = 1
            }else{
                wordBookList[index].remembered = 0
            }
        }

        toggle.setOnClickListener {
            if( toggle.isChecked ){
                //日本語
                txtWord.text = wordBookList[index].english
            }else{
                //英語
                txtWord.text = wordBookList[index].japanese
            }
        }
        //<<ボタン
        btnBack.setOnClickListener {
            if(index > 0 ){
                index--
                viewShow(index)
            }
        }

        //>>ボタン
        btnNext.setOnClickListener {
            Log.d("value",index.toString())
            if( wordBookList.size-1 > index){
                index++
                viewShow(index)
            }
        }

        //×ボタン
        btnClose.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //データベースに反映する
        helper.writableDatabase.use{ db->
            for(item in wordBookList){
                val cv = ContentValues().apply{
                    put("_id",item._id)
                    put("remembered",item.remembered)
                    put("count",item.count)
                }
                //db.insertWithOnConflict("WordBookTable",null,cv, SQLiteDatabase.CONFLICT_REPLACE)
                val params = arrayOf(item._id.toString())
                db.update("WordBookTable",cv,"_id = ?",params)
                Log.d("value",item._id.toString() + ":更新完了")
            }
        }
        Log.d("value","onDestroy()")
    }
    //表示のための関数
    fun viewShow( index : Int){
        wordBookList[index].count++

        if( toggle.isChecked ){
            //日本語
            txtWord.text = wordBookList[index].english
        }else{
            //英語
            txtWord.text = wordBookList[index].japanese
        }
        //表示回数
        cpCount.text = "表示回数:" + wordBookList[index].count
        //覚えたスイッチ
        if(wordBookList[index].remembered==1) {
            swRemember.isChecked = true
        }else if(wordBookList[index].remembered==0){
            swRemember.isChecked = false
        }
    }
}