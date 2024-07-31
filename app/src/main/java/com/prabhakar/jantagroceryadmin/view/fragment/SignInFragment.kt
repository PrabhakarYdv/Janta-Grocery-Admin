package com.prabhakar.jantagroceryadmin.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.prabhakar.jantagroceryadmin.R
import com.prabhakar.jantagroceryadmin.Utils
import com.prabhakar.jantagroceryadmin.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        userNumberValidation()
        binding.btnContinue.setOnClickListener {
            onClickBtnContinue()
        }

        Utils.setStatusBarColor(requireActivity(), R.color.blue)

        return binding.root
    }

    private fun userNumberValidation() {
        binding.userNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (value?.length == 10) {
                    binding.btnContinue.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    binding.btnContinue.isEnabled = true
                } else {
                    binding.btnContinue.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray_blue
                        )
                    )
                    binding.btnContinue.isEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun onClickBtnContinue() {
        val number = binding.userNumber.text.toString()
        if (number.isEmpty() || number.length != 10) {
            Utils.showToast(requireContext(), "Please enter a valid number...")
        } else {
            val bundle = Bundle()
            bundle.putString("number", number)
            findNavController().navigate(R.id.action_signInFragment_to_OTPFragment, bundle)

        }
    }


}