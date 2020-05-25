package hu.bme.aut.todoapp.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.todoapp.R
import hu.bme.aut.todoapp.data.DataManager
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        supportActionBar!!.title = "Statistics"

        loadStatistics()
    }

    private fun loadStatistics() {
        var entries: ArrayList<PieEntry> = ArrayList()

        entries.add(PieEntry(DataManager.getDoneNumber().toFloat(), "Done TODOs"))
        entries.add(PieEntry(DataManager.getNotDoneNumber().toFloat(), "All TODOs"))

        val dataSet = PieDataSet(entries, "Statistics")
        dataSet.setColors(*ColorTemplate.LIBERTY_COLORS)

        val data = PieData(dataSet)
        chartStatistics.data = data
        chartStatistics.invalidate()

    }
}