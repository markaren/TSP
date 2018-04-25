package laht.info.tsp

import info.laht.tsp.invToPerm
import info.laht.tsp.permToInv
import org.junit.Assert
import org.junit.Test

class Testperm {

    @Test
    fun test() {

        val permutations = listOf(
                intArrayOf(5, 7, 1, 3, 6, 4, 2) to intArrayOf(2, 5, 2, 3, 0, 1, 0),
                intArrayOf(4, 6, 2, 7, 3, 1, 5) to intArrayOf(5, 2, 3, 0, 2, 0, 0)
        )

        permutations.forEach { perm ->
            val inv = permToInv(perm.first)
            Assert.assertEquals(perm.second, inv)
            Assert.assertEquals(perm.first, invToPerm(inv))
        }

    }


    @Test
    fun test2() {

        val perm = (1 until 38).shuffled().toIntArray()
        println(perm)
        val inv = permToInv(perm)
        Assert.assertEquals(perm, invToPerm(inv))

    }

}