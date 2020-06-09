package info.covid.uicomponents

import android.text.Html
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.ChipGroup
import info.covid.data.utils.toNumber
import info.covid.uicomponents.customview.rings.Rings
import info.covid.uicomponents.customview.sectionbar.SectionProgressBar
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

@BindingAdapter("relativeTime")
fun setRelativeTime(tv: TextView, time: Long? = 0) {
    tv.text = if (time != null && time > 0) {
        try {
            DateUtils.getRelativeTimeSpanString(time * 1000)
        } catch (e: Exception) {
            ""
        }
    } else ""
}

@BindingAdapter("onItemSelected")
fun onItemSelected(cg: ChipGroup, listener: OnItemSelectionListener) {
    cg.setOnCheckedChangeListener { _, checkedId ->
        listener.onItemSelected(checkedId)
    }
}

interface OnItemSelectionListener {
    fun onItemSelected(id: Int)
}


@BindingAdapter(
    value = ["firstText", "secondText", "thiredText", "overAllText"],
    requireAll = false
)
fun setFirstText(rings: Rings, death: Int = 0, recovered: Int = 0, active: Int = 0, conf: Int = 0) {
    rings.overAllText = conf.toString()
    rings.innerFirstText = death.toString()
    rings.innerSecondText = recovered.toString()
    rings.innerThirdText = active.toString()

    rings.setRingInnerFirstProgress(death * 100f / conf, true)
    rings.setRingInnerSecondProgress(recovered * 100f / conf, true)
    rings.setRingInnerThirdProgress(active * 100f / conf, true)
    rings.setRingOverallProgress(100f, true)
    rings.invalidate()
}

val nf =NumberFormat.getNumberInstance(Locale("en", "In"))
@BindingAdapter("number")
fun setNumber(tv: TextView, amount: String? = null) {
    tv.text = if (amount.isNullOrEmpty().not()) {
        try {
            nf.format((amount ?: "0").toInt())
        } catch (e: NumberFormatException) {
            nf.format((amount ?: "0").toFloat())
        } catch (e: Exception) {
            amount
        }
    } else amount
}

@BindingAdapter("deltaNumber")
fun setDeltaNumber(tv: TextView, amount: String? = null) {
    tv.text = if (amount.isNullOrEmpty().not() && amount.toNumber() > 0) {
        tv.visibility = View.VISIBLE
        try {
            NumberFormat.getNumberInstance().format(amount.toNumber())
        } catch (e: Exception) {
            amount
        }
    } else {
        tv.visibility = View.INVISIBLE
        " "
    }
}

@BindingAdapter(value = ["confirmed_cases", "active_cases", "recovered_cases", "deaths_cases"])
fun setProgress(
    sp: SectionProgressBar,
    c: String? = null,
    a: Int? = 0,
    r: String? = null,
    d: String? = null
) {
    sp.setProgress(
        (a?.times(100f)?.div(c.toNumber()) ?: 0f).div(100),
        r.toNumber().times(100f).div(c.toNumber()).div(100),
        d.toNumber().times(100f).div(c.toNumber()).div(100)
    )
}


@BindingAdapter("htmlText")
fun setHtmlText(tv: TextView, str: String? = null) {
    tv.text = Html.fromHtml(str ?: "")
}