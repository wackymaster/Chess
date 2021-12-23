package model

class Pawn : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.PAWN + Constants.WHITE
        }
        return Constants.PAWN
    }

    override fun getAttackingSquares(loc: Pair<Int, Int>, b: Board): MutableList<Pair<Int, Int>> {
        val dir = if (isWhite()) 1 else -1
        val attack1 = Pair(loc.first + dir, loc.second - 1)
        val attack2 = Pair(loc.first + dir, loc.second + 1)

        return mutableListOf(attack1, attack2)
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        // Single moves first
        val dir = if (isWhite()) 1 else -1
        val forward = Pair(loc.first + dir, loc.second)
        val inFront = ifEmptyAddMove(this, b, forward, moves)
        // Jump on first turn
        val startRow = if (isWhite()) 2 else 7
        if (loc.first == startRow) {
            val firstMove = Pair(loc.first + 2 * dir, loc.second)
            if (inFront) ifEmptyAddMove(this, b, firstMove, moves) // Can't jump over a piece
        }
        // Captures
        val captureLeft = Pair(loc.first + dir, loc.second - 1)
        val captureRight = Pair(loc.first + dir, loc.second + 1)
        ifCaptureAddMove(this, b, captureLeft, moves)
        ifCaptureAddMove(this, b, captureRight, moves)
        return moves
    }
}