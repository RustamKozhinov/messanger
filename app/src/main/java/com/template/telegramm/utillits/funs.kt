package com.template.telegramm.utillits

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.template.telegramm.MainActivity
import com.template.telegramm.R
import com.template.telegramm.activities.RegisterActivity
import com.template.telegramm.model.CommonModel

//функция позволяющая не писать Toast а заменить на вызов функции
fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

//функция позваоляющая заменить написания Intent на вызов функции
fun AppCompatActivity.replaceActivity(activity: MainActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

//функция позваоляющая заменить написания Intent на вызов функции
fun AppCompatActivity.replaceActivity(activity: RegisterActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

//функция для того чтобы менять фрагменты
fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.dataContainer,
                fragment
            ).commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dataContainer,
                fragment
            ).commit()
    }

}

//функция для того чтобы изменять фрагменты способная вызываться во фрагменте
fun Fragment.replaceFragment(fragment: Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.dataContainer,
            fragment
        )?.commit()
}



//получение фото с помощью пикассо
fun ImageView.downloadAndSetImage(url: String) {
    Picasso.get()
        .load(url)
        .fit()
        .placeholder(R.drawable.default_photo)
        .into(this)
}

@SuppressLint("Range")
fun initContacts() {
    if (checkPermission(Manifest.permission.READ_CONTACTS)) {
        var arrayContacts = arrayListOf<CommonModel>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
        )
        //пробегаемся по курсору и считываем все данные которые у нас есть в БД имя, номер телефона
        cursor?.let {
            while (it.moveToNext()) {
                //считываем контакты с нашей телефонной книги
                val fullname =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                //данные которые мы считали записать в (model.CommandModel.kt)
                val newModel = CommonModel()
                newModel.fullname = fullname
                newModel.phone = phone.replace(Regex("[\\$,-]"), "")
                arrayContacts.add(newModel)
            }
        }
        cursor?.close()
        //после того как создали новую ноду которая после регистрации нового юзера записывает в БД
        // номер телефона и имя
        updatePhoneToDatabase(arrayContacts)

    }
}