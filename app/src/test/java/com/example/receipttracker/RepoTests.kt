package com.example.receipttracker

import androidx.lifecycle.MutableLiveData
import com.example.receipttracker.api.ReceiptApiDao
import com.example.receipttracker.model.Receipt
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class RepoTests {
    private val repo: ReceiptRepository = ReceiptRepository()
    private val apiDaoMock = Mockito.mock(ReceiptApiDao::class.java)
    private val receipt = Mockito.mock(Receipt::class.java)

    @Test
    fun testLogin(){
        val username = "User"
        val password = "Password"
        val expected: MutableLiveData<Boolean>? = MutableLiveData(true)

        `when`(apiDaoMock?.login(username, password)).thenReturn(expected)

        val result = repo.login(username, password, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testAddReceipt(){
        val username = "User"
        val expected: MutableLiveData<String>? = MutableLiveData("String")

        `when`(apiDaoMock.addReceipt(username, receipt)).thenReturn(expected)

        val result = repo.addReceipt(username, receipt, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testGetAllReceipts(){
        val token = "token"
        val expected: MutableLiveData<MutableList<Receipt>>? = MutableLiveData(mutableListOf(receipt))

        `when`(apiDaoMock.getAllReceipts(token)).thenReturn(expected)

        val result = repo.getAllReceipts(token, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testDeleteReceipt(){
        val token = "token"
        val id = 0
        val expected: MutableLiveData<String>? = MutableLiveData("Expected")

        `when`(apiDaoMock.deleteReceipt(token, id)).thenReturn(expected)

        val result = repo.deleteReceipt(token, id, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testEditReceipt(){
        val token = "token"
        val id = 0
        val expected: MutableLiveData<String>? = MutableLiveData("Expected")

        `when`(apiDaoMock.editReceipt(token, id, receipt)).thenReturn(expected)

        val result = repo.editReceipt(token, id, receipt, apiDaoMock)

        assertEquals(expected, result)
    }
}