package ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hw14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splash()

    }

    private fun splash() {
        supportActionBar?.hide()
        binding.splashIcon.alpha=0f
        binding.splashIcon.animate().setDuration(2000).alpha(1f).withEndAction {
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            binding.fragmentContainerView.visibility= View.VISIBLE
            binding.splashIcon.visibility=View.GONE
            supportActionBar?.show()
        }

    }


//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            ShowWikipediaFragment().onMyKeyDown(keyCode,event)
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//

}