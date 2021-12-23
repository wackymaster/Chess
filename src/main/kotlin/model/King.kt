package model

class King : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.KING + Constants.WHITE
        }
        return Constants.KING
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        val directions = mutableListOf(
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
            Pair(0, -1),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
        )
        var currentLocation: Pair<Int, Int>
        for (dir in directions) {
            currentLocation = Pair(loc.first + dir.first, loc.second + dir.second)
            ifEmptyAddMove(this, b, currentLocation, moves)
            ifCaptureAddMove(this, b, currentLocation, moves)
        }
        return moves
    }
}