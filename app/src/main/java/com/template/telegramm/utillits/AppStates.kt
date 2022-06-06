package com.template.telegramm.utillits

enum class AppStates(val state:String) {
    ONLINE("В сети"),
    OFFLINE("был недавано"),
    TYPING("Печатает");

    companion object {
        //записывает в приложение состояние из БД
        fun updateState(appStates: AppStates) {
            //обращаемся к БД
            if (AUTH.currentUser != null) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                    .setValue(appStates.state)
                    .addOnFailureListener { showToast(it.message.toString()) }
            }

        }
    }
}