package database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Word


@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Query("SELECT COUNT(*) FROM Word" )
    fun getCountQuestion():Int

    @Query("SELECT COUNT(*) FROM Word" )
    fun getCountQuestionLiveData(): LiveData<Int>


    @Query("SELECT * FROM Word WHERE id IN(:id)")
    fun getQuestion(id:Int): Word

}