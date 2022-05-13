package ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hw14.R
import com.example.hw14.databinding.FragmentMainBinding
import viewModels.MainViewModel


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    val vModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setRecyclerView()

    }

    private fun setRecyclerView() {
        val wordAdapter = WordAdapter({ goToDetailFragment(it) })
        binding.recyclerView.adapter = wordAdapter
        vModel.getWordList().observe(requireActivity()){
            wordAdapter.submitList(it)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun initView() {

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchWordFragment_to_addWordFragment)
        }

        vModel.getCountWordLiveData().observe(requireActivity()) {
            binding.textViewCountWord.text = "تعداد کلمات : $it"
            if (it == 0) {
                binding.buttonSearch.isEnabled = false
                binding.editTextSearch.isEnabled = false
            }
        }

        search()

    }

    private fun search() {

        binding.buttonSearch.setOnClickListener {

            if (binding.editTextSearch.text.isNullOrBlank())
                binding.editTextSearch.error = "یک کلمه وارد کنید"
            else {
                vModel.searchMeaning(binding.editTextSearch.text.toString()).observe(viewLifecycleOwner){ meaningId->

                    vModel.searchWord(binding.editTextSearch.text.toString()).observe(viewLifecycleOwner){ wordId->
                        if (wordId == null && meaningId == null) {
                            val dialog = AlertDialog.Builder(requireContext())
                            dialog.setMessage("The desired word does not exist")
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialog, id ->
                                    })
                            dialog.create()
                            dialog.show()
                        } else if (wordId != null) {
                            //val id = vModel.searchWord(binding.editTextSearch.text.toString())
                            goToDetailFragment(wordId)
                        } else {
                            //val id = vModel.searchMeaning(binding.editTextSearch.text.toString())
                            goToDetailFragment(meaningId)
                        }
                    }
                }
            }
        }
    }

    private fun goToDetailFragment(id: Int) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_searchWordFragment_to_detailFragment, bundle)
    }
}