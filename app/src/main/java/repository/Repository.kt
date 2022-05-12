package repository

import android.content.Context
import androidx.lifecycle.LiveData
import database.AppDatabase
import database.WordDao
import kotlinx.coroutines.runBlocking
import model.Word

object Repository {
    lateinit var db: AppDatabase
    lateinit var wordDao: WordDao


    fun initDB(context: Context):AppDatabase {
        db = AppDatabase.getMyDataBase(context)
        wordDao = db.wordDao()
        return db
    }

    suspend fun insert(word:Word){
        wordDao.insert(word)
    }

    fun getWordList():List<Word>{
        var wordList=listOf<Word>()
        runBlocking {
            wordList=wordDao.getWordList()
        }
        return wordList
    }

    fun getCountWordLiveData(): LiveData<Int>{
        return  wordDao.getCountWordLiveData()
    }

    suspend fun search(word:String):Int{
        return wordDao.search(word)
    }

    suspend fun searchMeaning(Meaning:String):Int{
        return wordDao.searchMeaning(Meaning)
    }

    suspend fun getWord(id:Int): Word{
       return wordDao.getWord(id)
    }

    suspend fun update(word: Word){
        wordDao.update(word)
    }

    suspend fun deleteById(id:Int){
        wordDao.deleteById(id)
    }



//    fun deleteWord(word:Word){
//        wordDao.delete(word)
//    }


}