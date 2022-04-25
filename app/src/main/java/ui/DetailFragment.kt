package ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
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
        goneEditTexts()

        vModel.getWord(id).let {
            binding.textViewWord.text = it.word
            binding.textViewMeaning.text = it.Meaning
            binding.textViewSynonyms.text = it.synonyms
            binding.textViewExample.text = it.example
            binding.textViewDescription.text = it.description
        }

        binding.buttonEdit.setOnClickListener {
            edit(id)
        }
        delete(id)
        back()
        goToWikipedia()
    }


    private fun goToWikipedia() {
        binding.buttonGoToWikipedia.setOnClickListener {
            val word= binding.textViewWord.text.toString()
            val bundle=bundleOf("word" to  word)
            findNavController().navigate(R.id.action_detailFragment_to_goToWikipediaFragment, bundle)
        }
    }


    private fun back() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
        }
    }


    private fun edit(id:Int) {
        visibleEditTexts()
        goneViews()

        vModel.getWord(id).let {
            binding.editTextWord.setText(it.word)
            binding.editTextMeaning.setText(it.Meaning)
            binding.editTextSynonyms.setText(it.synonyms)
            binding.editTextExample.setText(it.example)
            binding.editTextDescription.setText(it.description)
        }



        binding.buttonEdit.setOnClickListener {
            when {
                binding.editTextWord.text.isNullOrBlank() -> binding.editTextWord.error = "کلمه را وارد کنید"
                binding.editTextMeaning.text.isNullOrBlank() -> binding.editTextMeaning.error = "معنی را وارد کنید"
                binding.editTextSynonyms.text.isNullOrBlank() -> binding.editTextSynonyms.error = "مترادف را وارد کنید"

                else -> {
                    vModel.update(Word(id, binding.editTextWord.text.toString(),
                        binding.editTextMeaning.text.toString(),
                        binding.editTextSynonyms.text.toString(),
                        binding.editTextExample.text.toString(),
                        binding.editTextDescription.text.toString()))
                    Toast.makeText(requireContext(), "ویرایش کلمه انجام شد", Toast.LENGTH_SHORT)
                        .show()
                    //findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
                }
            }
        }

        binding.buttonBackToDetail.setOnClickListener {
            binding.buttonDelete.visibility=View.VISIBLE
            binding.buttonBack.visibility=View.VISIBLE
            binding.buttonGoToWikipedia.visibility=View.VISIBLE
            visibleTextViews()
            initView()
        }
    }


    private fun delete(id:Int){
        binding.buttonDelete.setOnClickListener {
            vModel.deleteById(id)
            Toast.makeText(requireContext(),"حذف کلمه انجام شد", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
        }
    }


    private fun goneEditTexts() {
        binding.editTextWord.visibility=View.GONE
        binding.editTextMeaning.visibility=View.GONE
        binding.editTextSynonyms.visibility=View.GONE
        binding.editTextExample.visibility=View.GONE
        binding.editTextDescription.visibility=View.GONE
        binding.buttonBackToDetail.visibility=View.GONE
        binding.buttonRecord.visibility=View.GONE
    }

    private fun visibleEditTexts() {
        binding.editTextWord.visibility=View.VISIBLE
        binding.editTextMeaning.visibility=View.VISIBLE
        binding.editTextSynonyms.visibility=View.VISIBLE
        binding.editTextExample.visibility=View.VISIBLE
        binding.editTextDescription.visibility=View.VISIBLE
        binding.buttonBackToDetail.visibility=View.VISIBLE
        binding.buttonRecord.visibility=View.VISIBLE
    }

    private fun goneViews(){
        binding.textViewWord.visibility=View.GONE
        binding.textViewMeaning.visibility=View.GONE
        binding.textViewSynonyms.visibility=View.GONE
        binding.textViewExample.visibility=View.GONE
        binding.textViewDescription.visibility=View.GONE

        binding.buttonDelete.visibility=View.INVISIBLE
        binding.buttonBack.visibility=View.GONE
        binding.buttonGoToWikipedia.visibility=View.GONE
        binding.buttonRecord.visibility=View.VISIBLE
    }

    private fun visibleTextViews(){
        binding.textViewWord.visibility=View.VISIBLE
        binding.textViewMeaning.visibility=View.VISIBLE
        binding.textViewSynonyms.visibility=View.VISIBLE
        binding.textViewExample.visibility=View.VISIBLE
        binding.textViewDescription.visibility=View.VISIBLE
    }


}