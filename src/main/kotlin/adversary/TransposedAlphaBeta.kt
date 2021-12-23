package adversary

import model.Board
import model.BoardImpl
import model.Move

class TransposedAlphaBeta(white: Boolean) : Adversary, AbstractAdversary(white, 4) {
    private val transpositionTable = HashMap<Array<IntArray>, Pair<Int, Move?>>()

    override fun pickMove(board: Board): Move {
        val moves = board.getMoves()
        val startTime = System.nanoTime()
        val decision = search(isMaximizing = true, alpha = Int.MIN_VALUE, beta = Int.MAX_VALUE, board = board)
        println("Took " + ((System.nanoTime() - startTime) / 1000000000.0) + " seconds")
        println("Decided move was " + decision.third + ", score of " + decision.first + ", Positions evaluated: " + decision.second)
        val move = decision.third
        return move ?: moves.random()
    }

    /**
     * Returns a Triple containing the score of the best searched move, number of positions evaluated, and the move itself
     * @param depth: How many levels to search
     * @param isMaximizing: Whether this function is maximizing or minimizing the given move
     * @param alpha: holds the current best score
     * @param beta: holds the current worst score
     * @param board: the game board
     */
    private fun search(
        depth: Int = 0,
        isMaximizing: Boolean,
        alpha: Int,
        beta: Int,
        board: Board
    ): Triple<Int, Int, Move?> {
        // Apply memoization
        val boardKey = board.getReadOnlyBoard()
        if (transpositionTable.containsKey(boardKey)) {
            val lookup = transpositionTable[boardKey]!!
            return Triple(lookup.first, 1, lookup.second)
        }
        // If we have reached maximum depth or the game is over, return the board evaluation
        val depthOrDone = checkDepthAndStatus(depth, board)
        if (depthOrDone.first) {
            return Triple(depthOrDone.second, 1, null)
        }
        // Look at all the possible moves for the current player
        val moves = board.getMoves()
        moves.shuffle()
        var bestMove: Move? = null
        var positionsEvaluated = 0

        // can't mutate alpha or beta
        var a = alpha
        var b = beta
        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (move in moves) {
                // Give a clone of the board to test on
                val boardTest = board.clone()
                boardTest.performMoveNoCheck(move)
                val decidedMove = search(depth + 1, false, a, b, boardTest)
                val score = decidedMove.first
                positionsEvaluated += decidedMove.second
                if (score > bestScore) {
                    bestScore = score
                    bestMove = move
                }
                if (bestScore > a) a = bestScore
                if (b <= a) break
            }
            transpositionTable[boardKey] = Pair(depthOrDone.second, null)
            return Triple(bestScore, positionsEvaluated, bestMove)
        } else {  // Minimizing score
            var bestScore = Int.MAX_VALUE
            for (move in moves) {
                // Give a clone of the board to test on
                val boardTest = board.clone()
                boardTest.performMoveNoCheck(move)
                val decidedMove = search(depth + 1, true, a, b, boardTest)
                val score = decidedMove.first
                positionsEvaluated += decidedMove.second
                if (score < bestScore) {
                    bestScore = score
                    bestMove = move
                }
                if (bestScore < b) b = bestScore
                if (b <= a) break
            }
            transpositionTable[boardKey] = Pair(bestScore, bestMove)
            return Triple(bestScore, positionsEvaluated, bestMove)
        }
    }

}