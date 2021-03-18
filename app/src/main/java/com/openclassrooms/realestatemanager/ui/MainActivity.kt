package com.openclassrooms.realestatemanager.ui

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
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.ui.edit.EditActivity
import com.openclassrooms.realestatemanager.ui.edit.EditFragment
import com.openclassrooms.realestatemanager.ui.list.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.ui.list.RealEstateUiModel
import com.openclassrooms.realestatemanager.ui.list.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.search.SearchActivity
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
                //TODO Ne doit s'afficher qu'après qu'un élément est été selectionné
                if (binding.fragmentContainerFlow?.isVisible == true) {
                    //add(R.id.fragment_container_flow, DetailFragment())
                    twoPane = true
                }

                val bundle = Bundle()
                bundle.putBoolean("TwoPane", twoPane)

                val fragment = RealEstatesFragment()
                fragment.arguments = bundle
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, fragment)
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

    override fun onRealEstateClick(model: RealEstateUiModel) {
        if (twoPane) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_flow, DetailFragment())
            }
        } else {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}