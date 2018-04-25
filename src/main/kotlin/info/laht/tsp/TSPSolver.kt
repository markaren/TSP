package info.laht.tsp

abstract class TSPSolver(
        val problem: TSPProblem
) {

    protected fun generateCandidate(): Candidate {
        return problem.generateCandidate()
    }

    protected fun evaluate(candidate: IntArray): Double {
        return problem.evaluate(candidate)
    }

    protected fun evaluateCandidates(candidates: List<Candidate>) {
        problem.evaluateCandidates(candidates)
    }

    protected fun evaluateAndSortCandiates(candidates: MutableList<Candidate>) {
        evaluateCandidates(candidates).also {
            candidates.sort()
        }
    }

    protected fun getBestFrom(candidates: Collection<Candidate>): Candidate {
        var best: Candidate? = null
        for (c in candidates) {
            if (best == null) {
                best = c
            } else if (c.cost < best.cost) {
                best = c
            }
        }
        return best!!
    }

    fun solve(terminationCriteria: (IterationData) -> Boolean): Candidate {
        val t0 = System.currentTimeMillis()
        val iterationData = IterationDataImpl()
        var best: Candidate
        do {
            best = iteration()
            iterationData.apply {
                bestCost = best.cost
                numIterations += 1
                timeSpent = System.currentTimeMillis() - t0
            }
        } while (!terminationCriteria.invoke(iterationData))

        return best

    }

    protected abstract fun iteration(): Candidate

}