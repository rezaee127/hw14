import android.content.Context
import database.AppDatabase
import database.WordDao

object Repository {
    var db: AppDatabase? = null
    var wordDao: WordDao? = null


    fun initDB(context: Context) {
        db = AppDatabase.getMyDataBase(context)
        wordDao = db?.wordDao()
    }



}