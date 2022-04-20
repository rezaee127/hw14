package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hw14.R
import com.example.hw14.databinding.FragmentDetailBinding
import model.Word
import viewModels.MainViewModel


class DetailFragment : Fragment() {
   lateinit var binding: FragmentDetailBinding
   val vModel: MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        val id=requireArguments().getInt("id")

        vModel.getWord(id).let {
            binding.editTextWord.setText(it.word)
            binding.editTextMeaning.setText(it.Meaning)
            binding.editTextSynonyms.setText(it.synonyms)
            binding.editTextExample.setText(it.example)
            binding.editTextDescription.setText(it.description)
        }


        binding.buttonEdit.setOnClickListener {

            when{
                binding.editTextWord.text.isNullOrBlank()-> binding.editTextWord.error="کلمه را وارد کنید"
                binding.editTextMeaning.text.isNullOrBlank()-> binding.editTextMeaning.error="معنی را وارد کنید"
                binding.editTextSynonyms.text.isNullOrBlank()-> binding.editTextSynonyms.error="مترادف را وارد کنید"

                else -> {
                    vModel.update(
                        Word(
                            id, binding.editTextWord.text.toString(),
                            binding.editTextMeaning.text.toString(),
                            binding.editTextSynonyms.text.toString(),
                            binding.editTextExample.text.toString(),
                            binding.editTextDescription.text.toString()
                        )
                    )
                    Toast.makeText(requireContext(), "ویرایش کلمه انجام شد", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
                }
        }

        binding.buttonDelete.setOnClickListener {
            vModel.deleteWord(Word(id,binding.editTextWord.text.toString(),
                binding.editTextMeaning.text.toString(),
                binding.editTextSynonyms.text.toString(),
                binding.editTextExample.text.toString(),
                binding.editTextDescription.text.toString()))

            Toast.makeText(requireContext(),"حذف کلمه انجام شد", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
        }
    }

}