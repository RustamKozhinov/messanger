package com.template.telegramm.utillits

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.template.telegramm.R
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.ui.fragments.ChatsFragment

//функция позволяющая не писать Toast а заменить на вызов функции
fun Fragment.showToast(message: String) {
    Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
}

//функция позваоляющая заменить написания Intent на вызов функции
fun AppCompatActivity.replaceActivity(activity:AppCompatActivity) {
    val intent = Intent(this,activity::class.java)
    startActivity(intent)
    this.finish()
}

//функция для того чтобы менять фрагменты
fun AppCompatActivity.replaceFragment(fragment:Fragment) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(
            R.id.dataContainer,
            fragment
        ).commit()
}

//функция для того чтобы изменять фрагменты способная вызываться во фрагменте
fun Fragment.replaceFragment(fragment:Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.dataContainer,
            fragment
        )?.commit()
}