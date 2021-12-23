package model

class Move(private val piece: Piece, private val loc: Pair<Int, Int>) {
    private var promotion = false
    private var capture = false

    fun isPromote(): Boolean {
        return promotion
    }

    fun setPromote() {
        promotion = true
    }

    fun isCapture(): Boolean {
        return capture
    }

    fun setCapture() {
        capture = true
    }

    fun getPiece(): Piece {
        return piece
    }

    fun getLoc(): Pair<Int, Int> {
        return loc
    }

    override fun equals(other: Any?): Boolean {
        return other.hashCode() == this.hashCode()
    }

    /**
     * Moves are only equal if the piece and the final destination are the same
     * The promotion/capture is handled elsewhere
     */
    override fun hashCode(): Int {
        var result = piece.hashCode()
        result = 31 * result + loc.hashCode()
        return result
    }

    override fun toString(): String {
        return "Piece $piece to $loc. Promote: $promotion. Capture: $capture"
    }
}