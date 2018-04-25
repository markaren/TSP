package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*


interface CrossoverOperator {
    fun apply(parents: List<Candidate>, numOffspring: Int, rng: Random): List<Candidate>
}

class NPointCrossover(
        val numberOfCrossoverPoints: Int? = null
): CrossoverOperator {

    override fun apply(parents: List<Candidate>, numOffspring: Int, rng: Random): List<Candidate> {

        return mutableListOf<Candidate>().apply {
            while (size < numOffspring) {

                val i1 = rng.nextInt(parents.size)
                var i2: Int
                do {
                    i2 = rng.nextInt(parents.size)
                } while (i1 == i2)

                val ma = parents[i1]
                var pa = parents[i2]

                mate(ma.candidate, pa.candidate, rng).also { offspring ->
                    addAll(offspring.map { Candidate(it) })
                }
            }

            if (size > numOffspring) {
                removeAt(size-1)
            }

        }
    }

    private fun mate(parent1: IntArray, parent2: IntArray, rng: Random): List<IntArray> {

        if (parent1.size != parent2.size) throw IllegalArgumentException()

        val numberOfCrossoverPoints = numberOfCrossoverPoints ?: rng.nextInt(parent1.size-1) + 1

        val offspring1 = IntArray(parent1.size)
        System.arraycopy(parent1, 0, offspring1, 0, parent1.size)
        val offspring2 = IntArray(parent2.size)
        System.arraycopy(parent2, 0, offspring2, 0, parent2.size)
        // Apply as many cross-overs as required.
        val temp = IntArray(parent1.size)
        for (i in 0 until numberOfCrossoverPoints) {
            // Cross-over index is always greater than zero and less than
            // the length of the parent so that we always pick a point that
            // will result in a meaningful cross-over.
            val crossoverIndex = 1 + rng.nextInt(parent1.size - 1)
            System.arraycopy(offspring1, 0, temp, 0, crossoverIndex)
            System.arraycopy(offspring2, 0, offspring1, 0, crossoverIndex)
            System.arraycopy(temp, 0, offspring2, 0, crossoverIndex)
        }
        return listOf((offspring1), offspring2)
    }

}
