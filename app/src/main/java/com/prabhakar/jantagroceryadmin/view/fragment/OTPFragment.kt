package com.prabhakar.jantagroceryadmin.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.databinding.FragmentOTPBinding
import com.prabhakar.jantagroceryadmin.models.AdminModel
import com.prabhakar.jantagroceryadmin.view.activity.HomeActivity
import com.prabhakar.jantagroceryadmin.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class OTPFragment : Fragment() {
    private lateinit var binding: FragmentOTPBinding
    private lateinit var userNumber: String
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(layoutInflater)

        getUserNUmber()
        customizeEnterOTP()
        goBack()
        sendOTP()
        btnLoginFunctionality()

        return binding.root
    }


    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }
    }

    private fun getUserNUmber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()
        binding.displayUserNumber.text = userNumber
    }

    private fun customizeEnterOTP() {
        val editTexts = arrayOf(
            binding.otp1,
            binding.otp2,
            binding.otp3,
            binding.otp4,
            binding.otp5,
            binding.otp6
        )

        for (index in editTexts.indices) {
            editTexts[index].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(value: Editable?) {
                    if (value?.length == 1) {
                        if (index <= editTexts.size - 2) {
                            editTexts[index + 1].requestFocus()
                        }
                    } else if (value?.length == 0) {
                        if (index > 0) {
                            editTexts[index - 1].requestFocus()
                        }
                    }
                }

            })
        }
    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(), "Sending OTP...")
        authViewModel.apply {
            sendOTP(userNumber, requireActivity())
            lifecycleScope.launch {
                exposeOtp.collect {
                    if (it) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "OTP has been sent")
                    }
                }
            }
        }

    }

    private fun btnLoginFunctionality() {
        val otpS = arrayOf(
            binding.otp1,
            binding.otp2,
            binding.otp3,
            binding.otp4,
            binding.otp5,
            binding.otp6
        )
        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(), "Signing You...")


            val otp = otpS.joinToString("") {
                it.text.toString()
            }
            if (otp.length < otpS.size) {
                Utils.showToast(requireContext(), "Enter a valid OTP")
            } else {
                otpS.forEach {
                    it.text?.clear()
                    it.clearFocus()
                }
                verifyOTP(otp)
            }
        }
    }

    private fun verifyOTP(otp: String) {
        val adminModel = Utils.getUId()?.let {
            AdminModel(it, userNumber)
        }

        authViewModel.signWithPhoneAuth(userNumber, otp, adminModel)
        lifecycleScope.launch {
            authViewModel.exposeVerifyStatus.collect {
                if (it) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Signing Complete !")
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                } else {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "Something went wrong !")
                }
            }
        }
    }

}