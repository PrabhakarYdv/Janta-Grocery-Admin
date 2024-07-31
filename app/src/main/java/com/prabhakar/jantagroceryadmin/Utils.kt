package com.prabhakar.jantagroceryadmin

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.prabhakar.jantagroceryadmin.databinding.ProgressBarBinding

object Utils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }

    private var dialog: AlertDialog? = null
    fun showDialog(context: Context, message: String) {
        val progressBar = ProgressBarBinding.inflate(LayoutInflater.from(context))
        progressBar.message.text = message
        dialog = AlertDialog.Builder(context)
            .setView(progressBar.root)
            .setCancelable(false)
            .create()
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    private var firebaseAuthInstance: FirebaseAuth? = null

    fun getFirebaseAuthInstance(): FirebaseAuth {
        if (firebaseAuthInstance == null) {
            firebaseAuthInstance = FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

    fun getUId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun setStatusBarColor(activity: Activity, color: Int) {
        activity?.window?.apply {
            val color = ContextCompat.getColor(activity, color)
            statusBarColor = color
        }
    }
}