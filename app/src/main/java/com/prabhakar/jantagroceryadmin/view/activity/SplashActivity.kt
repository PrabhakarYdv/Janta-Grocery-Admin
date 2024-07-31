package com.prabhakar.jantagroceryadmin.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            lifecycleScope.launch {
                viewModel.exposeCurrentUserStatus.collect {
                    if (it) {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                        finish()
                    }
                }
            }
        }, 2000)
    }
}