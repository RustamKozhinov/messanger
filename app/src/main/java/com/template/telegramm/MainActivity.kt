package com.template.telegramm

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.databinding.ActivityMainBinding
import com.template.telegramm.ui.fragments.ChatsFragment
import com.template.telegramm.ui.objects.AppDrawer
import com.template.telegramm.utillits.AUTH
import com.template.telegramm.utillits.initFirebase
import com.template.telegramm.utillits.replaceActivity
import com.template.telegramm.utillits.replaceFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        mBinding = ActivityMainBinding.inflate(layoutInflater)
////        setContentView(mBinding.root)
//    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
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
        mAppDrawer = AppDrawer(this,mToolbar)
        initFirebase()
    }
}

