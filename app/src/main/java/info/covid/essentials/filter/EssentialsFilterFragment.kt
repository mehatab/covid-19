package info.covid.essentials.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import info.covid.databinding.FragmentEssentialsFilterBinding
import info.covid.essentials.SharedViewModel
import info.covid.models.Filter
import info.covid.utils.addChips

class EssentialsFilterFragment : Fragment() {
    private lateinit var binding: FragmentEssentialsFilterBinding
    private val mViewModel by viewModels<EssentialFilterViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEssentialsFilterBinding.inflate(inflater).apply {
            viewModel = mViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.states.observe(viewLifecycleOwner, Observer {
            binding.statesRv.addChips(it, sharedViewModel.filter.value?.stateName)
        })

        mViewModel.cities.observe(viewLifecycleOwner, Observer {
            binding.citiesRv.addChips(it)
        })

        mViewModel.categories.observe(viewLifecycleOwner, Observer {
            binding.categoriesRv.addChips(it)
        })

        mViewModel.selectedState.observe(viewLifecycleOwner, Observer {
            binding.categoriesRv.removeAllViews()
            updateFilter()
        })

        mViewModel.selectedCity.observe(viewLifecycleOwner, Observer {
            binding.categoriesRv.removeAllViews()
            updateFilter()
        })

        binding.categoriesRv.setOnCheckedChangeListener { _, _ ->
            updateFilter()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.filterLayout)

        binding.collapseArrow.setOnClickListener {
            behavior.state = STATE_HIDDEN
        }
    }

    private fun updateFilter() {
        val categories = arrayListOf<String>().apply {
            binding.categoriesRv.checkedChipIds.forEach {
                add(mViewModel.categories.value?.get(it)?.name ?: "")
            }
        }

        sharedViewModel.filter.postValue(
            Filter(
                mViewModel.selectedState.value,
                mViewModel.selectedCity.value,
                categories
            )
        )
    }
}