package info.covid.notification

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import info.covid.models.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationRepository = NotificationRepository()
    val notifications = MutableLiveData<List<Notification>>()
    val error = MutableLiveData<String>()
    val loader = ObservableField(View.GONE)

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            loader.set(View.VISIBLE)
            notificationRepository.getUpdates({
                notifications.postValue(it.reversed())
                loader.set(View.GONE)
            }, {
                loader.set(View.GONE)
                error.postValue(it)
            })
        }
    }
}