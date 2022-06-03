package com.template.telegramm.ui.fragments

import com.template.telegramm.R
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        settings_input_username.setText(USER.username)
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.hideKeyboard()
    }

    override fun change() {
        mNewUsername = settings_input_username.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast("поле путое")
        } else {
            //проверяем уникальное ли имя пользователя
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast("такой пользователь уже существует")
                    } else {
                        changeUserName()
                    }
                })
            changeUserName()
        }
    }

    //имя которое мы написали и сохранили должно записаться в БД
    private fun changeUserName() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Данные обновлены!")
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }

            }
    }

    //если пользователь поменял username, тогда удаляем старый который больше не используется В firebase
    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Данные обновлены!")
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}