package com.template.telegramm.model

data class UserModel(
    val id: String = "",//id
    var username: String = "",// пользовательское имя
    var bio: String = "",//информация о пользователе
    var fullname: String = "",// имя + фамилия
    var status: String = "",//состояние (в сети, был недавно)
    var phone: String = "",//номер телефона
    var photoUrl: String = "empty"//аватарка
)