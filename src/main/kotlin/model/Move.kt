package model

class Move(p: Piece, loc: Pair<Int, Int>) {
    private var promotion = false

    fun promote(){
        promotion = true
    }

}