package com.template.telegramm.utillits

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/*
* Файл где будут храниться все разрешения
* */

//разрешение на чтение контактов
const val READ_CONTACT = Manifest.permission.READ_CONTACTS
const val PERMISSION_REQUEST = 200

fun checkPermission(permission: String): Boolean {
    //если SDK >= 23 и Разрешение небыло выдано еще тогда мы запрашиваем разренение у пользователя
    return if (Build.VERSION.SDK_INT >= 23
        && ContextCompat.checkSelfPermission(APP_ACTIVITY,permission) != PackageManager.PERMISSION_GRANTED) {
            //всплывающее окно которое будет запрашивать разрешения у пользователя
            ActivityCompat.requestPermissions(APP_ACTIVITY, arrayOf(permission), PERMISSION_REQUEST)
        false
    }else true
}