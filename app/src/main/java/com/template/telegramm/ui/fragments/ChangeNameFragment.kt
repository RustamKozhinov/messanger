package com.template.telegramm.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.utillits.*
import kotlinx.android.synthetic.main.fragment_change_name.*


class ChangeNameFragment : BaseFragment(R.layout.fragment_change_name) {
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)

        //следующие поля для получении имени и фамилии из БД и саписи в EditText
        val fullnameList = USER.fullname.split(" ")
        settings_input_name.setText(fullnameList[0])
        settings_input_name.setText(fullnameList[1])
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        * Когда мы нажимаем на галочку которая будет появляться только во фрагменте для изменения
        * имя и фамилии мы будем считывать данные и запоминать их в методе changeName
        * При измененние данных они сразу же записываются в БД
        * */
        when(item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    //считываеи данные
    private fun changeName() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()

        if( name.isEmpty()) {
            showToast(getString(R.string.name_is_empty))
        }else {
            val fullname = "$name $surname"
            //далее обращаемся к БД firebase
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener{
                    if (it.isSuccessful){
                        showToast("Данные обновлены!")
                        //обновляем user
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}
