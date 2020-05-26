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
import info.covid.state.databinding.FragmentStatewiseInfoBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
/**
 * NaN
1.0,
-0.65465367,
-0.8363503,
-0.8560779,
-0.8161001,
-0.7777172,
-0.45009005,
-0.30733892,
0.15517023,
0.14120112,
0.23301308,
0.0557404,
0.3381133,
0.48341748,
0.48744676,
0.48926333,
0.5626914,
0.5865558,
0.6573157,
0.70461726,
0.7103003,
0.75224364,
0.7859952,
0.8115626,
0.8304937,
0.8248012,
0.84159213,
0.85861087,
0.87249696,
0.8550374,
0.85996825,
0.8701858,
0.88198036,
0.855794,
0.8680829,
0.8461731,
0.85570544,
0.8593118,
0.87021935,
0.852415,
0.85746455,
0.85343397,
0.85820657,
0.8664797,
0.87387466,
0.8816862,
0.8881679,
0.88192636,
0.88911444,
0.8948762,
0.85355717,
0.86225075,
0.86641675,
0.8722738,
0.8794512,
0.88591886,
0.868087,
0.8750798,
0.8790992,
0.88437134,
0.88863647,
0.8935018,
0.8981815,
0.8886088,
0.89136505,
0.89406437,
0.8965921,
0.89774704,
0.8901131,
0.8918512
 * */
class StateDetailsFragment : Fragment(R.layout.fragment_statewise_info) {
    private val binding: FragmentStatewiseInfoBinding by bind(FragmentStatewiseInfoBinding::bind)
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_district_item) }
    private val stateViewModel : StateDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = stateViewModel
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