package ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hw14.R
import com.example.hw14.databinding.FragmentMainBinding
import viewModels.MainViewModel


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    val vModel: MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initView()
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchWordFragment_to_addWordFragment)
        }

        vModel.getCountWordLiveData().observe(requireActivity()){
            binding.textViewCountWord.text="تعداد کلمات : $it"
            if (it==0){
                binding.buttonSearch.isEnabled=false
                binding.editTextSearch.isEnabled=false
            }
        }

        search()

    }

    private fun search() {

        binding.buttonSearch.setOnClickListener {
            if (binding.editTextSearch.text.isNullOrBlank())
                binding.editTextSearch.error="یک کلمه وارد کنید"
            else if(vModel.searchWord(binding.editTextSearch.text.toString())==0){
                val dialog = SearchDialogFragment()
                dialog.show(requireActivity().supportFragmentManager, "NoticeDialogFragment")
            }else{
                val id=vModel.searchWord(binding.editTextSearch.text.toString())
                val bundle= bundleOf("id" to id)
                findNavController().navigate(R.id.action_searchWordFragment_to_detailFragment,bundle)
            }
        }
    }


}