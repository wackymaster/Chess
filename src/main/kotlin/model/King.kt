package model

class King : Piece, AbstractPiece() {
    override fun toValue(): Int  {
        if (isWhite()) {
            return Constants.KING + Constants.WHITE
        }
        return Constants.KING
    }
}