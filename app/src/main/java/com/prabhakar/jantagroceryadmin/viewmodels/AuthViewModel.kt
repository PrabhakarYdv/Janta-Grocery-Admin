package com.prabhakar.jantagroceryadmin.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.models.AdminModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private val _verificationId = MutableStateFlow<String?>(null)
    private val _otpSent = MutableStateFlow(false)
    val exposeOtp = _otpSent
    private val _isVerifySuccess = MutableStateFlow(false)
    val exposeVerifyStatus = _isVerifySuccess
    private val _isCurrentUser = MutableStateFlow(false)
    val exposeCurrentUserStatus = _isCurrentUser

    init {
        Utils.getFirebaseAuthInstance().currentUser?.let {
            _isCurrentUser.value = true
        }
    }

    fun sendOTP(userNumber: String, activity: Activity) {
        val callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                p1: PhoneAuthProvider.ForceResendingToken
            ) {
                _verificationId.value = verificationId
                _otpSent.value = true
            }
        }

        val option = PhoneAuthOptions.newBuilder(Utils.getFirebaseAuthInstance())
            .setPhoneNumber("91$userNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callBacks)
            .setActivity(activity)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    fun signWithPhoneAuth(userNumber: String, otp: String, adminModel: AdminModel?) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value.toString(), otp)

        Utils.getFirebaseAuthInstance().signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _isVerifySuccess.value = true
                } else {
                    _isVerifySuccess.value = false
                }
            }
    }
}