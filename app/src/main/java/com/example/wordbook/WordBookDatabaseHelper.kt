package com.example.wordbook
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WordBookDatabaseHelper(context:Context?) :SQLiteOpenHelper(context,DBNAME,null,VERSION){
    companion object{
        private const val DBNAME = "WordBookDatabase.sqlite"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let{
            it.execSQL("CREATE TABLE WordBookTable(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "category TEXT,japanese TEXT,english TEXT , remembered INT,count INT)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('あいさつ','おめでとう','congratulations!',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('あいさつ','さようなら','goodbye',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('あいさつ','こんにちは','hello',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('あいさつ','名前','name',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','住所','address',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','飛行機','airplane',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','通り','avenue',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','お手洗い','bathroom',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','入口','entrance',0,0)")

            it.execSQL("INSERT INTO WordBookTable(category,japanese,english,remembered,count)" +
                    " values('旅行','出口','exit',0,0)")

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let{
            it.execSQL("DROP TABLE IF EXISTS WordBookTable")
            onCreate(it)
        }
    }

    override fun onOpen(db:SQLiteDatabase?){
        super.onOpen(db)
    }

}