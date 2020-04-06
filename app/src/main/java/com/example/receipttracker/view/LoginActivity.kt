package com.example.receipttracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
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

    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        probar_login.visibility = View.GONE

        /*val token: String? = App.sharedPrefs.getString(App.TOKEN_PREF_KEY, "")
        if (token != null && token.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }*/

        button_login.setOnClickListener {
            viewModel.hideSoftKeyboard(this)
            probar_login.visibility = View.VISIBLE
            val user = et_username.text.toString()
            val password = et_password.text.toString()
            viewModel.login(user, password)?.observe(this, Observer {
                if (it != null) {
                    if (it == true) {
                        startActivity(Intent(this, MainActivity::class.java))
                        probar_login.visibility = View.GONE
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                        probar_login.visibility = View.GONE
                    }
                }
            })
        }

        // Handle conductor when user clicks Register button
        button_register.setOnClickListener {
            router = Conductor.attachRouter(this, constraint_parent, savedInstanceState)
            if (!router.hasRootController()){
                router.setRoot(RouterTransaction.with(RegisterController()))
            }
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            startActivity(Intent(this, LoginActivity::class.java))
            //super.onBackPressed()
        }
    }
}
