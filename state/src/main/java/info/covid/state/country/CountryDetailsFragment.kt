package info.covid.state.country

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import info.covid.data.utils.Const
import info.covid.state.R
import info.covid.state.databinding.FragmentCountryDetailsBinding
import info.covid.uicomponents.bind
import org.koin.android.viewmodel.ext.android.viewModel

class CountryDetailsFragment : Fragment(R.layout.fragment_country_details) {
    private val binding: FragmentCountryDetailsBinding by bind(FragmentCountryDetailsBinding::bind)
    private val vm: CountryDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vm.countryName.value = it.getString(Const.TITLE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = vm
    }
}