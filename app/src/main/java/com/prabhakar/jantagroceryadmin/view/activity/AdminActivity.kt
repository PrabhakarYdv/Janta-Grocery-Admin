package com.prabhakar.jantagroceryadmin.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.setStatusBarColor(this, R.color.blue)

        NavigationUI.setupWithNavController(
            binding.bottomMenu,
            Navigation.findNavController(this, R.id.fragmentHomeContainer)
        )
    }
}