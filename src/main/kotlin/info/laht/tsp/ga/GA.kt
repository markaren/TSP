package info.laht.tsp.ga

import info.laht.tsp.*
import java.util.*


class GA(
        problem: TSPProblem,
        val popSize: Int,
        val elitism: Double,
        val selectionRate: Double,
        val mutationRate: Double,
        val selectionOperator: SelectionOperator = RouletteWheelSelection(),
        val mutationOperator: MutationOperator = SwapMutator(),
        val crossoverOperator: CrossoverOperator = NPointCrossover()
): TSPSolver(problem) {

    private val population
            = MutableList(popSize, {problem.generateCandidate()})

    private val rng = Random()
    private val numElites = Math.round(elitism * popSize).toInt()
    private val numToSelect = Math.round(popSize * selectionRate).toInt()
    private val numMutations = Math.round((popSize - numElites).toDouble() * problem.dimensionality.toDouble() * this.mutationRate).toInt()

    init {
        evaluateAndSortPopulation()
    }

    private fun evaluateAndSortPopulation() {
        problem.evaluateCandidates(population).also {
            population.sort()
        }
    }

    override fun iteration(): Candidate {

        val parentPool = selectionOperator.apply(population, numToSelect, rng)
        val numOffspring = popSize-numToSelect
        val offspring = crossoverOperator.apply(parentPool, numOffspring, rng)

        population.subList(popSize-offspring.size, popSize).clear()
        population.addAll(offspring)

        mutationOperator.apply(population.subList(numElites, popSize), numMutations, rng)

        if (popSize != population.size) {
            println("popsize=$popSize, actual=${population.size}")
        }

        evaluateAndSortPopulation()
        return population[0]

    }

}

