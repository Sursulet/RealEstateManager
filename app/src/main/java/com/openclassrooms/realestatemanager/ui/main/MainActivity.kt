package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.ui.addedit.AddEditActivity
import com.openclassrooms.realestatemanager.ui.addedit.AddEditFragment
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.list.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.ui.list.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.loan.LoanActivity
import com.openclassrooms.realestatemanager.ui.loan.LoanFragment
import com.openclassrooms.realestatemanager.ui.map.MapFragment
import com.openclassrooms.realestatemanager.ui.map.MapsActivity
import com.openclassrooms.realestatemanager.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRealEstateClickListener {

    private lateinit var binding: ActivityMainBinding
    private var twoPane: Boolean = false

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                if (binding.fragmentContainerFlow?.isVisible == true) {
                    replace(R.id.fragment_container_flow, DetailFragment())
                    twoPane = true
                }

                viewModel.setTwoPane(twoPane)

                val bundle = Bundle()
                bundle.putBoolean("TwoPane", twoPane)

                val fragment = RealEstatesFragment()
                fragment.arguments = bundle
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, fragment)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                viewModel.onAddNewRealEstateClick()

                if (twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(
                            R.id.fragment_container_flow,
                            AddEditFragment()
                        )
                    }
                } else {
                    val intent = Intent(this, AddEditActivity::class.java)
                    startActivity(intent)
                }

                true
            }

            R.id.action_edit -> {

                if (twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(
                            R.id.fragment_container_flow,
                            AddEditFragment()
                        )
                    }
                } else {
                    val intent = Intent(this, AddEditActivity::class.java)
                    startActivity(intent)
                }

                viewModel.onEditRealEstateClick()

                true
            }

            R.id.action_search -> {
                val dialog = SearchFragment()
                dialog.show(supportFragmentManager, "searchDialog")
                true
            }

            R.id.map -> {
                if(twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(
                            R.id.fragment_container_flow,
                            MapFragment()
                        )
                    }
                } else {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                }
                true
            }

            R.id.action_loan -> {
                if(twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(
                            R.id.fragment_container_flow,
                            LoanFragment()
                        )
                    }
                } else {
                    val intent = Intent(this, LoanActivity::class.java)
                    startActivity(intent)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem? = menu?.findItem(R.id.action_edit)
        item?.isEnabled = twoPane
        if (twoPane) { item?.icon?.alpha = 255 }
        else { item?.icon?.alpha = 130 }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onRealEstateClick(id: Long) {
        if (twoPane) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_flow, DetailFragment())
            }
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

}