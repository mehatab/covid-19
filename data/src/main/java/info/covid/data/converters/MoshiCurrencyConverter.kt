package info.covid.data.converters


import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import info.covid.data.models.Currency

class MoshiCurrencyConverter {
    @FromJson
    fun toCurrency(string: String?): Currency? {
        return Currency.fromCode(string)
    }

    @ToJson
    fun toString(currency: Currency?): String? {
        return currency?.code
    }
}