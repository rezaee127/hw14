package com.example.hw14.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.hw14.databinding.FragmentGoToWikipediaBinding



class GoToWikipediaFragment : Fragment() {
    lateinit var binding : FragmentGoToWikipediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentGoToWikipediaBinding.inflate(inflater,container,false)
        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_show_wikipedia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        val regex = Regex("^[ آابپتثجچحخدذرزژسشصضطظعغفقکگلمنوهیئ]+$")
        val word = requireArguments().getString("word")
        if (regex.matches(word.toString()))
            binding.webView.loadUrl("https://fa.wikipedia.org/wiki/${word}")
        else
            binding.webView.loadUrl("https://en.wikipedia.org/wiki/${word}")

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()

        binding.webView.canGoBack()
        binding.webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.webView.canGoBack()) {
                binding.webView.goBack()
                return@OnKeyListener true
            }
            false
        })
    }


//    public fun onMyKeyDown( key:Int,  event:KeyEvent) {
//        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
//            && key == KeyEvent.KEYCODE_BACK
//            && event.repeatCount == 0
//        ) {
//            activity?.let {
//                val builder = AlertDialog.Builder(it)
//                builder.setTitle("Title")
//                    .setMessage("Do you really want to Exit?")
//                    .setPositiveButton("yes",
//                        DialogInterface.OnClickListener { dialog, id ->
//                            fun onClick(dialog: DialogInterface, whichButton: Int) {
//                                requireActivity().finish()
//                            }
//                        })
//
//                    .setNegativeButton("No", null).show()
//                builder.create()
//            } ?: throw IllegalStateException("Activity cannot be null")
//        }
//    }



//        binding.webView.webViewClient = object : WebViewClient(){
//            override fun onReceivedError(
//                view: WebView?,
//                request: WebResourceRequest?,
//                error: WebResourceError?
//            ) {
//                showMyDialog("Error",
//                    "No internet connection. Please check your connection.",
//                    requireActivity())
//                super.onReceivedError(view, request, error)
//            }
//        }
//
//    }
//
//    private fun showMyDialog(title: String, message: String, context: Context) {
//        val dialog = AlertDialog.Builder(context)
//        dialog.setTitle(title)
//        dialog.setMessage(message)
//        dialog.setNegativeButton("Cancel") { _, _ ->
//            requireActivity().finish()
//        }
//        dialog.setNeutralButton("Settings") { _, _ ->
//            startActivity(Intent(Settings.ACTION_SETTINGS))
//        }
//        dialog.setPositiveButton("Retry") { _, _ ->
//            requireActivity().recreate()
//        }
//        dialog.create().show()
//    }

}