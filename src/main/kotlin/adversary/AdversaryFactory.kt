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
}
