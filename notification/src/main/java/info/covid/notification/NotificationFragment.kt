package info.covid.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import info.covid.notification.databinding.FragmentNotificationBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val binding by bind(FragmentNotificationBinding::bind)
    private val notificationViewModel : NotificationViewModel by viewModel()
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_notification_item)}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = notificationViewModel
        binding.rv.adapter = adapter

        notificationViewModel.notifications.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        notificationViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}