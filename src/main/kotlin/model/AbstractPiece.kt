package model

abstract class AbstractPiece : Piece {
    private var white = false
    private var moved = false

    override fun setWhite() {
        white = true
    }

    override fun setBlack() {
        white = false
    }

    override fun isWhite(): Boolean {
        return white
    }

    override fun hasMoved(): Boolean {
        return moved
    }

    override fun setMoved() {
        moved = true
    }

    abstract override fun toValue(): Int

    override fun getAttackingSquares(loc: Pair<Int, Int>, b: Board): MutableList<Pair<Int, Int>> {
        return mutableListOf()
    }

    private fun checkPawnPromote(move: Move) {
        // Check pawn promotions
        val endRow = if (isWhite()) 8 else 1
        if (move.getPiece() is Pawn && move.getLoc().first == endRow) {
            move.setPromote()
        }
    }

    private fun checkLegal(white: Boolean, move: Move, b: Board): Boolean {
        val boardClone = b.clone()
        boardClone.performMoveNoCheck(move)
        return if (white) {
            !boardClone.whiteChecked()
        } else {
            !boardClone.blackChecked()
        }
    }

    /**
     * If the location is empty, create a move and add it to the list of moves
     */
    fun ifEmptyAddMove(p: Piece, b: Board, loc: Pair<Int, Int>, moves: MutableList<Move>): Boolean {
        if (loc.first !in 1..8 || loc.second !in 1..8) return false
        if (b.getPieceVal(loc.first, loc.second) == Constants.EMPTY_SQUARE) {
            val move = Move(p, loc)
            checkPawnPromote(move)
            if (checkLegal(p.isWhite(), move, b)) moves.add(move)
            return true
        }
        return false
    }

    fun ifCaptureAddMove(p: Piece, b: Board, loc: Pair<Int, Int>, moves: MutableList<Move>): Boolean {
        if (loc.first !in 1..8 || loc.second !in 1..8) return false
        val captureSquare = b.getPiece(loc.first, loc.second)
        if (captureSquare is Piece && captureSquare.isWhite() != p.isWhite()) {
            val move = Move(p, loc)
            move.setCapture()
            checkPawnPromote(move)
            if (checkLegal(p.isWhite(), move, b)) moves.add(move)
            return true
        }
        return false
    }

    fun addAttackingSquaresFromDirection(
        loc: Pair<Int, Int>,
        directions: List<Pair<Int, Int>>,
        b: Board,
        squares: MutableList<Pair<Int, Int>>
    ): MutableList<Pair<Int, Int>> {
        var currentLoc: Pair<Int, Int>
        for (dir in directions) {
            // Location in starting direction
            currentLoc = Pair(loc.first + dir.first, loc.second + dir.second)
            while (b.inBoard(currentLoc.first, currentLoc.second)) {
                val piece = b.getPiece(currentLoc.first, currentLoc.second)
                squares.add(currentLoc)
                if (piece is Piece){
                    break // Done
                }
                currentLoc = Pair(currentLoc.first + dir.first, currentLoc.second + dir.second)
            }
        }
        return squares
    }

    fun addMovesFromDirections(
        loc: Pair<Int, Int>,
        directions: List<Pair<Int, Int>>,
        b: Board,
        moves: MutableList<Move>
    ): MutableList<Move> {
        var currentLoc: Pair<Int, Int>
        for (dir in directions) {
            // Location in starting direction
            currentLoc = Pair(loc.first + dir.first, loc.second + dir.second)
            while (b.inBoard(currentLoc.first, currentLoc.second)) {
                val piece = b.getPiece(currentLoc.first, currentLoc.second)
                ifEmptyAddMove(this, b, currentLoc, moves)
                if (piece is Piece) {
                    ifCaptureAddMove(this, b, currentLoc, moves)
                    break
                }
                currentLoc = Pair(currentLoc.first + dir.first, currentLoc.second + dir.second)
            }
        }
        return moves
    }
}