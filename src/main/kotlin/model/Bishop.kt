package model

class Bishop : Piece, AbstractPiece() {
    override fun toValue(): Int  {
        if (isWhite()) {
            return Constants.BISHOP + Constants.WHITE
        }
        return Constants.BISHOP
    }
}