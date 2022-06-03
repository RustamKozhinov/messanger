package com.template.telegramm.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.template.telegramm.R
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.utillits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    //функция чтобы в поля с информацией о пользователе подтягивалась информация из БД
    private fun initFields() {
        settings_bio.text = USER.bio
        settings_fullname.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.status
        settings_user_name.text = USER.username
        //по клику переходим в ChangeUsernameFragment
        settings_btn_change_user_name.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        //по клику переходим в ChangeBioFragment
        settings_btn_change_bio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        //при нажатии на кнопку мы можем устанавливать фото юзера
        settings_change_photo.setOnClickListener { changePhotoUser() }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1,1)
            .setRequestedSize(600,600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY)
    }



    //create option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                //intent запускаем registerActivity
                (activity as RegisterActivity).replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}