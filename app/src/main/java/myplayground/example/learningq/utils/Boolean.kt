package myplayground.example.learningq.utils

fun List<Boolean>.allTrue(): Boolean = all { it }
fun List<String?>.allNull(): Boolean = all { it == null }
