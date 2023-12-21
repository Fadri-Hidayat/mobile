package myplayground.example.learningq.utils

enum class CustomDayOfWeek(val dayNumber: Int) {
    UNSPECIFIED(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    companion object {
        fun from(dayNumber: Int): CustomDayOfWeek {
            return values().firstOrNull { it.dayNumber == dayNumber }
                ?: throw IllegalArgumentException("Invalid day number: $dayNumber")
        }
    }
}