package com.template.telegramm.ui.fragments

import android.view.View
import com.template.telegramm.R
import com.template.telegramm.model.CommandModel
import com.template.telegramm.utillits.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.view.*


class SingleChatFragment(contact: CommandModel) : BaseFragment(R.layout.fragment_single_chat) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.VISIBLE

    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.mToolbar.toolbar_info.visibility = View.GONE
    }
}