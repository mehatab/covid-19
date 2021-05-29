package info.covid.uicomponents

import android.text.format.DateUtils
import android.util.Log
import org.jetbrains.annotations.NotNull
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun format(str: String? = null): String {
    return try {
        if (str == null) "" else SimpleDateFormat(
                "EEEE, d MMM yyyy",
                Locale.getDefault()
        ).format(
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse("$str 2020")
        )
    } catch (e: Exception) {
        ""
    }
}

fun day(str: String? = null): String {
    return try {
        if (str == null) "" else SimpleDateFormat(
                "dd",
                Locale.getDefault()
        ).format(
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse("$str 2020")
        )
    } catch (e: Exception) {
        ""
    }
}


fun month(str: String? = null): String {
    return try {
        if (str == null) "" else SimpleDateFormat(
                "MMM",
                Locale.getDefault()
        ).format(
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).parse("$str 2020")
        )
    } catch (e: Exception) {
        ""
    }
}


fun relativeTime(str: String? = null): String {
    return try {
       "Updated "+ DateUtils.getRelativeTimeSpanString(
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).parse("$str").time,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (e: Exception) {
        return str ?: ""
    }
}


fun String?.toMilliseconds() : Float {
    return try {
        if (this == null) 0f else
            SimpleDateFormat("dd MMMM", Locale.getDefault()).parse("$this").time.toFloat()
    } catch (e: Exception) {
        0f
    }
}