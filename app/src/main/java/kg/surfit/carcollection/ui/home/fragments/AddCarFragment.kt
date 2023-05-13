package kg.surfit.carcollection.ui.home.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
        private const val REQUEST_IMAGE_PICK = 100
    }

    private lateinit var viewModel: AddCarViewModel
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!
    var image: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, Companion.REQUEST_IMAGE_PICK)
        }

        binding.buttonSend.setOnClickListener{
            val carName = binding.editTextCarName.text.toString()
            val carYear = binding.editTextYear.text.toString().toIntOrNull()
            val car = Car(carName = carName, year = carYear!!, photo = image, engineCapacity = 1.1f, dateAdded = System.currentTimeMillis() )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data // получаем Uri изображения
            if (imageUri != null) {
                image = imageUri.toString()
            }
            Log.e("image", imageUri.toString())
            // Дальнейшая обработка
        }
    }

}