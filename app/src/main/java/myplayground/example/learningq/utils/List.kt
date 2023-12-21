package myplayground.example.learningq.utils

fun <T, K> MutableList<T>.removeDuplicatesBy(selector: (T) -> K) {
    val seen = HashSet<K>()
    val iterator = this.iterator()

    while (iterator.hasNext()) {
        val element = iterator.next()
        if (!seen.add(selector(element))) {
            iterator.remove()
        }
    }
}

fun <T, K> List<T>.removeDuplicatesBy(selector: (T) -> K): List<T> {
    val seen = HashSet<K>()
    val result = mutableListOf<T>()

    for (element in this) {
        if (seen.add(selector(element))) {
            result.add(element)
        }
    }

    return result
}