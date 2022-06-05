package com.template.telegramm

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.databinding.ActivityMainBinding
import com.template.telegramm.ui.fragments.ChatsFragment
import com.template.telegramm.ui.objects.AppDrawer
import com.template.telegramm.utillits.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {

            initContacts()
            initFields()
            initFunc()
        }

    }



    private fun initFunc() {
        //проверяет есть ли пользователь
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(),false)
        }else {
            //переход к окну регистрации пользователя
            replaceActivity(RegisterActivity())
        }

    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)

    }

    override fun onStart() {
        super.onStart()
        //когда мы запускаем приложение статус онлайн
        AppStates.updateState(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        //когда мы закрываем приложение статус не в сети
        AppStates.updateState(AppStates.OFFLINE)
    }

    //создание окна где пользователь разрешает чтение контактов
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACT) == PackageManager.PERMISSION_GRANTED) {
            initContacts()
        }
    }
}

