package com.template.telegramm.model

data class CommonModel(
    val id: String = "",//id
    var username: String = "",// пользовательское имя
    var bio: String = "",//информация о пользователе
    var fullname: String = "",// имя + фамилия
    var state: String = "",//состояние (в сети, был недавно)
    var phone: String = "",//номер телефона
    var photoUrl: String = "empty",//аватарка

    var text: String = "",
    var type: String = "",
    var from: String = "",
    var timeStamp: Any = ""
)