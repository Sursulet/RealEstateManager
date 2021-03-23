package com.openclassrooms.realestatemanager.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySearchBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_search_container_view, SearchFragment())
            }
        }
    }
}