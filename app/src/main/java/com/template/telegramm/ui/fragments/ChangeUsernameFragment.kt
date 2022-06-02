package com.template.telegramm.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*

class ChangeUsernameFragment : BaseFragment(R.layout.fragment_change_username) {
    lateinit var mNewUsername: String
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        settings_input_username.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        * Когда мы нажимаем на галочку которая будет появляться только во фрагменте для изменения
        * имя и фамилии мы будем считывать данные и запоминать их в методе changeName
        * При измененние данных они сразу же записываются в БД
        * */
        when (item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() {
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
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
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