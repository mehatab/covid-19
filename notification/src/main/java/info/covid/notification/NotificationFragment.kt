package info.covid.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import info.covid.notification.databinding.FragmentNotificationBinding
import info.covid.uicomponents.GenericRVAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel : NotificationViewModel by viewModel()
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_notification_item)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater).apply {
            viewModel = notificationViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = adapter

        notificationViewModel.notifications.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        notificationViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}