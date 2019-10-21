package com.example.receipttracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.receipttracker.model.Receipt

@Database(entities = [Receipt::class], exportSchema = true, version = 1)
abstract class ReceiptDatabase : RoomDatabase() {

    abstract fun receiptDao(): ReceiptRoomDao

    companion object {
        private var instance: ReceiptDatabase? = null

        fun getInstance(context: Context): ReceiptDatabase? {

            if (instance == null) {
                synchronized(ReceiptDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReceiptDatabase::class.java, "receipt_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}