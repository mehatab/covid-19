package info.covid.state

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import info.covid.state.databinding.FragmentTabContainerBinding
import info.covid.state.list.StateListFragment
import info.covid.state.world.GlobalCovidStatusFragment
import info.covid.uicomponents.bind

class TabContainerFragment : Fragment(R.layout.fragment_tab_container) {
    private val binding: FragmentTabContainerBinding by bind(FragmentTabContainerBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "India"
                else -> "Global"
            }
        }.attach()
    }

    private fun setupAdapter() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int) = when (position) {
                0 -> StateListFragment()
                else -> GlobalCovidStatusFragment()
            }
        }
    }

}