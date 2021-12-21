package model

interface Board {
    /**
     * Returns a read-only version of the board as a 2-D array
     */
    fun getReadOnlyBoard() : Array<IntArray>

    /**
     * Loads in a chess board based on the FEN string.
     * Requires: s is valid
     * @param s: FEN string to input, example:
     * "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
     */
    fun loadFEN(s: String)

    /**
     * For debugging mostly, prints the board to console
     */
    fun printToConsole()
}