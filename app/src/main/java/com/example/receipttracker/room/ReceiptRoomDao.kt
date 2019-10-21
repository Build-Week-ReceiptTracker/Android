package com.example.receipttracker.room

import androidx.room.*
import com.example.receipttracker.model.Receipt

@Dao
interface ReceiptRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(receipt: Receipt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(receipt: Receipt)

    @Delete
    fun delete(receipt: Receipt)

    @Query("SELECT * FROM receipt_table")
    fun getAllReceipts(): MutableList<Receipt>

}