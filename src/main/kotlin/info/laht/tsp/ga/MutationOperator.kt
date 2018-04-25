package info.laht.tsp.ga

import info.laht.tsp.Candidate
import java.util.*

interface MutationOperator {
    fun apply(candidates: List<Candidate>, numMutations: Int, rng: Random)
}

class SwapMutator: MutationOperator {
    override fun apply(candidates: List<Candidate>, numMutations: Int, rng: Random) {

        for (i in 0 until numMutations) {
            val row = rng.nextInt(candidates.size)
            val candidate = candidates[row]
            val i1 = rng.nextInt(candidate.size)
            var i2: Int
            do  {
                i2 = rng.nextInt(candidate.size)
            } while (i1 != i2)

            //swap to candidate
            val v1 = candidate[i1]
            val v2 = candidate[i2]
            candidate[i1] = v2
            candidate[i2] = v1
        }

    }
}