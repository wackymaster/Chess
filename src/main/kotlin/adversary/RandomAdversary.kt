package adversary

import model.Board
import model.Move

class RandomAdversary : Adversary {
    override fun makeMove(board: Board): Move {
        val moves = board.getMoves()
        return moves.random()
    }
}