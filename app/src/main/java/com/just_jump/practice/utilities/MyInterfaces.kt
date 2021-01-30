package com.just_jump.practice.utilities

import com.just_jump.practice.model.Movie

interface MovieClickedListener {
    fun onMovieClicked(movie: Movie)
}