package ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw14.R
import model.Word

class WordAdapter(var onClickItem:(Int)->Unit) : ListAdapter<Word, WordAdapter.ViewHolder>(WordDiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewWord=view.findViewById<TextView>(R.id.textViewWord)
        val textViewMeaning=view.findViewById<TextView>(R.id.textViewMeaning)
        val wordRowItems=view.findViewById<View>(R.id.wordRowItems)

        fun bind(word:Word,onClickItem: (Int) -> Unit){
            textViewWord.text=word.word
            textViewMeaning.text=word.Meaning
            wordRowItems.setOnClickListener {
                onClickItem(word.id)
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.word_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //viewHolder.buttonDoctorName.text = dataSet[position].name
        viewHolder.bind( getItem(position),onClickItem)

    }


    object WordDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
