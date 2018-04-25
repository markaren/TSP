package laht.info.tsp

import info.laht.tsp.TSPProblem
import info.laht.tsp.ga.*
import info.laht.tsp.invToPerm
import org.junit.Test
import java.io.File

class TestGA {

    private fun test(fileName: String, optimum: Double) {

        val problem = TSPProblem.parse(TestGA::class.java.classLoader.getResource(fileName))
        val ga = GA(
                popSize = problem.dimensionality * 2 ,
                elitism = 0.1,
                mutationRate = 0.1,
                selectionRate = 0.5,
                mutationOperator = SwapMutator(),
                crossoverOperator = NPointCrossover(),
                selectionOperator = StochasticUniversalSampling(),
                problem = problem
        )

        val solve = ga.solve{
            it.timeSpent >= 5000 || it.bestCost == optimum
        }
        println(solve.iterationData.numIterations)
        println("Computed solution for '$fileName' is ${solve.cost}, known optimum is $optimum. numIterations=${solve.iterationData.numIterations}, timeSpent=${solve.iterationData.timeSpent}")
        println("candidate=${invToPerm(solve.candidate).toList()}")

    }

    @Test
    fun test() {

        test("wi29.txt", 27603.0)
        test("dj38.txt", 6656.0)

    }

}