package com.just_jump.practice.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.bumptech.glide.Glide
import com.just_jump.practice.R
import com.just_jump.practice.databinding.ActivityDetailBinding


import com.just_jump.practice.model.Movie

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        if (movie != null) {
            binding.detailActivityToolbar.title = movie.title
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.backdrop)
            binding.summary.text = movie.overview
            bindDetailInfo(binding.detailInfo,movie)
        }
    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            appendInfo(R.string.original_language, movie.original_language)
            appendInfo(R.string.original_title, movie.title)
            appendInfo(R.string.release_data, movie.release_date)
            appendInfo(R.string.popularity, movie.popularity.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())

            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
        }
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value: String){
        bold {
            append(getString(stringRes))
            append(":")
        }
        appendLine(value)
    }
}