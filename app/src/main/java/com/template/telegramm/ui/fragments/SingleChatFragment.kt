package com.template.telegramm.ui.fragments

import android.view.View
import com.google.firebase.database.DatabaseReference
import com.template.telegramm.R
import com.template.telegramm.model.CommonModel
import com.template.telegramm.model.UserModel
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.toolbar_info.view.*


class SingleChatFragment(private val contact: CommonModel) :
    BaseFragment(R.layout.fragment_single_chat) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener//слушатель которые будут слушать измененние БД
    private lateinit var mReceivingUser: UserModel//пользователь который будет отправлять данные в тулбар и обновлять его
    private lateinit var mToolbarInfo: View
    private lateinit var mRefUser: DatabaseReference

    override fun onResume() {
        super.onResume()
        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE
        //получаем измененые данные из БД
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfoToolbar()
        }

        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)

    }

    private fun initInfoToolbar() {
        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(mReceivingUser.photoUrl)
        mToolbarInfo.toolbar_chat_fullname.text = mReceivingUser.fullname
        mToolbarInfo.toolbar_chat_status.text = mReceivingUser.status
    }

    override fun onPause() {
        super.onPause()
        mToolbarInfo.visibility = View.GONE
        //даляем данные
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }
}