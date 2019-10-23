package com.example.receipttracker

import androidx.lifecycle.MutableLiveData
import com.example.receipttracker.model.Receipt
import com.example.receipttracker.viewmodel.MyReceiptsViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MyReceiptsViewModelTests {
    private val repoMock = mock(ReceiptRepository::class.java)
    private val myReceiptsViewModel = MyReceiptsViewModel()
    private val receipt = Receipt("20.00",
        "2019-10-23",
        "Eat Out",
        "Panda Express",
        "fakeUrl.jpg",
        "fake",
        "I was hungry after work",
        0)
    private var liveDataList: MutableLiveData<MutableList<Receipt>>? = MutableLiveData(mutableListOf(receipt))
    private val liveDataString: MutableLiveData<String>? = MutableLiveData("complete")
    private val mockToken = "mockToken"


    @Test
    fun searchExactStringInReceipt(){
        val userSearch = "Panda Express"
        val expected = true
        val result = myReceiptsViewModel.isUserStringInReceipt(receipt, userSearch)

        assertEquals(expected, result)
    }

    @Test
    fun searchUpperCaseStringInReceipt(){
        val userSearch = "PANDA EXPRESS"
        val expected = true
        val result = myReceiptsViewModel.isUserStringInReceipt(receipt, userSearch)

        assertEquals(expected, result)
    }

    @Test
    fun searchLowerCaseStringInReceipt(){
        val userSearch = "panda express"
        val expected = true
        val result = myReceiptsViewModel.isUserStringInReceipt(receipt, userSearch)

        assertEquals(expected, result)
    }

    @Test
    fun searchStringNotInReceipt(){
        val userSearch = "Never"
        val expected = false
        val result = myReceiptsViewModel.isUserStringInReceipt(receipt, userSearch)

        assertEquals(expected, result)
    }

    @Test
    fun searchUrlInReceipt(){
        val userSearch = "fakeUrl.jpg"
        val expected = false
        val result = myReceiptsViewModel.isUserStringInReceipt(receipt, userSearch)

        assertEquals(expected, result)
    }

    @Test
    fun getAllReceiptsTest(){
        `when`(repoMock.getAllReceipts(mockToken)).thenReturn(liveDataList)

        val expected = liveDataList
        val result = myReceiptsViewModel.getAllReceipts(mockToken)

        assertEquals(expected, result)
    }

    @Test
    fun deleteReceiptTestReturnString(){
        val id = receipt.id!!
        `when`(repoMock.deleteReceipt(mockToken, id)).thenReturn(liveDataString)

        val expected = liveDataString
        val result = myReceiptsViewModel.deleteReceipt(mockToken, id)

        assertEquals(expected, result)
    }

//    @Test
//    fun deleteReceiptSuccessfullyTest(){
//        val id = receipt.id!!
//        val repoMock = mock(ReceiptRepository::class.java)
//        `when`(repoMock.deleteReceipt(mockToken, id).thenReturn(liveDataString))
//
//        val expected: MutableLiveData<MutableList<Receipt>>? = MutableLiveData()
//
//        val result = myReceiptsViewModel.deleteReceipt(mockToken, id)
//
//        assertEquals(expected, result)
//    }
}