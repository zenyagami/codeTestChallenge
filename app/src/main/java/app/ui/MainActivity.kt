package app.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.R
import app.ui.adapter.RestaurantsAdapter
import app.ui.viewmodel.MainViewModel
import domain.model.RestaurantModel
import kotlinx.android.synthetic.main.activity_main.*

// I could use a Fragment but for easy development I will use an Activity :)

class MainActivity : BaseMvvmActivity<MainViewModel>() {
    override val viewModelType = MainViewModel::class.java
    private val adapter by lazy {
        RestaurantsAdapter(onFavoriteClickListener = { favorite ->
            viewModel.onFavoriteClick(favorite)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        restaurantViewList.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter
        }

        viewModel.restaurantListLiveData.observe(this, Observer {
            setDataSetItem(it)
        })
    }

    private fun setDataSetItem(list: List<RestaurantModel>) {
        adapter.submitList(list)
    }


}
