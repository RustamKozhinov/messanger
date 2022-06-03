package com.template.telegramm.ui.fragments

import com.template.telegramm.R
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

//класс для изменения данных о себе в настройках
class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onResume() {
        super.onResume()
        //сначала подгружаем уже существующию информацию из Firebase
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Данные обновлены!")
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }
}