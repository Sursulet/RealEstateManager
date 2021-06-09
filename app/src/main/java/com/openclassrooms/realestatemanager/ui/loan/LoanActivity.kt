package com.openclassrooms.realestatemanager.ui.loan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_loan_container_view, LoanFragment())
        }
    }
}