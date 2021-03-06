package com.template.telegramm.ui.fragments

import androidx.fragment.app.Fragment
import com.template.telegramm.MainActivity
import com.template.telegramm.utillits.APP_ACTIVITY

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()

        //когда запускается любой фрагмент
        // кроме ChatsFragment тогда запускается этот код и бургер отключается
        APP_ACTIVITY.mAppDrawer.disableDrawer()

    }

    override fun onStop() {
        super.onStop()
        //включаем бургер когда переходим во фрагмент чата
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}