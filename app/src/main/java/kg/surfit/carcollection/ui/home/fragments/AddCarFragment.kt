package kg.surfit.carcollection.ui.home.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.surfit.carcollection.R

class AddCarFragment : Fragment() {

    companion object {
        fun newInstance() = AddCarFragment()
    }

    private lateinit var viewModel: AddCarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_car, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddCarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}