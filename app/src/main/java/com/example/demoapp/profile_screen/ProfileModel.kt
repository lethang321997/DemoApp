package com.example.demoapp.profile_screen


import com.example.demoapp.dataBaseHandler.DataHandler
import com.example.demoapp.common.DemoApp

class ProfileModel : ProfileContract.Model{
    private val db: DataHandler = DataHandler(DemoApp.context())
    override fun deleteUser(id: Int){
        db.deleteUser(id)
    }

    override fun editName(id: Int, name: String) {
        db.updateName(id, name)
    }

    override fun editPass(id: Int, newPass: String) {
        db.updatePass(id, newPass)
    }

    override fun checkEmpty(textInput: String): Boolean {
        return textInput.isEmpty()
    }

    override fun checkMatchPass(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }
}