package model

class King : Piece, AbstractPiece() {
    private val directions = mutableListOf(
        Pair(1, -1),
        Pair(1, 0),
        Pair(1, 1),
        Pair(0, -1),
        Pair(0, 1),
        Pair(-1, -1),
        Pair(-1, 0),
        Pair(-1, 1),
    )

    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.KING + Constants.WHITE
        }
        return Constants.KING
    }

    override fun getAttackingSquares(loc: Pair<Int, Int>, b: Board): MutableList<Pair<Int, Int>> {
        val attackingSquares: MutableList<Pair<Int, Int>> = mutableListOf()
        var currentLocation: Pair<Int, Int>
        for (dir in directions) {
            currentLocation = Pair(loc.first + dir.first, loc.second + dir.second)
            attackingSquares.add(currentLocation)
        }

        return attackingSquares
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()

        var currentLocation: Pair<Int, Int>
        for (dir in directions) {
            currentLocation = Pair(loc.first + dir.first, loc.second + dir.second)
            ifEmptyAddMove(this, b, currentLocation, moves)
            ifCaptureAddMove(this, b, currentLocation, moves)
        }
        return moves
    }
}