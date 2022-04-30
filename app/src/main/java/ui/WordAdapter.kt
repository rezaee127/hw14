package ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw14.databinding.WordRowItemBinding
import model.Word

class WordAdapter(var onClickItem:(Int)->Unit) : ListAdapter<Word, WordAdapter.ViewHolder>(WordDiffCallback) {

    class ViewHolder(val binding: WordRowItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(word:Word,onClickItem: (Int) -> Unit){
            binding.word=word

            binding.wordRowItems.setOnClickListener {
                onClickItem(word.id)
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val binding = WordRowItemBinding.inflate(LayoutInflater.from(viewGroup.context)
            , viewGroup, false)
        return ViewHolder(binding)

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
