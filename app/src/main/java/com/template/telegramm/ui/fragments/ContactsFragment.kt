package com.template.telegramm.ui.fragments

import com.template.telegramm.R
import com.template.telegramm.utillits.APP_ACTIVITY

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Contacts"

    }

}