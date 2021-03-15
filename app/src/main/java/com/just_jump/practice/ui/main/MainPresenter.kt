package com.just_jump.practice.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.location.FusedLocationProviderClient
import com.just_jump.practice.R
import com.just_jump.practice.model.Movie
import com.just_jump.practice.model.MovieDbClient
import com.just_jump.practice.utilities.Scope
import kotlinx.coroutines.launch

class MainPresenter: Scope by Scope.Imp(){

    private var view: View? = null
    private var appContext: Context? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val DEFAULT_REGION = "US"
    }

    interface View {
        fun updateData(movies: List<Movie>)
    }

    fun onCreate(
        view: View,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        fusedLocationClient: FusedLocationProviderClient,
        context: Context
    ) {
        initScope()
        this.view = view
        this.fusedLocationClient = fusedLocationClient
        this.appContext = context

        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    fun requestPopularMovies(isLocationGranted: Boolean) {
        if (isLocationGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        } else {
            doRequestPopularMovies(DEFAULT_REGION)
        }
    }

    private fun doRequestPopularMovies(region: String) {
        launch {
            val apiKey = appContext!!.getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            view!!.updateData(popularMovies.results)
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        if(location == null){
            return DEFAULT_REGION
        }

        // this GeoCoder need the context of the application
        val geoCoder = Geocoder(appContext)
        val result = geoCoder.getFromLocation(
            location.latitude,
            location.longitude,
            1
        )
        return result.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    fun onDestroy(){
        cancelScope()
        this.view = null
    }
}