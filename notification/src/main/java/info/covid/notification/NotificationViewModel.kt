package info.covid.notification

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.covid.data.models.Notification
import info.covid.data.models.Result
import info.covid.data.repositories.NotificationRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationViewModel(private val repo: NotificationRepository) : ViewModel() {
    private val _notifications = MutableLiveData<List<Notification>>()
    val loader = ObservableField(View.GONE)
    val error = MutableLiveData<String>()

    val notifications: LiveData<List<Notification>>
        get() = _notifications

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            repo.getNotifications().collect {
                when (it) {
                    is Result.Success -> {
                        _notifications.value = it.data.reversed()
                    }
                    is Result.Error -> {
                        error.value = it.exception.localizedMessage
                    }
                    is Result.Loading -> {
                        loader.set(if (it.isLoading) View.VISIBLE else View.GONE)
                    }
                }
            }
        }
    }
}