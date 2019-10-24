package com.example.receipttracker.controller

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.example.receipttracker.App
import com.example.receipttracker.R
import com.example.receipttracker.view.LoginActivity
import com.example.receipttracker.viewmodel.LoginViewModel

class RegisterController : Controller() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.login_main_conductor, container, false)
    }

    override fun onChangeStarted(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeStarted(changeHandler, changeType)

        fun passwordsAreValid(password: String, passwordConfirm: String): Boolean{
            if (password != passwordConfirm){
                Toast.makeText(activity, "Passwords Don't Match", Toast.LENGTH_SHORT).show()
                return false
            }else if(password.isEmpty() || passwordConfirm.isEmpty()){
                Toast.makeText(activity, "Blank Password Field", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        fun getEditTextInput(id: Int): String{
            return view?.findViewById<EditText>(id)?.text.toString()
        }

        view?.findViewById<Button>(R.id.button_conductor_register)?.setOnClickListener {
            val email = getEditTextInput(R.id.et_conductor_email)
            val username = getEditTextInput(R.id.et_conductor_username)
            val password = getEditTextInput(R.id.et_conductor_password)
            val passwordConfirm = getEditTextInput(R.id.et_conductor_password_confirm)

            if (passwordsAreValid(password, passwordConfirm)){
                App.repo?.register(username, password, email)
                startActivity(Intent(activity, LoginActivity::class.java))
            }


        }
    }

}