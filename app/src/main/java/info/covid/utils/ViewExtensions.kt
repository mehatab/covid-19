package info.covid.utils

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import info.covid.database.enities.Tag


fun ChipGroup.addChips(list: List<Tag>, tag: String? = null) {
    removeAllViews()
    list.forEachIndexed { index, s ->
        val chip = Chip(context)
        chip.text = s.name
        chip.isCheckable = true
        chip.id = index
        chip.isChecked = tag == s.name
        addView(chip)
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