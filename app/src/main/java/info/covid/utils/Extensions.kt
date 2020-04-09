package info.covid.utils

fun String?.toNumber(): Int {
    return try {
        if (isNullOrEmpty()) 0 else if (this!!.toInt() < 0) 0 else this.toInt()
    } catch (e: Exception) {
        0
    }
}


fun <T> List<T>.removeLast(): List<T> {
    return if (isNullOrEmpty()) this else {
        (this as ArrayList<T>).removeAt(size - 1)
        return this
    }
}


fun <T> List<T>.removeFirst(): List<T> {
    return if (isNullOrEmpty()) this else {
        (this as ArrayList<T>).removeAt(0)
        return this
    }
}


fun Int?.percentage(second: String? = null): Int {
    return if (this == null) {
        0
    } else {
        try {
            (second.toNumber() * 100).div(this)
        } catch (e: java.lang.Exception) {
            return 0
        }
    }
}