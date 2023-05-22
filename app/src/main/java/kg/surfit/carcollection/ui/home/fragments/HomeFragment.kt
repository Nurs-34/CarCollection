package kg.surfit.carcollection.ui.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import kg.surfit.carcollection.ui.home.adapters.HomeRecyclerViewAdapter
import kg.surfit.carcollection.R
import kg.surfit.carcollection.db.AppDatabase
import kg.surfit.carcollection.db.entity.Car
import kg.surfit.carcollection.utils.SubCounter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment() {

    private var columnCount = 1
    private lateinit var originalList: List<Car>
    private lateinit var adapter: HomeRecyclerViewAdapter

    private lateinit var sortRadioGroup: RadioGroup
    private lateinit var radioName: RadioButton
    private lateinit var radioYear: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(requireContext().applicationContext)
        val carDao = db.carDao()

//        val car = Car(carName = "Моя машина", year = 2022, photo = R.drawable.ic_home_24.toString(), engineCapacity = 2.0f, dateAdded = System.currentTimeMillis())
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//                carDao.insertCar(car)
//
//            }
//        }

        val subCounter = SubCounter(requireContext().applicationContext)
        var addCounter = subCounter.getAddCount()
        Log.e("Count", addCounter.toString())




        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        sortRadioGroup = view.findViewById(R.id.sortRadioGroup)
        radioName = view.findViewById(R.id.radioName)
        radioYear = view.findViewById(R.id.radioYear)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        recyclerView.layoutManager = layoutManager

        val db = AppDatabase.getInstance(requireContext().applicationContext)
        val carDao = db.carDao()


        lifecycleScope.launch {
            var cars = withContext(Dispatchers.IO){
                carDao.getAllCars()
            }
            adapter = HomeRecyclerViewAdapter(cars)
            recyclerView.adapter = adapter
            originalList = cars
        }



        val searchView: SearchView = requireView().findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Не используется в данном прим
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })

        sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioName -> sortByCarName()
                R.id.radioYear -> sortByYear()
            }
        }




        super.onViewCreated(view, savedInstanceState)
    }

    private fun filterList(query: String) {
        val filteredList = if (query.isNotEmpty()) {
            originalList.filter { car ->
                car.carName.contains(query, ignoreCase = true)
            }
        } else {
            originalList
        }
        adapter.updateList(filteredList)
    }

    private fun sortByCarName() {
        val sortedList = originalList.sortedBy { car -> car.carName }
        adapter.updateList(sortedList)
    }

    private fun sortByYear() {
        val sortedList = originalList.sortedBy { car -> car.year }
        adapter.updateList(sortedList)
    }



    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}