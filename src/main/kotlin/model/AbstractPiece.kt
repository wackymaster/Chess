package model

abstract class AbstractPiece : Piece {
    private var white = false

    override fun setWhite() {
        white = true
    }

    override fun isWhite(): Boolean {
        return white
    }

    abstract override fun toValue(): Int

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        TODO("Not yet implemented")
    }

    /**
     * If the location is empty, create a move and add it to the list of moves
     */
    fun ifEmptyAddMove(p: Piece, b: Board, loc: Pair<Int, Int>, moves: MutableList<Move>) {
        assert(loc.first in 1..8 && loc.second in 1..8)
        if (b.getPiece(loc.first, loc.second) == Constants.EMPTY_SQUARE) {
            moves.add(Move(p, loc))
        }
    }
}