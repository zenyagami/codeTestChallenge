package app.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import app.R
import app.ui.adapter.RestaurantsAdapter
import app.ui.model.MainActivityNavigation
import app.ui.viewmodel.MainViewModel
import domain.model.RestaurantModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort_items) {
            viewModel.onSortButtonClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
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

        viewModel.navigationEvent.observe(this, Observer {
            when (it) {
                is MainActivityNavigation.OnSortCLickEvent -> displaySortDialog()
            }
        })
    }

    private fun displaySortDialog() {
        AlertDialog.Builder(this)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which ->
                Timber.d("Single false")
            }.setSingleChoiceItems(
                R.array.sort_options,
                0,
                { dialog, which ->
                    Timber.d("Single true")
                })
            .show()

    }

    private fun setDataSetItem(list: List<RestaurantModel>) {
        adapter.submitList(list)
    }


}
