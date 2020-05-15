package com.example.demoapp.user_screen

import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.common.DemoApp
import com.example.demoapp.model.UserMoney
import com.example.demoapp.R

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserModel : UserContract.Model {
    private val db: DataHandler = DataHandler(DemoApp.context())
    override fun addMoney(id: Int, note: String, money: Int, moneyAdd: Int) {
        val formatter =
            DateTimeFormatter.ofPattern(DemoApp.context().resources.getString(R.string.format_date))
        val userMoney = UserMoney(
            userID = id,
            note = note,
            moneyAdd = moneyAdd,
            date = LocalDateTime.now().format(formatter)
        )
        db.insertDataMoney(userMoney)
        val newMoney = money + moneyAdd
        db.updateMoney(id, newMoney)
    }

}