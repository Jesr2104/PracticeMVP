package com.just_jump.practice.ui.detail

import com.just_jump.practice.model.Movie

class DetailPresenter {

    private var view: View? = null

    interface View {
        fun update(movie: Movie)
    }

    fun onCreate(view: View, movie: Movie){
        this.view = view
        view.update(movie)
    }

    fun onDestroy(){
        view = null
    }
}