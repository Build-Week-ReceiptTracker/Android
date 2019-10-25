package com.example.receipttracker

import com.example.receipttracker.controller.RegisterController
import org.junit.Test
import kotlin.test.assertEquals

class RegisterControllerTests {
    private val registerController = RegisterController()

    @Test
    fun testDifferentPasswords(){
        val password1 = "PASSWORD"
        val password2 = "password"
        val expected = false
        val result = registerController.passwordsAreValid(password1, password2)

        assertEquals(expected, result)
    }

    @Test
    fun testMatchingPasswords(){
        val password1 = "password"
        val password2 = "password"
        val expected = true
        val result = registerController.passwordsAreValid(password1, password2)

        assertEquals(expected, result)
    }

    @Test
    fun testEmptyPasswords(){
        val password1 = ""
        val password2 = ""
        val expected = false
        val result = registerController.passwordsAreValid(password1, password2)

        assertEquals(expected, result)
    }
}