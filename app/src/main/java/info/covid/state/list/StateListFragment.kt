package info.covid.state.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import info.covid.R
import info.covid.common.RVAdapter
import info.covid.database.enities.State
import info.covid.databinding.FragmentStatesUtListBinding
import info.covid.utils.Const
import info.covid.utils.Const.STATE
import info.covid.utils.removeLast

class StateListFragment : Fragment(), RVAdapter.OnItemClickListener {
    private lateinit var binding: FragmentStatesUtListBinding
    private lateinit var adapter: RVAdapter<State>
    private val mViewModel by viewModels<StateListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatesUtListBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RVAdapter(R.layout.adapter_state_ut_list_item, this)
        binding.rv.adapter = adapter

        mViewModel.states.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
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

    }

    override fun onItemClick(view: View, position: Int) {
        when (view.id) {
            R.id.state_item -> {
                findNavController().navigate(R.id.state_details, Bundle().apply {
                    val state = adapter.getItem(position.minus(1)) as State
                    putString(Const.TITLE, state.state)
                    putString(STATE, state.state)
                })
            }
        }
    }
}