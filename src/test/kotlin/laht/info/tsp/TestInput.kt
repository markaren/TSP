package laht.info.tsp

import info.laht.tsp.TSPProblem
import org.junit.Assert
import org.junit.Test
import java.io.File

class TestInput {

    @Test
    fun test() {
        val problem = TSPProblem.parse(File("dj38.txt"))
        Assert.assertTrue(problem.dimensionality == 38)
    }

}