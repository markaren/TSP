package info.laht.tsp

import java.util.*

class Candidate(
        var candidate: IntArray,
        var cost: Double = Double.MAX_VALUE
): Comparable<Candidate> {

    val size: Int
        get() = candidate.size

    operator fun get(index: Int): Int {
        return candidate[index]
    }

    operator fun set(index: Int, value: Int) {
        candidate[index] = value
    }

    override fun compareTo(other: Candidate): Int {
        return cost.compareTo(other.cost)
    }

    fun copy(other: Candidate) {
        candidate = other.candidate.clone()
        cost = other.cost
    }

}
