package info.covid.dashboard

import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import info.covid.dashboard.databinding.FragmentHomeBinding
import info.covid.data.utils.Const.pieChartColors
import info.covid.data.utils.format
import info.covid.data.utils.toFloatNumber
import info.covid.data.utils.toNumber
import info.covid.uicomponents.GenericRVAdapter
import info.covid.uicomponents.bind
import info.covid.uicomponents.getTextColorPrimary
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.absoluteValue


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by bind(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: GenericRVAdapter by inject() { parametersOf(R.layout.adapter_day_count_item) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.rv.adapter = adapter
        setUpChart()
        subscribeToData()
        initListeners()
    }

    private fun subscribeToData() {
        viewModel.chartData.observe(viewLifecycleOwner, Observer {
            setData((binding.chartType.text == getString(R.string.cumulative)))
        })

        viewModel.stateDataList.observe(viewLifecycleOwner, Observer {
            val dataSet = PieDataSet(it, "")
            dataSet.colors = pieChartColors

            val data = PieData(dataSet)

            data.setValueFormatter(PercentFormatter())

            data.setValueTextSize(13f)
            binding.stateChart.data = data

            binding.stateChart.data.notifyDataChanged()
            binding.stateChart.invalidate()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.dayList.observe(viewLifecycleOwner, Observer { list ->
            adapter.setList(list.reversed(), list.maxWith(Comparator { o1, o2 ->
                o1.dailyconfirmed.toNumber().compareTo(o2.dailyconfirmed.toNumber())
            })?.dailyconfirmed.toNumber())

            binding.rv.scrollToPosition(0)
        })
    }


    private fun preparePieChart(mChart: PieChart, tf: Typeface) {
        binding.stateChart.description.text = ""
        binding.stateChart.isDrawHoleEnabled = false

        mChart.description.isEnabled = true
        mChart.centerText = ""
        mChart.setCenterTextSize(10F)
        mChart.setCenterTextTypeface(tf)
        val l = mChart.legend
        mChart.legend.isWordWrapEnabled = true
        mChart.legend.isEnabled = true
        l.textColor = getTextColorPrimary()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.formSize = 20F
        l.formToTextSpace = 5f
        l.form = Legend.LegendForm.CIRCLE
        l.textSize = 12f
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.isWordWrapEnabled = true
        l.yEntrySpace = 5f
        l.setDrawInside(false)
        mChart.setTouchEnabled(false)
        mChart.setDrawEntryLabels(false)
        mChart.legend.isWordWrapEnabled = true
        mChart.setExtraOffsets(10f, 0f, 10f, 0f)
        mChart.setUsePercentValues(false)
        mChart.setDrawCenterText(false)
        mChart.description.isEnabled = true
        mChart.isRotationEnabled = false
    }

    private fun initListeners() {
        binding.filters.setOnCheckedChangeListener { _, checkedId ->
            viewModel.allTime.postValue(checkedId == R.id.all)
        }

        binding.chartType.setOnClickListener {
            showChartTypeMenu()
        }
    }

    private fun showChartTypeMenu() {
        val popupMenu = PopupMenu(context, binding.chartType)
        popupMenu.menuInflater.inflate(R.menu.chart_type, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.daily -> {
                    binding.chartType.setText(R.string.daily)
                    setData(false)
                }
                R.id.cumulative -> {
                    binding.chartType.setText(R.string.cumulative)
                    setData(true)
                }
            }

            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun setUpChart() {
        binding.dailyChart.legend.textColor = getTextColorPrimary()
        binding.dailyChart.xAxis.textColor = getTextColorPrimary()
        binding.dailyChart.axisLeft.textColor = getTextColorPrimary()
        binding.dailyChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.dailyChart.description = null
        binding.dailyChart.axisRight.isEnabled = false
        binding.dailyChart.axisLeft.setDrawGridLines(false)
        binding.dailyChart.xAxis.setDrawGridLines(false)
        binding.dailyChart.xAxis.valueFormatter = MyXAxisValueFormatter()
        binding.dailyChart.setExtraOffsets(0f, 0f, 0f, 15f)
        preparePieChart(binding.stateChart, Typeface.SANS_SERIF)
    }


    private fun setData(isCumulative: Boolean) {
        val lines = arrayListOf<ILineDataSet>().apply {
            add(
                LineDataSet(
                    if (isCumulative) viewModel.confirmedList else viewModel.dailyConfirmedList,
                    getString(R.string.confirmed)
                ).apply {
                    color = getColor(requireContext(), R.color.confirmed)
                    setDrawCircles(false)
                    lineWidth = 4f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    cubicIntensity = 0.10f
                })

            add(
                LineDataSet(
                    if (isCumulative) viewModel.recoveredList else viewModel.dailyRecoveredList,
                    getString(R.string.recovered)
                ).apply {
                    color = getColor(requireContext(), R.color.recovered)
                    setDrawCircles(false)
                    lineWidth = 4f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    cubicIntensity = 0.10f
                })

            add(
                LineDataSet(
                    if (isCumulative) viewModel.deceasedList else viewModel.dailyDeceasedList,
                    getString(R.string.deaths)
                ).apply {
                    color = getColor(requireContext(), R.color.deaths)
                    setDrawCircles(false)
                    lineWidth = 4f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    cubicIntensity = 0.10f
                })
        }

        if (binding.dailyChart.data != null) {
            binding.dailyChart.clearValues()
        }

        binding.dailyChart.data = LineData(lines).apply {
            setDrawValues(false)
        }

        binding.dailyChart.data.notifyDataChanged()
        binding.dailyChart.invalidate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar, menu)
        val refresh = menu.findItem(R.id.refresh)

        viewModel.refreshing.observe(viewLifecycleOwner, Observer {
            if (it) {
                refresh.setActionView(R.layout.action_view_progress)
            } else refresh.actionView = null
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                viewModel.getDate()
            }
        }
        return true
    }
}