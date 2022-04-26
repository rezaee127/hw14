package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(@PrimaryKey(autoGenerate=true)  var id:Int, var word:String, var Meaning:String,
                var synonyms:String, var example:String, var description:String,
                var isFavorite:Boolean=false,var voiceRecorded: Boolean =false,
                var fileName:String="")