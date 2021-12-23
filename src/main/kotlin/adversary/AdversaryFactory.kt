package adversary

class AdversaryFactory {
    fun getRandomAdversary(): Adversary {
        return RandomAdversary()
    }

    fun getDepthAdversary(white: Boolean): Adversary {
        return DepthAdversary(white)
    }

    fun getAlphaBetaAdversary(white: Boolean): Adversary {
        return AlphaBetaAdversary(white)
    }

    fun getTransposedDFS(white: Boolean): Adversary {
        return TransposedDFS(white)
    }

    fun getTransposedAlphaBeta(white: Boolean): Adversary {
        return TransposedAlphaBeta(white)
    }
}
