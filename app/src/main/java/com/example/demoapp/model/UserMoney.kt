package com.example.demoapp.model

data class UserMoney (
    var id: Int = 0,
    var userID: Int = 0,
    var moneyAdd: Int = 0,
    var note: String = "",
    var date: String = ""

) {
    override fun toString(): String {
        return "$note\n +${String.format("%,d",moneyAdd)} VND - $date"
    }
}
