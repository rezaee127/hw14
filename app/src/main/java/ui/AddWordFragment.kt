package ui

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hw14.R
import com.example.hw14.databinding.FragmentAddWordBinding
import model.Word
import viewModels.MainViewModel
import java.io.IOException


class AddWordFragment : Fragment() {
    private lateinit var binding:FragmentAddWordBinding
    private val vModel: MainViewModel by activityViewModels()
    var isFavorite=false
    var flagStartRecording = true
    var voiceRecorded=false
    private var recorder: MediaRecorder? = null

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == 200) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) {
           // requireActivity().finishAffinity()
         }
    }




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
        ActivityCompat.requestPermissions(requireActivity(), permissions, 200)

        initView()
    }

    private fun initView() {
        recordAudio()

        binding.buttonFavorite.setOnClickListener {
            binding.buttonFavorite.setIconResource(R.drawable.ic_baseline_favorite_24)
            isFavorite=!isFavorite
        }

        binding.buttonSave.setOnClickListener {
            vModel.searchWord(binding.editTextWord.text.toString()).observe(viewLifecycleOwner){wordId->
                when{
                    binding.editTextWord.text.isNullOrBlank()-> binding.editTextWord.error="کلمه را وارد کنید"
                    wordId!=null->binding.editTextWord.error="این کلمه قبلا وارد شده است"
                    binding.editTextMeaning.text.isNullOrBlank()-> binding.editTextMeaning.error="معنی را وارد کنید"
                    binding.editTextSynonyms.text.isNullOrBlank()-> binding.editTextSynonyms.error="مترادف را وارد کنید"

                    else ->{

                        // binding.buttonSave.setOnClickListener {
                        vModel.insert(Word(0,binding.editTextWord.text.toString(),
                            binding.editTextMeaning.text.toString(),binding.editTextSynonyms.text.toString(),
                            binding.editTextExample.text.toString(),binding.editTextDescription.text.toString(),isFavorite,voiceRecorded))
                        Toast.makeText(requireActivity(),"کلمه ذخیره شد", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_addWordFragment_to_searchWordFragment)
                        //}
                    }
                }
            }

        }
    }

    private fun recordAudio() {

        binding.buttonRecord.setOnClickListener {
            if (binding.editTextWord.text.toString()=="") {
                Toast.makeText(requireContext(), "یک کلمه وارد کنید", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

                startRecord()
            }
        }
    }

    private fun startRecord() {
        onRecord(flagStartRecording)

        binding.buttonRecord.setIconResource(when (flagStartRecording) {
            true -> R.drawable.ic_baseline_mic_off_2
            false -> R.drawable.ic_baseline_mic_2
        })

        binding.buttonRecord.text=
            when (flagStartRecording) {
                true -> "قطع ضبط"
                false -> "ضبط صدا"
            }
        flagStartRecording = !flagStartRecording
    }


    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun startRecording() {
        val name=binding.editTextWord.text.toString()
        val fileName="${requireActivity().externalCacheDir?.absolutePath}/$name.3gp"
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        voiceRecorded=true
        recorder = null
    }


}