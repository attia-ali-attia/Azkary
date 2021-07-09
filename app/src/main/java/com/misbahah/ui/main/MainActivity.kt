package com.misbahah.ui.main

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.misbahah.R
import com.misbahah.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var counterTextView: TextView
    private lateinit var topTimesTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeViewsAndBindingVariables()


        setUpProgressBar(mViewModel.getTopValue())

        mViewModel.currentTime.observe(this, { currentValue: Long ->
            counterTextView.text = currentValue.toString()
            progressBar.progress = currentValue.toBigInteger().toInt()
        })

        startNotificationWorker()
    }

    private fun startNotificationWorker() {
        NotificationWorker.startNotificationWorker(applicationContext, this)
    }

    fun incrementCounterByOne() {
        val incrementedValue = (counterTextView.text.toString().toInt() + 1).toLong()
        mViewModel.incrementCounterByOne(baseContext, incrementedValue)
    }

    fun decrementCounterByOne() {
        val decrementedValue = (counterTextView.text.toString().toInt() - 1).toLong()
        mViewModel.decrementCounterByOne(decrementedValue)
    }

    private fun setUpProgressBar(topValue: Long) {
        try {
            progressBar.max = topValue.toInt()
            progressBar.progress = counterTextView.text.toString().toInt()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeViewsAndBindingVariables() {
        binding.mainActivity = this
        binding.model = mViewModel
        binding.lifecycleOwner = this
        binding.currentTime = mViewModel.currentTime.value?.toInt() ?: -1

        counterTextView = binding.timer
        topTimesTextView = binding.topTimes
        progressBar = binding.progressBar
    }
}