package kg.surfit.carcollection.ui.home.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kg.surfit.carcollection.R
import kg.surfit.carcollection.databinding.FragmentAddCarBinding
import kg.surfit.carcollection.db.AppDatabase
import kg.surfit.carcollection.db.entity.Car
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCarFragment : Fragment() {

    companion object {
        fun newInstance() = AddCarFragment()
    }

    private lateinit var viewModel: AddCarViewModel
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)

        binding.buttonSend.setOnClickListener{
            val carName = binding.editTextCarName.text.toString()
            val carYear = binding.editTextYear.text.toString().toIntOrNull()
            val car = Car(carName = carName, year = carYear!!, photo = "фото", engineCapacity = 1.1f, dateAdded = System.currentTimeMillis() )
            addCarToDatabase(car)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddCarViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addCarToDatabase(car: Car) {
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        val carDao = db.carDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                carDao.insertCar(car)
            }
//            updateCarListFromDatabase()
        }
    }

//    private fun updateCarListFromDatabase() {
//        val db = AppDatabase.getInstance(requireContext().applicationContext)
//        val carDao = db.carDao()
//
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                val carList = carDao.getAllCars()
//                adapter.updateData(carList)
//            }
//        }
//    }

}