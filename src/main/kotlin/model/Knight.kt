package model

class Knight : Piece, AbstractPiece() {
    override fun toValue(): Int {
        if (isWhite()) {
            return Constants.KNIGHT + Constants.WHITE
        }
        return Constants.KNIGHT
    }

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        val moves = mutableListOf<Move>()
        val directions = mutableListOf(
            Pair(2, 1),
            Pair(2, -1),
            Pair(-2, 1),
            Pair(-2, -1),
            Pair(1, 2),
            Pair(1, -2),
            Pair(-1, 2),
            Pair(-1, -2)
        )
        var currentLocation : Pair<Int, Int>
        for (dir in directions) {
            currentLocation = Pair(loc.first + dir.first, loc.second + dir.second)
            ifEmptyAddMove(this, b, currentLocation, moves)
            ifCaptureAddMove(this, b, currentLocation, moves)
        }
        return moves
    }
}