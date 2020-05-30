package info.covid.state

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import info.covid.data.utils.Const.STATE
import info.covid.data.utils.removeLast
import info.covid.data.utils.toNumber
import info.covid.state.databinding.FragmentStatewiseInfoBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StateDetailsFragment : Fragment(R.layout.fragment_statewise_info) {
    private val binding: FragmentStatewiseInfoBinding by bind(FragmentStatewiseInfoBinding::bind)
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_district_item) }
    private val stateViewModel: StateDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = stateViewModel
        binding.rv.adapter = adapter

        stateViewModel.districts.observe(viewLifecycleOwner, Observer {
            adapter.setList(it, it.sumBy { it.confirmed ?: 0 })
        })

        stateViewModel.recoversMapData.observe(viewLifecycleOwner, Observer {
            binding.recoveredGraph.addDataPoints(it, stateViewModel.maxNumber)
        })

        stateViewModel.confirmedMapData.observe(viewLifecycleOwner, Observer {
            binding.confirmedGraph.addDataPoints(it, stateViewModel.maxNumber)
        })

        stateViewModel.deceasedMapData.observe(viewLifecycleOwner, Observer {
            binding.deceasedGraph.addDataPoints(it, stateViewModel.maxNumber)
        })

        stateViewModel.activeMapData.observe(viewLifecycleOwner, Observer {
            binding.activeGraph.addDataPoints(it, it.maxBy { it.amount }?.amount ?: 0f)
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateViewModel.stateName.postValue(arguments?.getString(STATE))
    }
}