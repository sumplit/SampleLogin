package id.test.tada.mvvm.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.test.tada.R
import id.test.tada.util.makeStatusBarTransparent
import id.test.tada.util.replaceFragmentInActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        setupFragment()
    }

    fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frame_main_content)
        SplashScreenFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.frame_main_content)
        }
    }

    companion object {
        fun startThisActivity(contex: Context) {
            contex.startActivity(Intent(contex, SplashScreenActivity::class.java))
        }
    }
}