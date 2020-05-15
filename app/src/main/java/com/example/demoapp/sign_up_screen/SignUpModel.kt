package com.example.demoapp.sign_up_screen


import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.common.DemoApp
import com.example.demoapp.model.User

class SignUpModel : SignUpContract.Model{
    private val db: DataHandler = DataHandler(DemoApp.context())
    override fun signUp(
        textName: String,
        textUsername: String,
        textPass: String
    ): User? {
        val user = User(
            name = textName,
            username = textUsername,
            password = textPass
        )
        db.insertData(user)
        return db.searchUser(textUsername, textPass)
    }

    override fun checkEmpty(textInput: String): Boolean {
        return textInput.isEmpty()
    }

    override fun checkMatchPass(textPass: String, textRePass: String): Boolean {
        return textPass == textRePass
    }

    override fun checkExistUser(textUsername: String): Boolean {
        return db.checkUser(textUsername)
    }
}