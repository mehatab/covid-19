package info.covid.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import info.covid.data.utils.Const.STATE
import info.covid.data.utils.removeLast
import info.covid.state.databinding.FragmentStatewiseInfoBinding
import info.covid.uicomponents.GenericRVAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StateDetailsFragment : Fragment() {
    private lateinit var binding: FragmentStatewiseInfoBinding
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_district_item) }
    private val stateViewModel : StateDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatewiseInfoBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = stateViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = adapter

        stateViewModel.districts.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        stateViewModel.recoversMapData.observe(viewLifecycleOwner, Observer {
            binding.recoveredGraph.addDataPoints(it.removeLast(), stateViewModel.maxNumber)
        })

        stateViewModel.confirmedMapData.observe(viewLifecycleOwner, Observer {
            binding.confirmedGraph.addDataPoints(it.removeLast(), stateViewModel.maxNumber)
        })

        stateViewModel.deceasedMapData.observe(viewLifecycleOwner, Observer {
            binding.deceasedGraph.addDataPoints(it.removeLast(), stateViewModel.maxNumber)
        })

        stateViewModel.activeMapData.observe(viewLifecycleOwner, Observer {
            binding.activeGraph.addDataPoints(it.removeLast(), it.maxBy { it.amount }?.amount ?: 0f)
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateViewModel.stateName.postValue(arguments?.getString(STATE))
    }
}