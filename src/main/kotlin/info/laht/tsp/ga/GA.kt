package info.laht.tsp.ga

import info.laht.tsp.*
import java.util.*


class GA(
        problem: TSPProblem,
        val popSize: Int,
        val elitism: Double,
        val selectionRate: Double,
        val mutationRate: Double,
        val selectionOperator: SelectionOperator,
        val mutationOperator: MutationOperator = SwapMutator(),
        val crossoverOperator: CrossoverOperator = NPointCrossover()
): TSPSolver(problem) {

    private val population
            = MutableList(popSize, {problem.generateCandidate()})

    private val rng = Random()
    private val numElites: Int = Math.round(elitism * popSize).toInt()
    private val numToSelect: Int = Math.round(popSize * selectionRate).toInt()
    private val numMutations: Int = Math.round(((popSize - numElites) * problem.dimensionality).toDouble() * this.mutationRate).toInt()

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

        population.subList(popSize-offspring.size, popSize).clear() //make room for new offspring, by removing the worst candidates
        population.addAll(offspring)

        mutationOperator.apply(population.subList(numElites, popSize), numMutations, rng) //elites are not to be mutated

        if (popSize != population.size) {
            throw IllegalStateException("popsize=$popSize, actual=${population.size}")
        }

        evaluateAndSortPopulation()
        return population[0]

    }

}

