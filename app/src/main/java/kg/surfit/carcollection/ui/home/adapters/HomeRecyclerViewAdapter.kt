package kg.surfit.carcollection.ui.home.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import kg.surfit.carcollection.databinding.FragmentItemBinding

import kg.surfit.carcollection.placeholder.PlaceholderContent.PlaceholderItem
import kg.surfit.carcollection.db.entity.Car

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class HomeRecyclerViewAdapter(
    private val values: List<Car>
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.carNameView.text = item.carName
        holder.yearView.text = item.year.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val carNameView: TextView = binding.textViewCarName
        val yearView: TextView = binding.textViewYear
    }

}