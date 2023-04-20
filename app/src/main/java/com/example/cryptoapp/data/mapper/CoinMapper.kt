package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL+ dto.imageUrl
    )

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfoEntity {
        return CoinInfoEntity(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    //из сети мы получаем коллекцию с названиями валют для того чтобы дальше загрузить полную информацию
    // по полученому списку необходимо всю коллекцию преобразовать с строку, разделенную через запятую
    //и передать в fSyms
    //получаем все данные из коллекци и соединяем в 1 строку через запятую
    fun mapNamesLstToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }

    private fun convertTimestampToTime(timestamp: Int?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp.toLong() * 1000)//получ сек с 1970г
        val data = Date(stamp.time)
        val pattern =
            "HH:mm:ss" //если напишем часы с большой буквы то это будет 24 формат, с маленькой 12часовой
        val simpleDateFormat = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        simpleDateFormat.timeZone =
            TimeZone.getDefault()
        return simpleDateFormat.format(data)
    }

    companion object{
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }


}