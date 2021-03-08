package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.edit.EditActivity
import com.openclassrooms.realestatemanager.ui.edit.EditFragment
import com.openclassrooms.realestatemanager.ui.list.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.search.SearchActivity
import com.openclassrooms.realestatemanager.ui.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, RealEstatesFragment())

                if (binding.fragmentContainerFlow != null) {
                    add(R.id.fragment_container_flow, DetailFragment())
                    twoPane = true
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        /*
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.onQueryTextChanged {  }

         */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                if (twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_flow, EditFragment())
                    }
                } else {
                    val intent = Intent(this, EditActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            R.id.action_edit -> {
                if (twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_flow, EditFragment())
                    }
                } else {
                    val intent = Intent(this, EditActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            R.id.action_search -> {
                if (twoPane) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_flow, SearchFragment())
                    }
                } else {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

const val ADD_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER + 1