package model

class Knight : Piece, AbstractPiece() {
    override fun toValue(): Int  {
        if (isWhite()) {
            return Constants.KNIGHT + Constants.WHITE
        }
        return Constants.KNIGHT
    }
}