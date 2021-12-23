package adversary

import model.Board
import model.Move

interface Adversary {
    fun pickMove(board: Board): Move
}