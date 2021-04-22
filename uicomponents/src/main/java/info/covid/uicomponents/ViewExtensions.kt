package info.covid.uicomponents

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import info.covid.data.enities.Tag

fun ChipGroup.addChips(list: List<Tag>, tag: String? = null) {
    removeAllViews()
    list.forEachIndexed { index, s ->
        Chip(context).apply {
            text = s.name
            isCheckable = true
            id = index
            isChecked = tag == s.name
            addView(this)
        }
        invalidate()
    }
}


fun BottomSheetBehavior<*>.onExpanded(block: (Boolean) -> Unit) {
    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            block(newState != BottomSheetBehavior.STATE_HIDDEN)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    })
}

fun Fragment.getTextColorPrimary() = ContextCompat.getColor(requireContext(), R.color.textPrimary)
