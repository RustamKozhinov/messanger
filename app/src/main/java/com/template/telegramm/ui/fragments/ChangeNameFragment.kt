package com.template.telegramm.ui.fragments

import com.template.telegramm.R
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        //следующие поля для получении имени и фамилии из БД и саписи в EditText
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size > 1) {
            settings_input_name.setText(fullnameList[0])
            settings_input_name.setText(fullnameList[1])
        } else settings_input_name.setText(fullnameList[0])
    }

    //считываеи данные
    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()

        if (name.isEmpty()) {
            showToast(getString(R.string.name_is_empty))
        } else {
            val fullname = "$name $surname"
            //далее обращаемся к БД firebase
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Данные обновлены!")
                        //обновляем user
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}
