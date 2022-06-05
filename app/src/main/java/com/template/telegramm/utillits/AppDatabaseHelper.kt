package com.template.telegramm.utillits

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.provider.ContactsContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.template.telegramm.model.CommandModel
import com.template.telegramm.model.User

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var CURRENT_UID: String
lateinit var REF_STORAGE_ROOT: StorageReference

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"
const val NODE_PHONES = "phones"
const val NODE_PHONES_CONTACTS = "phone_contacts"

const val CHILD_ID = "id"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_STATE = "state"

//когда мы хотим изменить имя и фамилию в поле EditText должно быть старое имя и фамилия которую мы возьмем из БД
const val CHILD_FULLNAME = "fullname"
const val CHILD_BIO = "bio"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}


inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putImageToStorage(uri: Uri?, path: StorageReference, crossinline function: () -> Unit) {
    if (uri != null) {
        path.putFile(uri)
            .addOnSuccessListener { function() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }
}

inline fun initUser(crossinline function: () -> Unit) {
    //обращаемся к БД
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(User::class.java) ?: User()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })
}

@SuppressLint("Range")
fun initContacts() {
    if (checkPermission(Manifest.permission.READ_CONTACTS)) {
        var arrayContacts = arrayListOf<CommandModel>()
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
                val newModel = CommandModel()
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

fun updatePhoneToDatabase(arrayContacts: ArrayList<CommandModel>) {
    REF_DATABASE_ROOT.child(NODE_PHONES).addListenerForSingleValueEvent(AppValueEventListener {
        it.children.forEach { snapshot ->
            arrayContacts.forEach { contact ->
                if (snapshot.key == contact.phone) {
                    REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
                        .child(snapshot.value.toString()).child(CHILD_ID)
                        .setValue(snapshot.value.toString())
                        .addOnFailureListener { showToast(it.message.toString()) }
                }
            }
        }
    })
}

fun DataSnapshot.getCommandModel(): CommandModel =
    this.getValue(CommandModel::class.java) ?: CommandModel()


