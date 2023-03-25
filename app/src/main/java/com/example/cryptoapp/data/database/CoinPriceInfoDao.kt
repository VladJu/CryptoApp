package com.example.cryptoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.data.model.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY price DESC")
    fun getPriceList() : LiveData<List<CoinPriceInfo>>


    //метод пойдет в базу данных найдет все совпадения и веренет 1 строку
    //LIMIT 1  - если каким то образом будет две одинаковые записи , то он вернет 1
    @Query("SELECT  * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceInfoAboutCoin(fSym:String) : LiveData<CoinPriceInfo>

    //метод сохраянет подученные из инетрента данные в бд
    //когда будут приходить новые данные старые будут заменяться
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinPriceInfo>)

}