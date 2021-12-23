package model

class Queen : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.QUEEN + Constants.WHITE
        }
        return Constants.QUEEN
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        val directions = mutableListOf(
            Pair(1, 1),
            Pair(1, -1),
            Pair(-1, 1),
            Pair(-1, -1),
            Pair(1, 0),
            Pair(0, 1),
            Pair(-1, 0),
            Pair(0, -1)
        )
        return addMovesFromDirections(loc, directions, b, moves)
    }
}