package com.template.telegramm.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.template.telegramm.R
import kotlinx.android.synthetic.main.fragment_enter_phone.*


class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {
    override fun onStart() {
        super.onStart()
        register_btn_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if(register_input_phone_number.text.toString().isEmpty()) {
            Toast.makeText(activity,"введите номер телефона!",Toast.LENGTH_SHORT).show()
        }else {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.dataContainer,EnterCodeFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}