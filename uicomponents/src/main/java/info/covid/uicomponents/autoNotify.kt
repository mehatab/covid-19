package info.covid.uicomponents

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import info.covid.data.DiffItem
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : DiffItem, R : RecyclerView.ViewHolder> RecyclerView.Adapter<R>.autoNotify(oldList: List<T>, newList: List<T>) {

    val diffItemCallback = object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].getId() == newList[newItemPosition].getId()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    }
    DiffUtil.calculateDiff(diffItemCallback).dispatchUpdatesTo(this)
}

fun <T : List<DiffItem>, R : RecyclerView.ViewHolder> autoNotifyDelegate(
    adapter: RecyclerView.Adapter<R>,
    initialValue: T
): ReadWriteProperty<Any?, T> =
    object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            adapter.autoNotify(oldValue, newValue)
        }
    }