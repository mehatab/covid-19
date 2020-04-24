package info.covid.essentials

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import info.covid.R
import info.covid.common.RVAdapter
import info.covid.database.enities.Resources
import info.covid.databinding.FragmentEssentialsBinding
import info.covid.utils.onExpanded

class EssentialsFragment : Fragment() {
    private lateinit var binding: FragmentEssentialsBinding
    private lateinit var adapter: RVAdapter<Resources>
    private val viewModel by viewModels<EssentialsViewModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEssentialsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RVAdapter(R.layout.adapter_resource_item)
        binding.rv.adapter = adapter

        viewModel.resources.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.filter_sheet))
        bottomSheetBehavior.state =
            if (sharedViewModel.isFilterSelected()) STATE_HIDDEN else STATE_COLLAPSED

        binding.filter.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        sharedViewModel.filter.observe(viewLifecycleOwner, Observer {
            viewModel.filter.postValue(it)
        })

        bottomSheetBehavior.onExpanded {
            backPressedCallback.isEnabled = it
        }
    }


    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            bottomSheetBehavior.state = STATE_HIDDEN
            isEnabled = false
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                viewModel.getResources()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar, menu)
        val refresh = menu.findItem(R.id.refresh)

        viewModel.refreshing.observe(viewLifecycleOwner, Observer {
            if (it) {
                refresh.setActionView(R.layout.action_view_progress)
            } else refresh.actionView = null
        })
    }
}