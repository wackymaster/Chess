package adversary

import model.Board
import model.Constants
import model.Move

abstract class AbstractAdversary(private val white: Boolean, private val maxDepth : Int) : Adversary {
    private val pieceValues = hashMapOf(
        Pair(Constants.PAWN, 1),
        Pair(Constants.BISHOP, 3),
        Pair(Constants.KNIGHT, 3),
        Pair(Constants.KING, 4),
        Pair(Constants.ROOK, 5),
        Pair(Constants.QUEEN, 8)
    )

    fun checkDepthAndStatus(depth: Int, board: Board): Pair<Boolean, Int> {
        if (depth == maxDepth) {
            return Pair(true, evaluateBoard(board))
        }
        if (!board.getStatus().first) {
            if (board.getStatus().second == 1) {
                return if (white) Pair(true, Int.MAX_VALUE) else Pair(true, Int.MIN_VALUE)
            } else if (board.getStatus().second == -1) {
                return if (white) Pair(true, Int.MIN_VALUE) else Pair(true, Int.MAX_VALUE)
            }
        }
        return Pair(false, 0)
    }

    private fun evaluateBoard(board: Board): Int {
        var score = 0
        val boardArr = board.getReadOnlyBoard()
        for (rank in boardArr) {
            for (pieceVal in rank) {
                val corrPiece = pieceVal % Constants.WHITE
                val pieceWhite = pieceVal > Constants.WHITE
                val pieceScore = pieceValues.getOrDefault(corrPiece, 0)
                score += if (pieceWhite) {
                    if (board.isWhiteMove()) pieceScore else -1 * pieceScore
                } else {
                    if (board.isWhiteMove()) -1 * pieceScore else pieceScore
                }
            }
        }
        return score
    }
}