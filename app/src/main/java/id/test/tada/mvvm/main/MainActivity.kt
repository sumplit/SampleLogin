package id.test.tada.mvvm.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import id.test.tada.R
import id.test.tada.databinding.FragmentMainBinding
import id.test.tada.util.obtainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        obtainViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun obtainViewModel(): MainViewModel =
        obtainViewModel(MainViewModel::class.java).apply {

            getUser()

            mUser.observe(this@MainActivity, {
                setNameUser(it.username.orEmpty())

            })
        }

    private fun setNameUser(name: String) {
        val viewHeader = binding.navView.getHeaderView(0)
        val textViewWelcome = viewHeader.findViewById<TextView>(R.id.tvWelcome)
        textViewWelcome.text = getString(R.string.format_welcome, name)
    }

    companion object {
        fun startThisActivity(contex: Context) {
            contex.startActivity(Intent(contex, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }
}