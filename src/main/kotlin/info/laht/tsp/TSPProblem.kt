package info.laht.tsp

import java.io.File
import java.net.URL

class TSPProblem private constructor(
        private val cities: List<Vector2>
) {

    var useInverse = true

    val dimensionality: Int
        get() = cities.size

    fun evaluate(candidate: IntArray): Double {

        @Suppress("NAME_SHADOWING")
        val candidate = if (useInverse) invToPerm(candidate) else candidate

        var dist = 0.0
        candidate.map { cities[it-1] }.zipWithNext().forEach { (c1, c2) ->
            dist += c1.distanceTo(c2)
        }
        return dist
    }

    fun evaluateCandidates(candidates: List<Candidate>) {
        candidates.parallelStream().forEach{
            it.cost = evaluate(it.candidate)
        }
    }

    fun generateCandidate(): Candidate {
        var genes = (1 .. dimensionality).shuffled().toIntArray()
        genes = if (useInverse) permToInv(genes) else genes
        return Candidate((genes))
    }

    companion object {

        fun parse(file: File): TSPProblem {
            return parse(file.readText(Charsets.UTF_8).trim())
        }

        fun parse(url: URL): TSPProblem {
            return parse(url.readText(Charsets.UTF_8).trim())
        }

        fun parse(text: String): TSPProblem {
            return text.split("\n").map {
                val split = it.split(" ")
                val x = split[1].toDouble()
                val y = split[2].toDouble()
                Vector2(x, y)
            }.let { TSPProblem(it) }
        }

    }

}