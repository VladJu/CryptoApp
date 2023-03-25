package com.example.cryptoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptoapp.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
//в [ ] т.к надо передать массив классов помечаный @Entity
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DATA_BASE_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK){
                db?.let { return it } //если бд не == null, то вернем значение
                //данную переменнуб ввели для того чтобы возвращаемый тип был не nullабельный и не добавляли везде проверку на null
                val instance = Room.databaseBuilder( //создаем новый объект бд
                    context,
                    AppDatabase::class.java,
                    DATA_BASE_NAME
                )
                    .build()//тип переменной AppDatabase
                db = instance
                return instance
            }

        }
    }
    abstract fun coinPriceInfoDao() : CoinPriceInfoDao
}