package info.covid.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import info.covid.common.RVAdapter
import info.covid.database.enities.CovidDayInfo
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import android.graphics.Color
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineDataSet
import info.covid.R
import info.covid.databinding.FragmentHomeBinding
import info.covid.utils.MyXAxisValueFormatter
import info.covid.utils.toNumber


class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RVAdapter<CovidDayInfo>
    private lateinit var stateAdapter: RVAdapter<CovidDayInfo>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        adapter = RVAdapter(R.layout.adapter_day_count_item_new)
        stateAdapter = RVAdapter(R.layout.adapter_state_item)
        binding.rv.adapter = adapter
        binding.stateRv.adapter = stateAdapter
        setUpChart()
        subscribeToData()
        initListeners()
        return binding.root
    }


    private fun subscribeToData() {
        viewModel.chartData.observe(viewLifecycleOwner, Observer {
            if (binding.chartType.text == getString(R.string.cumulative))
                setData()
            else
                setDailyData()
        })

        viewModel.stateDataList.observe(viewLifecycleOwner, Observer {
            stateAdapter.setList(it)
        })

        viewModel.dayList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it.reversed(), it.maxWith(Comparator { o1, o2 ->
                o1.dailyconfirmed.toNumber().compareTo(o2.dailyconfirmed.toNumber())
            })?.dailyconfirmed.toNumber())

            binding.rv.scrollToPosition(0)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })


        viewModel.todayData.observe(viewLifecycleOwner, Observer { resp ->

        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun initListeners() {
        binding.toolbar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.refresh -> {
                    viewModel.getDate()
                }
            }

            return@setOnMenuItemClickListener true
        }

        binding.filters.setOnCheckedChangeListener { group, checkedId ->
            viewModel.allTime.postValue(checkedId == R.id.all)
        }

        binding.chartType.setOnClickListener {
            showFilterMenu()
        }
    }

    private fun showFilterMenu() {
        val popupMenu = PopupMenu(context, binding.chartType)
        popupMenu.menuInflater.inflate(R.menu.chart_type, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.daily -> {
                    binding.chartType.setText(R.string.daily)
                    setDailyData()
                }
                R.id.cumulative -> {
                    binding.chartType.setText(R.string.cumulative)
                    setData()
                }
            }

            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun setUpChart() {
        binding.dailyChart.legend.textColor =   ContextCompat.getColor(requireActivity(), R.color.textColorPrimary)
        binding.dailyChart.xAxis.textColor = ContextCompat.getColor(requireActivity(), R.color.textColorPrimary)
        binding.dailyChart.axisLeft.textColor = ContextCompat.getColor(requireActivity(), R.color.textColorPrimary)
        binding.dailyChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.dailyChart.description = null
        binding.dailyChart.axisRight.isEnabled = false
        binding.dailyChart.axisLeft.setDrawGridLines(false)
        //  binding.lineChart.axisLeft.setDrawAxisLine(false)
        binding.dailyChart.xAxis.setDrawGridLines(false)
        //  binding.lineChart.xAxis.setDrawAxisLine(false)
        binding.dailyChart.xAxis.valueFormatter = MyXAxisValueFormatter()
        binding.dailyChart.setExtraOffsets(0f, 0f, 0f, 15f)
    }

    private fun setData() {
        val lines = arrayListOf<ILineDataSet>().apply {
            add(LineDataSet(viewModel.confirmedList, "Confirmed").apply {
                color = Color.parseColor("#FF6A88")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })

            add(LineDataSet(viewModel.recoveredList, "Recovered").apply {
                color = Color.parseColor("#7ECA8F")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })

            add(LineDataSet(viewModel.deceasedList, "Deaths").apply {
                color = Color.parseColor("#A6ACB1")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })
        }

        val data = LineData(lines)

        data.setDrawValues(false)

        if (binding.dailyChart.data != null) {
            binding.dailyChart.clearValues()
        }

        binding.dailyChart.data = data
    }

    private fun setDailyData() {
        val lines = arrayListOf<ILineDataSet>().apply {
            add(LineDataSet(viewModel.dailyConfirmedList, "Confirmed").apply {
                color = Color.parseColor("#FF6A88")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })

            add(LineDataSet(viewModel.dailyRecoveredList, "Recovered").apply {
                color = Color.parseColor("#7ECA8F")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })

            add(LineDataSet(viewModel.dailyDeceasedList, "Deaths").apply {
                color = Color.parseColor("#A6ACB1")
                setDrawCircles(false)
                lineWidth = 4f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            })
        }

        val data = LineData(lines)

        data.setDrawValues(false)

        if (binding.dailyChart.data != null) {
            binding.dailyChart.clearValues()
        }

        binding.dailyChart.data = data
    }

    companion object {
        fun newInstance(bundle: Bundle? = Bundle()) = HomeFragment().apply {
            arguments = bundle
        }
    }
}