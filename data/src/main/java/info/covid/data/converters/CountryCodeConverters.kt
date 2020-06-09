package info.covid.data.converters

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import info.covid.data.models.Currency
import java.time.OffsetDateTime

object CountryCodeConverters {

    @FromJson
    @JvmStatic
    @TypeConverter
    fun toCurrency(string: String?) : Currency? {
       return Currency.fromCode(string)
    }

    @ToJson
    @JvmStatic
    @TypeConverter
    fun toString(currency: Currency?) : String? {
       return currency?.code
    }

}