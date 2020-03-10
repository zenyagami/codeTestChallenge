package app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.databinding.AdapterRestaurantRowBinding
import domain.model.RestaurantModel

class RestaurantsAdapter(private val onFavoriteClickListener: (item: RestaurantModel) -> Unit) :
    RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>() {
    private val dataList = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            AdapterRestaurantRowBinding.inflate(layoutInflater, parent, false),
            onFavoriteClickListener
        )
    }

    override fun getItemCount() = dataList.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = dataList.currentList[position]
    }

    fun submitList(newList: List<RestaurantModel>) {
        dataList.submitList(newList)
    }

    class ViewHolder(
        private val binding: AdapterRestaurantRowBinding,
        private val onFavoriteClickListener: (item: RestaurantModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var item: RestaurantModel? = null
            set(value) {
                field = value
                value?.let { item ->
                    binding.item = item
                    binding.restaurantFavorite.setOnClickListener {
                        onFavoriteClickListener.invoke(item)
                    }
                }
            }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RestaurantModel>() {
            override fun areItemsTheSame(
                oldItem: RestaurantModel,
                newItem: RestaurantModel
            ) = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: RestaurantModel,
                newItem: RestaurantModel
            ) = oldItem == newItem
        }
    }
}
