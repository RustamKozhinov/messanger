package com.template.telegramm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.template.telegramm.R
import com.template.telegramm.databinding.FragmentChatsBinding
import com.template.telegramm.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {


    private lateinit var mBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(layoutInflater)
        return mBinding.root
    }


    override fun onResume() {
        super.onResume()

    }


}