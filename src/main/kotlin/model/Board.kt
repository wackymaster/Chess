package model

interface Board {
    /**
     * Returns a read-only version of the board as a 2-D array
     */
    fun getReadOnlyBoard(): Array<IntArray>

    /**
     * Gets the integer value associated with the piece at @rank, @column
     * Requires: rank, column are in the bounds of the board
     */
    fun getPiece(rank: Int, column: Int): Int

    /**
     * Clears/Resets this of all pieces
     */
    fun clear()

    /**
     * Loads in a chess board based on the FEN string.
     * Requires: s is valid
     * @param s: FEN string to input, example:
     * "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
     */
    fun loadFEN(s: String)

    fun getMoves() : MutableList<Move>
}