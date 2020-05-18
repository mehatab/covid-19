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
import info.covid.essentials.databinding.FragmentEssentialsBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import info.covid.uicomponents.onExpanded
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EssentialsFragment : Fragment(R.layout.fragment_essentials) {
    private val binding : FragmentEssentialsBinding by bind(FragmentEssentialsBinding::bind)

    private val viewModel : EssentialsViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val sharedViewModel by activityViewModels<SharedViewModel>()
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_resource_item) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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