package model

class Queen : Piece, AbstractPiece() {
    private val directions = mutableListOf(
        Pair(1, 1),
        Pair(1, -1),
        Pair(-1, 1),
        Pair(-1, -1),
        Pair(1, 0),
        Pair(0, 1),
        Pair(-1, 0),
        Pair(0, -1)
    )

    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.QUEEN + Constants.WHITE
        }
        return Constants.QUEEN
    }

    override fun getAttackingSquares(loc: Pair<Int, Int>, b: Board): MutableList<Pair<Int, Int>> {
        val attackingSquares = mutableListOf<Pair<Int, Int>>()
        return addAttackingSquaresFromDirection(loc, directions, b, attackingSquares)
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        return addMovesFromDirections(loc, directions, b, moves)
    }
}