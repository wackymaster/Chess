package model

class Rook : Piece, AbstractPiece() {
    override fun toValue(): Int  {
        if (isWhite()) {
            return Constants.ROOK + Constants.WHITE
        }
        return Constants.ROOK
    }
}