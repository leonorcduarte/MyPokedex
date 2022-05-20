package com.example.mypokedex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.mypokedex.R
import com.example.mypokedex.databinding.BaseActivityLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: BaseActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.base_activity_layout)

        initScreen()
    }

    private fun initScreen() {
        binding.mainToolbar.apply {
            title = getString(R.string.app_name)
            setTitleTextColor(resources.getColor(R.color.white, theme))
        }

        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.pokeListFragment -> binding.mainToolbar.title = getString(R.string.app_name)
                R.id.pokeDetailFragment -> binding.mainToolbar.title = getString(R.string.detail)
            }
        }
    }
}