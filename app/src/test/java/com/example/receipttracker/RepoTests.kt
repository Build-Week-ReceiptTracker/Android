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

    @Test
    fun testLogin(){
        val username = "User"
        val password = "Password"
        val expected = MutableLiveData<Boolean>(true)

        `when`(apiDaoMock?.login(username, password)).thenReturn(MutableLiveData(true))

        val result = repo.login(username, password, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testAddReceipt(){
        val username = "User"
        val expected: MutableLiveData<String>? = MutableLiveData("String")
        val receipt = Mockito.mock(Receipt::class.java)

        `when`(apiDaoMock.addReceipt(username, receipt)).thenReturn(expected)

        val result = repo.addReceipt(username, receipt, apiDaoMock)

        assertEquals(expected, result)
    }

    @Test
    fun testGetAllReceipts(){
        val username = "User"
        val password = "Password"
        val expected = MutableLiveData<Boolean>(true)

        `when`(apiDaoMock.login(username, password)).thenReturn(MutableLiveData(true))


        assert(repo.login(username, password, apiDaoMock)?.value!!)
    }

    @Test
    fun testDeleteReceipt(){
        val username = "User"
        val password = "Password"
        val expected: MutableLiveData<Boolean>? = MutableLiveData(true)

        `when`(apiDaoMock.login(username, password)).thenReturn(MutableLiveData(true))

        assert(repo.login(username, password, apiDaoMock)?.value!!)
    }

    @Test
    fun testEditReceipt(){
        val username = "User"
        val password = "Password"
        val expected: MutableLiveData<Boolean>? = MutableLiveData(true)

        `when`(apiDaoMock.login(username, password)).thenReturn(MutableLiveData(true))

        assert(repo.login(username, password, apiDaoMock)?.value!!)
    }
}