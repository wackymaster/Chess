package adversary

import model.Board
import model.Constants
import model.Move

class DepthAdversary(white: Boolean) : Adversary, AbstractAdversary(white, 3) {
    override fun makeMove(board: Board): Move {
        val moves = board.getMoves()
        val decision = pickMove(board = board)
        println(decision.toString())
        val move = decision.second
        return move ?: moves.random()
    }

    private fun pickMove(depth: Int = 0, board: Board): Pair<Int, Move?> {
        val depthOrDone = checkDepthAndStatus(depth, board)
        if (depthOrDone.first) {
            return Pair(depthOrDone.second, null)
        }
        val moves = board.getMoves()
        var bestScore = Int.MIN_VALUE
        var bestMove: Move? = null
        for (move in moves) {
            val boardTest = board.clone()
            boardTest.performMoveNoCheck(move)
            val score = -1 * pickMove(depth + 1, boardTest).first
            if (score > bestScore) {
                bestMove = move
                bestScore = score
            }
        }
        return Pair(bestScore, bestMove)
    }

}