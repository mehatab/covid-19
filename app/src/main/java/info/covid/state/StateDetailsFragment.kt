package info.covid.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import info.covid.R
import info.covid.common.RVAdapter
import info.covid.databinding.FragmentStatewiseInfoBinding
import info.covid.models.District
import info.covid.utils.Const.STATE
import info.covid.utils.removeLast

class StateDetailsFragment : Fragment() {
    private lateinit var binding: FragmentStatewiseInfoBinding
    private val stateViewModel by viewModels<StateDetailsViewModel>()
    private lateinit var adapter: RVAdapter<District>

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
        adapter = RVAdapter(R.layout.adapter_district_item)
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