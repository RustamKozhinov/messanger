package com.template.telegramm.ui.fragments

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.utillits.AUTH
import com.template.telegramm.utillits.AppTextWatcher
import com.template.telegramm.utillits.replaceActivity
import com.template.telegramm.utillits.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*


class EnterCodeFragment(val mPhoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id,code)
        AUTH.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful) {
                showToast("welcome")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            }else {
                showToast(it.exception?.message.toString())
            }
        }
    }
}