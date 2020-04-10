package info.covid.utils

import androidx.databinding.BindingAdapter
import info.covid.customview.rings.Rings

@BindingAdapter(value = ["firstText", "secondText", "thiredText", "overAllText"], requireAll = false)
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
