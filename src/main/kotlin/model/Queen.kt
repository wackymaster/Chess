package model

class Queen : Piece, AbstractPiece() {
    override fun toValue(): Int  {
        if (isWhite()) {
            return Constants.QUEEN + Constants.WHITE
        }
        return Constants.QUEEN
    }
}