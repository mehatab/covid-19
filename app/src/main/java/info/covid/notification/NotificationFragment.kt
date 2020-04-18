package info.covid.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import info.covid.R
import info.covid.common.RVAdapter
import info.covid.databinding.FragmentNotificationBinding
import info.covid.models.Notification

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val notificationViewModel by viewModels<NotificationViewModel>()
    private lateinit var adapter: RVAdapter<Notification>

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
        adapter = RVAdapter(R.layout.adapter_notification_item)
        binding.rv.adapter = adapter

        notificationViewModel.notifications.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        notificationViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}