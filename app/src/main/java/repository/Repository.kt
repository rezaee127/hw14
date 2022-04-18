package repository

import android.content.Context
import database.AppDatabase
import database.WordDao
import model.Word

object Repository {
    var db: AppDatabase? = null
    var wordDao: WordDao? = null


    fun initDB(context: Context):AppDatabase? {
        db = AppDatabase.getMyDataBase(context)
        wordDao = db?.wordDao()
        return db
    }

    fun insert(word:Word){
        wordDao?.insert(word)
    }


}