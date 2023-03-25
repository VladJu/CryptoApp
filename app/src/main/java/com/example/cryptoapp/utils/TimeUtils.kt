package com.example.cryptoapp.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        fun convertTimestampToTime(timestamp: Long?): String {
            if (timestamp == null) return ""
            //1_ получаем объект timestamp-который потом мб преобразовывать в любое вермя и дату
            val stamp = Timestamp(timestamp * 1000)//получ сек с 1970
            //2)получаем конкретную дату
            val data = Date(stamp.time)
            //3)создам паттерн в соответсвии с которым будем поулчать время (часы:время:сек)
            val pattern =
                "HH:mm:ss" //если напишем часы с большой буквы то это будет 24 формат, с маленькой 12часовой
            val simpleDateFormat = SimpleDateFormat(
                pattern,
                Locale.getDefault()
            )//чтобы вернул текущее метоположение пользователя
            //Делаем так чтобы время было в нашей временной зоне GMT +3
            simpleDateFormat.timeZone =
                TimeZone.getDefault() // вернет временную зону где находиться польхователь
            return simpleDateFormat.format(data)
        }
    }
}