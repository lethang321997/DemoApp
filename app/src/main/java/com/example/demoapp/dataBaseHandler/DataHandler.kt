package com.example.demoapp.dataBaseHandler

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.demoapp.model.User
import com.example.demoapp.model.UserMoney



class DataHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        const val DATABASE_NAME = "MyDB"
        const val TABLE_NAME = "UserTable"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_USER = "username"
        const val COL_PASS = "password"
        const val COL_MONEY = "money"

        const val TABLE_MONEY = "AddMoneyTable"
        const val COL_ID_MONEY = "id_money"
        const val COL_ID_USER = "id_user"
        const val COL_NOTE = "note"
        const val COL_MONEY_ADD = "money_add"
        const val COL_DATE = "date"

        const val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_USER + " VARCHAR(256)," +
                COL_PASS + " VARCHAR(256)," +
                COL_MONEY + " INTEGER DEFAULT 0)"
        const val createTableMoney = "CREATE TABLE IF NOT EXISTS " + TABLE_MONEY + "(" +
                COL_ID_MONEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ID_USER + " INTEGER," +
                COL_MONEY_ADD + " INTEGER," +
                COL_NOTE + " VARCHAR(256)," +
                COL_DATE + " VARCHAR(256)," +
                "FOREIGN KEY(" + COL_ID_USER + ") REFERENCES " + TABLE_NAME + "(" + COL_ID + "))"
    }

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTable)
        db?.execSQL(createTableMoney)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun createTable(){
        db.execSQL(createTable)
        db.execSQL(createTableMoney)
    }

    fun insertData(user: User) {
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_USER, user.username)
        cv.put(COL_PASS, user.password)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertDataMoney(userMoney: UserMoney) {
        val cv = ContentValues()
        cv.put(COL_ID_USER, userMoney.userID)
        cv.put(COL_MONEY_ADD, userMoney.moneyAdd)
        cv.put(COL_NOTE, userMoney.note)
        cv.put(COL_DATE, userMoney.date)
        val result = db.insert(TABLE_MONEY, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }


    fun readAllUser(): ArrayList<User> {
        val users = ArrayList<User>()
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from $TABLE_NAME", null)
        } catch (e: SQLException) {
            db.execSQL(createTable)
            return ArrayList()
        }

        var id: Int
        var name: String
        var username: String
        var password: String
        var money: Int
        if (cursor?.moveToFirst() == true) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                username = cursor.getString(cursor.getColumnIndex(COL_USER))
                password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                money = cursor.getInt(cursor.getColumnIndex(COL_MONEY))
                users.add(
                    User(
                        id,
                        name,
                        username,
                        password,
                        money
                    )
                )
                cursor.moveToNext()
            }
        }
        return users
    }


    fun readAllUserMoney(idUser: Int): ArrayList<UserMoney> {
        val moneyList = ArrayList<UserMoney>()
        val cursor: Cursor?
        try {
            cursor = db.rawQuery("select * from $TABLE_MONEY where $COL_ID_USER='$idUser'", null)
        } catch (e: SQLException) {
            db.execSQL(createTableMoney)
            return ArrayList()
        }

        var id: Int
        var note: String
        var date: String
        var money: Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id = cursor.getInt(cursor.getColumnIndex(COL_ID_MONEY))
                note = cursor.getString(cursor.getColumnIndex(COL_NOTE))
                money = cursor.getInt(cursor.getColumnIndex(COL_MONEY_ADD))
                date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                moneyList.add(
                    UserMoney(
                        id,
                        idUser,
                        money,
                        note,
                        date
                    )
                )
                cursor.moveToNext()
            }
        }
        return moneyList
    }

    fun updateMoney(id: Int, money: Int) {
        val cv = ContentValues()
        cv.put(COL_MONEY, money)
        db.update(TABLE_NAME, cv, "id=$id", null)
    }

    fun editMoney(id: Int, idMoney: Int, note: String, money: Int, moneyEdit: Int) {
        val cv = ContentValues()
        cv.put(COL_NOTE, note)
        cv.put(COL_MONEY_ADD, moneyEdit)
        db.update(TABLE_MONEY, cv, "id_money=$idMoney", null)
        updateMoney(id, money)
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    fun updateName(id: Int, name: String) {
        val cv = ContentValues()
        cv.put(COL_NAME, name)
        db.update(TABLE_NAME, cv, "id=$id", null)
    }

    fun updatePass(id: Int, pass: String) {
        val cv = ContentValues()
        cv.put(COL_PASS, pass)
        db.update(TABLE_NAME, cv, "id=$id", null)
    }

    fun deleteUser(id: Int) {
        db.delete(TABLE_NAME, "id=$id", null)
    }


    fun checkUser(username: String): Boolean {
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from $TABLE_NAME where $COL_USER='$username'",
                null
            )
        } catch (e: SQLException) {

        }
        if (cursor!!.moveToFirst()) {
            return true
        }
        return false
    }


    fun searchUser(username: String, password: String): User? {
        val user: User
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from $TABLE_NAME where $COL_USER='$username' and $COL_PASS='$password'",
                null
            )
        } catch (e: SQLException) {

        }

        val id: Int
        val name: String
        val username: String
        val password: String
        val money: Int

        if (cursor!!.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            username = cursor.getString(cursor.getColumnIndex(COL_USER))
            password = cursor.getString(cursor.getColumnIndex(COL_PASS))
            money = cursor.getInt(cursor.getColumnIndex(COL_MONEY))
            user = User(
                id,
                name,
                username,
                password,
                money
            )
            return user
        }
        return null
    }


    fun getUser(id: Int): User? {
        val user: User
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from $TABLE_NAME where $COL_ID='$id'",
                null
            )
        } catch (e: SQLException) {

        }

        val id: Int
        val name: String
        val username: String
        val password: String
        val money: Int

        if (cursor!!.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID))
            name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            username = cursor.getString(cursor.getColumnIndex(COL_USER))
            password = cursor.getString(cursor.getColumnIndex(COL_PASS))
            money = cursor.getInt(cursor.getColumnIndex(COL_MONEY))
            user = User(
                id,
                name,
                username,
                password,
                money
            )
            return user
        }
        return null
    }

    fun deleteMoney(idMoney: Int, id: Int, money: Int) {
        val cv = ContentValues()
        cv.put(COL_MONEY, money)
        db.update(TABLE_NAME, cv, "id=$id", null)
        db.delete(TABLE_MONEY, "id_money=$idMoney", null)
    }

    fun deleteTable(){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MONEY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
}