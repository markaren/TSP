package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*


interface CrossoverOperator {
    fun apply(parents: List<Candidate>, numOffspring: Int, rng: Random): List<Candidate>
}

class NPointCrossover(
        private val numberOfCrossoverPoints: Int? = null
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

                mate(ma.candidate, pa.candidate, rng).also {
                    add(it.first)
                    add(it.second)
                }
            }

            if (size > numOffspring) {
                removeAt(size-1)
            }

        }
    }

    private fun mate(ma: IntArray, pa: IntArray, rng: Random): Pair<Candidate, Candidate> {

        if (ma.size != pa.size) {
            throw IllegalArgumentException("ma.size != pa.size")
        }

        val numberOfCrossoverPoints = numberOfCrossoverPoints ?: rng.nextInt(ma.size-1) + 1

        val offspring1 = IntArray(ma.size)
        System.arraycopy(ma, 0, offspring1, 0, ma.size)
        val offspring2 = IntArray(pa.size)
        System.arraycopy(pa, 0, offspring2, 0, pa.size)
        // Apply as many cross-overs as required.
        val temp = IntArray(ma.size)
        for (i in 0 until numberOfCrossoverPoints) {
            // Cross-over index is always greater than zero and less than
            // the length of the parent so that we always pick a point that
            // will result in a meaningful cross-over.
            val crossoverIndex = 1 + rng.nextInt(ma.size - 1)
            System.arraycopy(offspring1, 0, temp, 0, crossoverIndex)
            System.arraycopy(offspring2, 0, offspring1, 0, crossoverIndex)
            System.arraycopy(temp, 0, offspring2, 0, crossoverIndex)
        }
        return Candidate(offspring1) to Candidate(offspring2)
    }

}
