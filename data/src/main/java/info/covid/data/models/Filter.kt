package info.covid.data.models


data class Filter(
    val stateName: String? = null,
    val cityName: String? = null,
    val categories: ArrayList<String>? = null
) {
    fun getStateOrNull(): String? {
        return if (stateName == null || stateName.isEmpty())
            null else stateName
    }

    fun getCityOrNull(): String? {
        return if (cityName == null || cityName.isEmpty())
            null else cityName
    }

    fun getCatOrNull(): ArrayList<String>? {
        return if (categories == null || categories.isEmpty())
            arrayListOf() else categories
    }
}