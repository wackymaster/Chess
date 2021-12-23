package adversary

import model.Board
import model.Constants
import model.Move

class AlphaBetaAdversary(white: Boolean) : Adversary, AbstractAdversary(white, 4) {

    override fun makeMove(board: Board): Move {
        val moves = board.getMoves()
        val decision = pickMove(isMaximizing = true, alpha = Int.MIN_VALUE, beta = Int.MAX_VALUE, board = board)
        println(decision.toString())
        val move = decision.second
        return move ?: moves.random()
    }

    private fun pickMove(depth: Int = 0, isMaximizing: Boolean, alpha: Int, beta: Int, board: Board): Pair<Int, Move?> {
        // If we have reached maximum depth or the game is over, return the board evaluation
        val depthOrDone = checkDepthAndStatus(depth, board)
        if (depthOrDone.first) {
            return Pair(depthOrDone.second, null)
        }
        // Look at all the possible moves for the current player
        val moves = board.getMoves()
        var bestMove: Move? = null
        var a = alpha // can't mutate alpha or beta
        var b = beta

        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (move in moves) {
                // Give a clone of the board to test on
                val boardTest = board.clone()
                boardTest.performMoveNoCheck(move)
                val score = pickMove(depth + 1, false, a, b, boardTest).first
                if (score > bestScore) {
                    bestScore = score
                    bestMove = move
                }
                if (bestScore > a) a = bestScore
                if (b <= a) break
            }
            return Pair(bestScore, bestMove)
        } else {
            var bestScore = Int.MAX_VALUE
            for (move in moves) {
                // Give a clone of the board to test on
                val boardTest = board.clone()
                boardTest.performMoveNoCheck(move)
                val score = pickMove(depth + 1, true, a, b, boardTest).first
                if (score < bestScore) {
                    bestScore = score
                    bestMove = move
                }
                if (bestScore < b) b = bestScore
                if (b <= a) break
            }
            return Pair(bestScore, bestMove)
        }
    }

}