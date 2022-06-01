package com.template.telegramm.ui.fragments

import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.utillits.AUTH
import com.template.telegramm.utillits.replaceActivity
import com.template.telegramm.utillits.replaceFragment
import com.template.telegramm.utillits.showToast
import kotlinx.android.synthetic.main.fragment_enter_phone.*
import java.util.concurrent.TimeUnit


class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {
    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()
        AUTH = FirebaseAuth.getInstance()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //проверяет есть ли верефикация если все ок то он запускается, ореентирован для реального телефона
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener{
                    if (it.isSuccessful) {
                        showToast("welcome")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    }else {
                        showToast(it.exception?.message.toString())
                    }
                }
            }
            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            //метод запускается тогда когда было отправлено смс
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber,id))
            }
        }
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if(register_input_phone_number.text.toString().isEmpty()) {
            showToast("введите номер телефона!")
        }else {
            authUser()

        }
    }

    //get phone number
    private fun authUser() {
        mPhoneNumber = register_input_phone_number.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallback
        )
    }
}