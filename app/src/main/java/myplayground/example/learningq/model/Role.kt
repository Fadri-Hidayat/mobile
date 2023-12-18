package myplayground.example.learningq.model

sealed class Role {
    object Student : Role()
    object Teacher : Role()
    object Admin : Role()

    override fun toString(): String {
        return when (this) {
            is Student -> "student"
            is Teacher -> "teacher"
            is Admin -> "admin"
        }
    }

    companion object {
        fun parseString(str: String): Role {
            return when (str) {
                "student" -> Student
                "teacher" -> Teacher
                "admin" -> Admin

                else -> Student
            }
        }
    }
}
