package com.just_jump.practice.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.just_jump.practice.utilities.MovieClickedListener
import com.just_jump.practice.R
import com.just_jump.practice.databinding.ActivityMainBinding
import com.just_jump.practice.model.Movie
import com.just_jump.practice.model.MovieDbClient
import com.just_jump.practice.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var moviesAdapter: MovieAdapter

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            requestPopularMovies(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

        moviesAdapter = MovieAdapter( emptyList(),
            object : MovieClickedListener {
                override fun onMovieClicked(movie: Movie) {
                    Toast.makeText(this@MainActivity, movie.title, Toast.LENGTH_SHORT).show()
                    navigationTo(movie) // function to load detail Activity
                }
            }
        )
        binding.recycleViewFilms.adapter = moviesAdapter
    }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies(isLocationGranted: Boolean) {
        if (isLocationGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        } else {
            doRequestPopularMovies(DEFAULT_REGION)
        }
    }

    private fun doRequestPopularMovies(region: String) {
        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        if(location == null){
            return DEFAULT_REGION
        }

        val geoCoder = Geocoder(this)
        val result = geoCoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    private fun navigationTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}