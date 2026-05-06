package lv.eduardspozarickis.ubiquititestassignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lv.eduardspozarickis.ubiquititestassignment.data.local.dao.ProductDao
import lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product

@Database(
    entities = [
        Product::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "ubiquiti-database")
        .build()