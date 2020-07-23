package info.covid.state.world

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import info.covid.data.models.global.Country
import info.covid.data.utils.Const.TITLE
import info.covid.state.R
import info.covid.state.databinding.FragmentGloabalCovidStatusBinding
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlobalCovidStatusFragment : Fragment(R.layout.fragment_gloabal_covid_status) {
    private val binding: FragmentGloabalCovidStatusBinding by bind(FragmentGloabalCovidStatusBinding::bind)
    private val vm: GlobalCovidStatusViewModel by viewModel()
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_country_item) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = vm
        binding.rv.adapter = adapter

        vm.countries.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })

        vm.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        adapter.onItemClickListener = { v, index ->
            findNavController().navigate(R.id.country, Bundle().apply {
                putString(TITLE, adapter.getItem<Country>(index.minus(1)).country)
            })
        }

        binding.searchQuery.doAfterTextChanged { str ->
            vm.filter(str.toString())
        }
    }
}