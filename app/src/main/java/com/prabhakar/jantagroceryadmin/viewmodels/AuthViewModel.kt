package com.prabhakar.jantagroceryadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.prabhakar.jantagroceryadmin.Utils
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {
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
}