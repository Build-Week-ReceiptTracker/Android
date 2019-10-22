package com.example.receipttracker.room

import androidx.room.*
import com.example.receipttracker.model.Receipt

@Dao
interface ReceiptRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(receipt: Receipt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(receipt: Receipt)

    @Query("DELETE FROM receipt_table")
    fun deleteAllReceipts()

    @Query("DELETE FROM receipt_table WHERE id = :id")
    fun deleteReceiptById(id: Int)

    @Query("SELECT * FROM receipt_table")
    fun getAllReceipts(): MutableList<Receipt>

}