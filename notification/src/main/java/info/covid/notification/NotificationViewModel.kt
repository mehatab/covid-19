package info.covid.notification

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.covid.data.models.Notification
import info.covid.data.repositories.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private val repo: NotificationRepository) : ViewModel() {
    val notifications = MutableLiveData<List<Notification>>()
    val error = MutableLiveData<String>()
    val loader = ObservableField(View.GONE)

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            loader.set(View.VISIBLE)
            repo.getUpdates({
                notifications.postValue(it.reversed())
                loader.set(View.GONE)
            }, {
                loader.set(View.GONE)
                error.postValue(it)
            })
        }
    }
}