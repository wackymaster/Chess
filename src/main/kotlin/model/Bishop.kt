package model

class Bishop : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.BISHOP + Constants.WHITE
        }
        return Constants.BISHOP
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        val directions = mutableListOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))
        return addMovesFromDirections(loc, directions, b, moves)
    }
}