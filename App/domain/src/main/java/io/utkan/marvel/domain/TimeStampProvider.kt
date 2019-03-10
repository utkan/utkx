package io.utkan.marvel.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeStampProvider @Inject constructor() {

    /**
     * Get current time stamp
     *
     * @return - string value of the current time stamp.
     */
    val timeStamp: String
        get() = System.currentTimeMillis().toString()
}