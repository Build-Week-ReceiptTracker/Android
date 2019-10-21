package com.example.receipttracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.RouterTransaction
import com.example.receipttracker.App
import com.example.receipttracker.R
import com.example.receipttracker.controller.RegisterController
import com.example.receipttracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val token: String? = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "")
        if (token != null && token.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener {
            val user = et_username.text.toString()
            val password = et_password.text.toString()
            viewModel.login(user, password)
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Handle conductor when user clicks Register button
        button_register.setOnClickListener {
            val router = Conductor.attachRouter(this, constraint_parent, savedInstanceState)
            if (!router.hasRootController()){
                router.setRoot(RouterTransaction.with(RegisterController()))
            }
        }
    }
}
