package com.just_jump.practice.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.just_jump.practice.databinding.ActivityMainBinding
import com.just_jump.practice.model.Movie
import com.just_jump.practice.ui.detail.DetailActivity
import com.just_jump.practice.utilities.MovieClickedListener

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private lateinit var moviesAdapter: MovieAdapter
    private var presenter = MainPresenter()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            presenter.requestPopularMovies(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // this property need the context, for this reason, it's better to
        // obtain the value a pass it by parameter to the presenter
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // ------------------------------------------------------------------------------

        presenter.onCreate(this, requestPermissionLauncher,fusedLocationClient, applicationContext)

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

    private fun navigationTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    override fun updateData(movies: List<Movie>) {
        moviesAdapter.movies = movies
        moviesAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}