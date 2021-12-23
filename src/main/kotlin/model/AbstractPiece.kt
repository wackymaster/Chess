package model

abstract class AbstractPiece : Piece {
    private var white = false

    override fun setWhite() {
        white = true
    }

    override fun setBlack() {
        white = false
    }

    override fun isWhite(): Boolean {
        return white
    }

    abstract override fun toValue(): Int

    override fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move> {
        return mutableListOf()
    }

    private fun checkPawnPromote(move: Move) {
        // Check pawn promotions
        val endRow = if (isWhite()) 8 else 1
        if (move.getPiece() is Pawn && move.getLoc().first == endRow) {
            move.setPromote()
        }
    }

    /**
     * If the location is empty, create a move and add it to the list of moves
     */
    fun ifEmptyAddMove(p: Piece, b: Board, loc: Pair<Int, Int>, moves: MutableList<Move>) {
        if (loc.first !in 1..8 && loc.second !in 1..8) return
        if (b.getPieceVal(loc.first, loc.second) == Constants.EMPTY_SQUARE) {
            val move = Move(p, loc)
            checkPawnPromote(move)
            moves.add(Move(p, loc))
        }
    }

    fun ifCaptureAddMove(p: Piece, b: Board, loc: Pair<Int, Int>, moves: MutableList<Move>) {
        if (loc.first !in 1..8 && loc.second !in 1..8) return
        val captureSquare = b.getPiece(loc.first, loc.second)
        if (captureSquare is Piece && captureSquare.isWhite() != p.isWhite()) {
            val move = Move(p, loc)
            move.setCapture()
            checkPawnPromote(move)
            moves.add(move)
        }
    }
}