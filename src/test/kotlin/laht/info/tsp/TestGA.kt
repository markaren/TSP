package laht.info.tsp

import info.laht.tsp.ga.GA
import info.laht.tsp.ga.NPointCrossover
import info.laht.tsp.TSPProblem
import org.junit.Test
import java.io.File

class TestGA {

    @Test
    fun test() {

        val problem = TSPProblem.parse(File("dj38.txt"))
        val ga = GA(
                popSize = problem.dimensionality * 5,
                elitism = 0.1,
                mutationRate = 0.25,
                selectionRate = 0.5,
                crossoverOperator = NPointCrossover(),
                problem = problem
        )

        val solve = ga.solve( {it.timeSpent > 2000} )
        println(solve.cost)

    }

}