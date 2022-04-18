package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.hw14.databinding.FragmentAddWordBinding
import model.Word
import viewModels.MainViewModel


class AddWordFragment : Fragment() {
    lateinit var binding:FragmentAddWordBinding
    val vModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddWordBinding.inflate (inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        binding.button.setOnClickListener {

            when{
                binding.editTextWord.text.isNullOrBlank()-> binding.editTextWord.error="کلمه را وارد کنید"
                binding.editTextMeaning.text.isNullOrBlank()-> binding.editTextMeaning.error="معنی را وارد کنید"
                binding.editTextSynonyms.text.isNullOrBlank()-> binding.editTextSynonyms.error="مترادف را وارد کنید"

                else ->{
                    vModel.insert(Word(0,binding.editTextWord.text.toString(),
                        binding.editTextMeaning.text.toString(),binding.editTextSynonyms.text.toString(),
                        binding.editTextExample.text.toString(),binding.editTextDescription.text.toString()))

                    Toast.makeText(requireActivity(),"کلمه ذخیره شد", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}