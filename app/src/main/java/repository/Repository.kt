package repository

import android.content.Context
import androidx.lifecycle.LiveData
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

    fun getCountWordLiveData(): LiveData<Int>?{
        return  wordDao?.getCountWordLiveData()
    }

    fun search(word:String):Int?{
        return wordDao?.search(word)
    }

    fun getWord(id:Int): Word?{
       return wordDao?.getWord(id)
    }

    fun update(word: Word){
        wordDao?.update(word)
    }

    fun deleteWord(word:Word){
        wordDao?.delete(word)
    }


}