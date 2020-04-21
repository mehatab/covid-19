package info.covid.utils

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import info.covid.customview.rings.Rings
import java.text.NumberFormat

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

@BindingAdapter("amount")
fun setAmount(tv: TextView, amount: String? = null) {
    tv.text = if (amount.isNullOrEmpty().not()) {
        try {
            NumberFormat.getNumberInstance().format(amount.toNumber())
        } catch (e: Exception) {
            amount
        }
    } else amount
}