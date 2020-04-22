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

class StateListFragment : Fragment(), RVAdapter.OnItemClickListener {
    private lateinit var binding: FragmentStatesUtListBinding
    private lateinit var adapter: RVAdapter<State>
    private val viewModel by viewModels<StateListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatesUtListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RVAdapter(R.layout.adapter_state_ut_list_item, this)
        binding.rv.adapter = adapter

        viewModel.states.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })
    }


    override fun onItemClick(view: View, position: Int) {
        when (view.id) {
            R.id.state_item -> {
                findNavController().navigate(R.id.state_wise_info, Bundle().apply {
                    val state = adapter.getItem(position.minus(1)) as State
                    putString(Const.TITLE, state.state)
                    putString(STATE, state.state)
                })
            }
        }
    }
}