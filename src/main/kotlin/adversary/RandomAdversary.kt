package adversary

import model.Board
import model.Move

class RandomAdversary : Adversary {
    override fun pickMove(board: Board): Move {
        val moves = board.getMoves()
        return moves.random()
    }
}