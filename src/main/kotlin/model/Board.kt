package model

interface Board {
    /**
     * Returns a read-only version of the board as a 2-D array
     */
    fun getReadOnlyBoard(): Array<IntArray>

    /**
     * Returns a pair containing a boolean whether game still going and an int for the status
     * If the boolean is false, then the int corresponds to the status (1 white wins, 0 draw, -1 black wins)
     * The second value is irrelevant if the first is true
     */
    fun getStatus() : Pair<Boolean, Int>

    fun isWhiteMove() : Boolean

    /**
     * Gets the integer value associated with the piece at @rank, @column
     * Requires: rank, column are in the bounds of the board
     */
    fun getPieceVal(rank: Int, column: Int): Int

    /**
     * Gets the Piece associated with the piece at @rank, @column
     * Requires: rank, column are in the bounds of the board
     */
    fun getPiece(rank: Int, column: Int): Piece?

    /**
     * Clears/Resets this of all pieces
     */
    fun clear()

    /**
     * Returns whether (rank, column) corresponds to a viable location in the game board
     */
    fun inBoard(rank: Int, column: Int): Boolean

    /**
     * Loads in a chess board based on the FEN string.
     * Requires: s is valid
     * @param s: FEN string to input, example:
     * "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
     */
    fun loadFEN(s: String)

    /**
     * Returns true if white is currently in check
     */
    fun whiteChecked() : Boolean

    /**
     * Returns true if black is currently in check
     */
    fun blackChecked() : Boolean

    /**
     * Returns a clone of this board
     */
    fun clone() : Board

    /**
     * Returns a list of all legal moves
     */
    fun getMoves(): MutableList<Move>

    /**
     * Performs the given move. No check is made to see if it is legal
     */
    fun performMoveNoCheck(move: Move)

    /**
     * Attempts to perform the given move. Returns true if successful
     */
    fun performMove(move: Move) : Boolean

    fun getAttackingSquares() : MutableList<Pair<Int, Int>>
}