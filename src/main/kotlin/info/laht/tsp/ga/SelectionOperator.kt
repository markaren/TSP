package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*


interface SelectionOperator {
    fun apply(population: List<Candidate>, selectionSize: Int, rng: Random): List<Candidate>
}

class RouletteWheelSelection: SelectionOperator {
    override fun apply(population: List<Candidate>, selectionSize: Int, rng: Random): List<Candidate> {

        val cumulativeFitnesses = DoubleArray(population.size)
        cumulativeFitnesses[0] = getAdjustedFitness(population[0].cost)
        for (i in 1 until population.size) {
            val fitness = getAdjustedFitness(population[i].cost)
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness
        }

        val selection = ArrayList<Candidate>(selectionSize)
        for (i in 0 until selectionSize) {
            val randomFitness = rng.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.size - 1]
            var index = Arrays.binarySearch(cumulativeFitnesses, randomFitness)
            if (index < 0) {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1)
            }
            selection.add(population[index])
        }
        return selection
    }

    private fun getAdjustedFitness(rawFitness: Double): Double {
        return if (rawFitness == 0.0) {
            Double.POSITIVE_INFINITY
        } else {
            1.0 / rawFitness
        }

    }
}