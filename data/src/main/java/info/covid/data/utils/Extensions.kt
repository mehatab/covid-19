package info.covid.data.utils

fun String?.toNumber(): Int {
    return try {
        if (isNullOrEmpty()) 0 else if (this!!.toInt() < 0) 0 else this.toInt()
    } catch (e: Exception) {
        0
    }
}

fun String?.toFloatNumber(): Float {
    return try {
        if (isNullOrEmpty()) 0f else if (this!!.toFloat() < 0f) 0f else this.toFloat()
    } catch (e: Exception) {
        0f
    }
}

fun Float?.format(frm: String): String {
    return String.format(frm, this)
}


fun <T> List<T>.removeLast(): List<T> {
    return if (isNullOrEmpty()) this else {
        (this as ArrayList<T>).removeAt(size - 1)
        return this
    }
}

fun <T> List<T>.top(count: Int): List<T> {
    return if (isNullOrEmpty() || size <= count) this else {
        return this.subList(0, count)
    }
}

fun <T> List<T>.removeFirst(): List<T> {
    return if (isNullOrEmpty()) this else {
        (this as ArrayList<T>).removeAt(0)
        return this
    }
}


fun Int?.percentageInt(second: String? = null) = percentage(second).toInt()

fun Int?.percentage(second: String? = null): Float {
    return if (this == null) {
        0f
    } else {
        try {
            (second.toNumber() * 100f).div(this)
        } catch (e: java.lang.Exception) {
            return 0f
        }
    }
}