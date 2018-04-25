package info.laht.tsp

import kotlin.math.sqrt


data class Vector2(
        var x: Double,
        var y: Double
) {

    fun distanceToSquared(other: Vector2): Double {
        val dx = x - other.x
        val dy = y - other.y
        return dx * dx + dy * dy;
    }

    fun distanceTo(other: Vector2): Double {
        return sqrt(distanceToSquared(other))
    }

}

interface IterationData {
    val numIterations: Int
    val bestCost: Double
    val timeSpent: Long
}

class IterationDataImpl(
        override var numIterations: Int = 0,
        override var bestCost: Double = Double.MAX_VALUE,
        override var timeSpent: Long = 0
): IterationData

fun permToInv(perm: IntArray): IntArray {
    val N = perm.size
    val inv = IntArray(N)
    for (i in 1 .. N) {
        inv[i-1] = 0
        var m = 0
        while (perm[m] != i) {
            if (perm[m] > i) {
                inv[i-1] += 1
            }
            m += 1
        }
    }
    return inv

}

fun invToPerm(inv: IntArray): IntArray {
    val N = inv.size
    val pos = IntArray(N)
    for (i in N downTo 1) {
        for (m in (i + 1) .. N) {
            if (pos[m-1] >= inv[i-1]) {
                pos[m-1] += 1
            }
            pos[i-1] = inv[i-1]
        }
    }
    val perm = IntArray(N)
    try {
        for (i in 1 .. N) {
            perm[pos[i-1]] = i
        }
    } catch (ex: Exception) {
        println(pos)
        throw ex
    }
    return perm
}

fun Int.clamp(min: Int, max: Int): Int {
    return if (this > max) max else if (this < min) min else this
}

fun Double.clamp(min: Double, max: Double): Double {
    return if (this > max) max else if (this < min) min else this
}