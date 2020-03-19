package com.noblecilla.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.noblecilla.timefighter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel()
        view()
    }

    private fun viewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.score.observe(this, score)
        viewModel.time.observe(this, time)
        viewModel.isGameOver.observe(this, isGameOver)

        viewModel.play()
    }

    private val score = Observer<Int> {
        binding.score.text = getString(R.string.your_score, it)
    }

    private val time = Observer<Long> {
        binding.time.text = getString(R.string.time_left, it / 1000)
    }

    private val isGameOver = Observer<Boolean> {
        if (it) toast(getString(R.string.game_over_message, "${binding.score.text}"))
    }

    private fun view() {
        binding.play.setOnClickListener {
            viewModel.score()
            it.blink()
            it.random()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.pause()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> menu?.findItem(R.id.night)?.isVisible = true
            Mode.NIGHT.ordinal -> menu?.findItem(R.id.light)?.isVisible = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.light -> switchToMode(AppCompatDelegate.MODE_NIGHT_NO, Mode.LIGHT)
            R.id.night -> switchToMode(AppCompatDelegate.MODE_NIGHT_YES, Mode.NIGHT)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchToMode(nightMode: Int, mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        Preferences.switchToMode(this, mode)
    }
}
