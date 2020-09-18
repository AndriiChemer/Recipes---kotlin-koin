package com.artatech.inkbook.recipes.ui.splash.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.artatech.inkbook.recipes.MainActivity
import com.artatech.inkbook.recipes.R
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        observeData()
        observeError()

        viewModel.onViewCreated()
    }

    private fun observeData() {
        viewModel.data.observe(this) {
            MainActivity.start(this@SplashActivity, it)

        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
        }
    }

    private fun observeError() {
        //TODO
        viewModel.error.observe(this) {
            Toast.makeText(this@SplashActivity, "Error", Toast.LENGTH_LONG).show()
        }
    }
}