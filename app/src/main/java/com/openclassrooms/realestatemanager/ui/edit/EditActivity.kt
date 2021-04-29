package com.openclassrooms.realestatemanager.ui.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditActivity : AppCompatActivity(), OnSaveClickListener {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_edit_container_view, EditFragment())
            }

            supportFragmentManager.setFragmentResultListener("add_edit_request", this) { _, bundle ->
                val result = bundle.getInt("add_edit_result")
                Log.d("PEACH", "onCreate: $result")
                finish()
            }
        }
    }

    override fun onSaveClick() {
        Log.d("PEACH", "onSaveClick: ACTIVITY ²")
        finish()
    }
}