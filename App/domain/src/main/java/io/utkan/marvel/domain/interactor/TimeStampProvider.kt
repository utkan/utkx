package io.utkan.marvel.domain.interactor

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