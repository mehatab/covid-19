package info.covid.data.utils

object Const {
    val statesCodes = HashMap<String, String>().apply {
        put("Chandigarh", "CH")
        put("Delhi", "DL")
        put("Himachal Pradesh", "HP")
        put("Haryana", "HR")
        put("Jammu and Kashmir", "JK")
        put("Andhra Pradesh", "AP")
        put("Kerala", "KL")
        put("Odisha", "OR")
        put("Dadra and Nagar Haveli", "DN")
        put("Karnataka", "KA")
        put("Maharashtra", "MH")
        put("Andaman and Nicobar Islands", "AN")
        put("Assam", "AS")
        put("Manipur", "MNL")
        put("Nagaland", "NL")
        put("Meghalaya", "ML")
        put("Punjab", "PB")
        put("Rajasthan", "RJ")
        put("Uttar Pradesh", "UP")
        put("Uttarakhand", "UT")
        put("Jharkhand", "JH")
        put("West Bengal", "WB")
        put("Bihar", "BR")
        put("Sikkim", "SK")
        put("Chhattisgarh", "CT")
        put("Madhya Pradesh", "MP")
        put("Pondicherry", "PY")
        put("Tamil Nadu", "TN")
        put("Gujarat", "GJ")
        put("Telangana", "TG")
        put("Arunachal Pradesh", "AR")
        put("Mizoram", "MZ")
        put("Tripura", "TR")
        put("Andhra Pradesh (New)", "AD")
        put("Daman and Diu", "DD")
        put("Goa", "GA")
        put("Lakshadweep Islands", "LD")
    }


    const val STATE_ID = "STATE_ID"
    const val STATE = "STATE"
    const val TITLE = "title"


    val pieChartColors = listOf<Int>(
        0xFFed1869.toInt(),
        0xFF29bfcd.toInt(),
        0xFFf89820.toInt(),
        0xFF9367ac.toInt(),
        0xFF8ec73f.toInt(),
        0xFF808080.toInt(),
        0xFFfcd800.toInt(),
        0xFFb76766.toInt(),
        0xFF1a77b3.toInt(),
        0xFF66403a.toInt(),
        0xFFC4C4C4.toInt()
    )

}