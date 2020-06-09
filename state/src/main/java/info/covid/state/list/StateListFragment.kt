package info.covid.state.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import info.covid.data.enities.State
import info.covid.data.utils.Const
import info.covid.data.utils.Const.STATE
import info.covid.data.utils.removeLast
import info.covid.data.utils.toNumber
import info.covid.state.R
import info.covid.state.StateDetailsViewModel
import info.covid.state.databinding.FragmentStatesUtListBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StateListFragment : Fragment(R.layout.fragment_states_ut_list) {
    private val binding: FragmentStatesUtListBinding by bind(FragmentStatesUtListBinding::bind)

    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_state_ut_list_item) }
    private val mViewModel: StateListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mViewModel
        binding.rv.adapter = adapter

        mViewModel.states.observe(viewLifecycleOwner, Observer {
            adapter.setList(it, mViewModel.total.value?.confirmed.toNumber())
        })


        mViewModel.dayList.observe(viewLifecycleOwner, Observer { res ->
            res["Active"]?.let {
                binding.activeGraph.addDataPoints(
                    it.removeLast(),
                    it.maxBy { it.amount }?.amount ?: 0f
                )
            }

            res["Confirmed"]?.let {
                binding.confirmedGraph.addDataPoints(it.removeLast(), mViewModel.maxValue)
            }

            res["Deceased"]?.let {
                binding.deceasedGraph.addDataPoints(it.removeLast(), mViewModel.maxValue)
            }

            res["Recovered"]?.let {
                binding.recoveredGraph.addDataPoints(it.removeLast(), mViewModel.maxValue)
            }
        })

        adapter.onItemClickListener = { v, index ->
            onItemClick(v, index)
        }
    }

    private fun onItemClick(view: View, position: Int) {
        when (view.id) {
            R.id.state_item -> {
                findNavController().navigate(R.id.state_details, Bundle().apply {
                    val state = adapter.getItem<State>(position.minus(1))
                    putString(Const.TITLE, state.state)
                    putString(STATE, state.state)
                })
            }
        }
    }
}