package com.template.telegramm.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.utillits.APP_ACTIVITY
import com.template.telegramm.utillits.hideKeyboard

open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        //когда запускается любой фрагмент
        // кроме ChatsFragment тогда запускается этот код и бургер отключается
        (APP_ACTIVITY).mAppDrawer.disableDrawer()
        //отключает клавиатуру
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        * Когда мы нажимаем на галочку которая будет появляться только во фрагменте для изменения
        * имя и фамилии мы будем считывать данные и запоминать их в методе changeName
        * При измененние данных они сразу же записываются в БД
        * */
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }
}