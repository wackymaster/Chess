package adversary

import model.Board
import model.Move

interface Adversary {
    fun makeMove(board: Board): Move
}