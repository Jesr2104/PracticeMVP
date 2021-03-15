package com.just_jump.practice.utilities

import com.just_jump.practice.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface MovieClickedListener {
    fun onMovieClicked(movie: Movie)
}

interface Scope: CoroutineScope {
    var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun cancelScope() {
        job.cancel()
    }

    class Imp: Scope {
        override lateinit var job:Job
    }
}