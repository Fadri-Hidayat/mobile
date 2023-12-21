package myplayground.example.learningq.utils

class TimeInSeconds(private val totalSeconds: Int) {

    init {
        if (totalSeconds < 0) {
            throw IllegalArgumentException("Total minutes must be non-negative.")
        }
    }

    private val hours: Int
        get() = totalSeconds / 3600

    private val minutes: Int
        get() = (totalSeconds % 3600) / 60

    override fun toString(): String {
        return "%02d:%02d".format(hours, minutes)
    }
}
