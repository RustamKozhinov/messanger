package com.template.telegramm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.template.telegramm.databinding.ActivityMainBinding
import com.template.telegramm.ui.fragments.ChatsFragment
import com.template.telegramm.ui.objects.AppDrawer

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)

    }

    override fun onStart() {
        super.onStart()
        initFields()
    }
    private fun initFunc() {
        setSupportActionBar(mToolbar)
        mAppDrawer.create()
        //when launch app, setup fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer, ChatsFragment()).commit()

    }



    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this,mToolbar)
    }


}