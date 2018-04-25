package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*


interface CrossoverOperator {
    fun apply(parents: List<Candidate>, numOffspring: Int, rng: Random): List<Candidate>
}


/**
 * adapted from https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/operators/IntArrayCrossover.java
 */
class NPointCrossover(
        private val numberOfCrossoverPoints: Int? = null
): CrossoverOperator {

    override fun apply(parents: List<Candidate>, numOffspring: Int, rng: Random): List<Candidate> {

        Collections.shuffle(parents, rng)
        val offspring = ArrayList<Candidate>()

        var i = 0
        while (i < numOffspring) {

            if (i + 1 >= parents.size) {
                break
            }
            val ma = parents[i].candidate
            val pa = parents[i + 1].candidate
            val mateTwo = mateTwo(ma, pa, rng)

            offspring.add(mateTwo.first)
            offspring.add(mateTwo.second)
            i += 2
        }

        if (offspring.size < numOffspring) {

            do {
                val ma = parents[rng.nextInt(parents.size)].candidate
                val pa = parents[rng.nextInt(parents.size)].candidate
                val mateTwo = mateTwo(ma, pa, rng)

                offspring.add(mateTwo.first)
                offspring.add(mateTwo.second)

            } while (offspring.size < numOffspring)

        }

        while (offspring.size > numOffspring) {
            offspring.removeAt(offspring.size - 1)
        }

        return offspring
    }

    private fun mateTwo(ma: IntArray, pa: IntArray, rng: Random): Pair<Candidate, Candidate> {

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
