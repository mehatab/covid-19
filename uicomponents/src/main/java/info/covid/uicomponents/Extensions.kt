package info.covid.uicomponents


inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float?): Float {
    var sum: Float = 0f
    for (element in this) {
        sum += selector(element) ?: 0f
    }
    return sum
}
