package info.laht.tsp

import java.util.*

interface ICandidate: Comparable<ICandidate> {

    var candidate: IntArray
    var cost: Double

    val size: Int
        get() = candidate.size

    operator fun get(index: Int): Int {
        return candidate[index]
    }

    operator fun set(index: Int, value: Int) {
        candidate[index] = value
    }


    override fun compareTo(other: ICandidate): Int {
        return cost.compareTo(other.cost)
    }

}

class Candidate(
        override var candidate: IntArray,
        override var cost: Double = Double.MAX_VALUE
): ICandidate {

    fun copy(): Candidate {
        return Candidate(candidate.clone(), cost)
    }
}

class Solution(
        candidate: ICandidate,
        val iterationData: IterationData
): ICandidate by candidate
