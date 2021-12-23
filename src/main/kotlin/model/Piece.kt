package model

interface Piece {
    /**
     * Returns the integer value associated with this piece (based on type and color)
     */
    fun toValue(): Int

    fun isWhite(): Boolean

    fun setBlack()

    fun setWhite()

    /**
     * Returns all the moves associated with this piece, given the location and board
     */
    fun getMoves(loc: Pair<Int, Int>, b: Board): MutableList<Move>
}