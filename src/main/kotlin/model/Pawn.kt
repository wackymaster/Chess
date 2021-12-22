package model

class Pawn : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.PAWN + Constants.WHITE
        }
        return Constants.PAWN
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        // Single moves first
        val dir = if (isWhite()) 1 else -1
        val forward = Pair(loc.first + dir, loc.second)
        ifEmptyAddMove(this, b, forward, moves)
        // Jump on first turn
        val startRow = if (isWhite()) 2 else 7
        if (loc.first != startRow) {
            return moves
        }
        val firstMove = Pair(loc.first + 2 * dir, loc.second)
        ifEmptyAddMove(this, b, firstMove, moves)
        return moves
    }
}