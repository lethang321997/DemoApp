package com.example.demoapp.home_screen


import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.common.DemoApp
import com.example.demoapp.model.User
import com.example.demoapp.common.Constant

class HomeModel : HomeContract.Model {

    private val db: DataHandler = DataHandler(DemoApp.context())

    override fun login(userName: String, password: String): User? {
        return if (userName == Constant.ADMIN && password == Constant.PASSWORD) {
            User(isAdmin = true)
        } else {
            db.searchUser(userName, password)

        }
    }
}