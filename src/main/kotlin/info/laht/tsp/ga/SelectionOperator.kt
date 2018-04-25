package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*
import java.util.ArrayList


interface SelectionOperator {
    fun apply(population: List<Candidate>, selectionSize: Int, rng: Random): List<Candidate>
}

private fun getAdjustedFitness(rawFitness: Double): Double {
    return if (rawFitness == 0.0) {
        Double.POSITIVE_INFINITY
    } else {
        1.0 / rawFitness
    }
}

/**
 * https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/selection/RouletteWheelSelection.java
 */
class RouletteWheelSelection: SelectionOperator {
    override fun apply(population: List<Candidate>, selectionSize: Int, rng: Random): List<Candidate> {

        val cumulativeFinesses = DoubleArray(population.size)
        cumulativeFinesses[0] = getAdjustedFitness(population[0].cost)
        for (i in 1 until population.size) {
            val fitness = getAdjustedFitness(population[i].cost)
            cumulativeFinesses[i] = cumulativeFinesses[i - 1] + fitness
        }

        val selection = ArrayList<Candidate>(selectionSize)
        for (i in 0 until selectionSize) {
            val randomFitness = rng.nextDouble() * cumulativeFinesses[cumulativeFinesses.size - 1]
            var index = Arrays.binarySearch(cumulativeFinesses, randomFitness)
            if (index < 0) {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1)
            }
            selection.add(population[index])
        }
        return selection
    }

}

/**
 * https://github.com/dwdyer/watchmaker/blob/master/framework/src/java/main/org/uncommons/watchmaker/framework/selection/StochasticUniversalSampling.java
 */
class StochasticUniversalSampling: SelectionOperator {
    override fun apply(population: List<Candidate>, selectionSize: Int, rng: Random): List<Candidate> {
        // Calculate the sum of all fitness values.
        var aggregateFitness = 0.0
        for (candidate in population) {
            aggregateFitness += getAdjustedFitness(candidate.cost)
        }

        val selection = ArrayList<Candidate>(selectionSize)
        // Pick a random offset between 0 and 1 as the starting point for selection.
        val startOffset = rng.nextDouble()
        var cumulativeExpectation = 0.0
        var index = 0
        for (candidate in population) {
            // Calculate the number of times this candidate is expected to
            // be selected on average and add it to the cumulative total
            // of expected frequencies.
            cumulativeExpectation += getAdjustedFitness(candidate.cost) / aggregateFitness * selectionSize

            // If f is the expected frequency, the candidate will be selected at
            // least as often as floor(f) and at most as often as ceil(f). The
            // actual count depends on the random starting offset.
            while (cumulativeExpectation > startOffset + index) {
                selection.add(candidate)
                index++
            }
        }
        return selection
    }
}