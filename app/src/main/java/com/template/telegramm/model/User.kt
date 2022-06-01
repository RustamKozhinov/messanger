package com.template.telegramm.model

data class User(
    val id: String = "",
    var username: String = "",
    var bio: String = "",//информация о пользователе
    var fullname: String = "",
    var status: String = "",
    var phone: String = "",
    var photoUrl: String = ""
)