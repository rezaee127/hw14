package ui

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.hw14.R
import com.example.hw14.databinding.FragmentDetailBinding
import model.Word
import viewModels.MainViewModel
import java.io.IOException


class DetailFragment : Fragment() {
   lateinit var binding: FragmentDetailBinding
   val vModel: MainViewModel by activityViewModels()
    var isFavorite=false
    var flagStartPlaying=true
    var flagStartRecording = true
    var voiceRecorded=false
    private var fileName=""
    private var player: MediaPlayer? = null
    private var recorder: MediaRecorder? = null
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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val id=requireArguments().getInt("id")
        goneEditTexts()

        vModel.getWord(id).observe(viewLifecycleOwner){
            binding.textViewWord.text = "کلمه : ${it.word}"
            binding.textViewMeaning.text ="معنی : ${it.Meaning}"
            binding.textViewSynonyms.text ="مترادف : ${it.synonyms}"
            binding.textViewExample.text ="مثال : ${it.example}"
            binding.textViewDescription.text = "توضیحات : ${it.description}"
            isFavorite=it.isFavorite
            favorite()
        }

        binding.buttonEdit.setOnClickListener {
            edit(id)
        }
        delete(id)
        back()
        goToWikipedia(id)
        playVoice(id)
        recordAudio()
    }


    private fun edit(id:Int) {
        visibleEditTexts()
        goneViews()

        vModel.getWord(id).observe(viewLifecycleOwner){
            binding.editTextWord.setText( it.word)
            binding.editTextMeaning.setText( it.Meaning)
            binding.editTextSynonyms.setText( it.synonyms)
            binding.editTextExample.setText( it.example)
            binding.editTextDescription.setText( it.description)
            isFavorite=it.isFavorite
            voiceRecorded=it.voiceRecorded
            favorite()
        }

        binding.buttonFavorite.setOnClickListener {
            isFavorite=!isFavorite
            favorite()
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
                        binding.editTextDescription.text.toString(),isFavorite,voiceRecorded))

                    Toast.makeText(requireContext(), "ویرایش کلمه انجام شد", Toast.LENGTH_SHORT)
                        .show()
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

    private fun favorite() {
        if (isFavorite)
            binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }



    private fun goToWikipedia(id:Int) {
        binding.buttonGoToWikipedia.setOnClickListener {
            var wordToWikiPedia= ""
                vModel.getWord(id).observe(viewLifecycleOwner){
                    wordToWikiPedia=it.word
                    val bundle=bundleOf("word" to  wordToWikiPedia)
                    findNavController().navigate(R.id.action_detailFragment_to_goToWikipediaFragment, bundle)
                }

        }
    }


    private fun back() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
        }
    }


    private fun delete(id:Int){
        binding.buttonDelete.setOnClickListener {
            vModel.deleteById(id)
            Toast.makeText(requireContext(),"حذف کلمه انجام شد", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_detailFragment_to_searchWordFragment)
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

        binding.buttonRecord.setImageResource(when (flagStartRecording) {
            true -> R.drawable.ic_baseline_mic_off_24
            false -> R.drawable.ic_baseline_mic_24
        })

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
            try {
                stop()
                release()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "stop() failed")
            }
        }
        voiceRecorded=true
        recorder = null
    }







    private fun playVoice(id:Int) {
        binding.buttonPlay.setOnClickListener {
            vModel.getWord(id).observe(viewLifecycleOwner){
                if (it.voiceRecorded){
                    fileName="${requireActivity().externalCacheDir?.absolutePath}/${it.word}.3gp"
                    startPlay()
                }else
                    Toast.makeText(requireContext(),"برای این کلمه صدایی ضبط نشده است",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun startPlay() {
        onPlay(flagStartPlaying)
        binding.buttonPlay.setImageResource(when (flagStartPlaying) {
            true -> R.drawable.ic_baseline_stop_24
            false -> R.drawable.ic_baseline_play_arrow_24
        })
        flagStartPlaying = !flagStartPlaying
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
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