package model

import java.lang.UnsupportedOperationException

class BoardImpl : Board {
    private var board: Array<IntArray> = Array(Constants.BOARD_HEIGHT) { IntArray(Constants.BOARD_WIDTH) }
    private var whiteMove = true
    private var castleAllowed = mutableListOf(true, true, true, true) // w-k, w-q, b-k, b-q
    private var numMoves = 0

    init {
        clear()
    }

    /** Clears this board */
    private fun clear() {
        whiteMove = true
        castleAllowed = mutableListOf(true, true, true, true)
        numMoves = 0
        for (i in board.indices) {
            val row = IntArray(board[i].size)
            for (j in row.indices) {
                row[j] = Constants.EMPTY_SQUARE
            }
            board[i] = row
        }
    }

    override fun loadFEN(s: String) {
        val ranks = s.split("/")
        val extraneous = ranks.last().split(" ").drop(1)
        // Fill in each row based on the rank
        var row = 8
        for (rank in ranks) {
            val trimmedRank = rank.trim()
            var offset = 0 // In case there is a number
            for ((i, c) in trimmedRank.withIndex()) {
                if (i + offset >= Constants.BOARD_WIDTH) {
                    continue
                }
                // Handle digits
                if (c.isDigit()) {
                    offset += c.digitToInt() - 1
                    continue
                }
                // Convert the char to piece
                var piece = when (c.lowercaseChar()) {
                    'p' -> Constants.PAWN
                    'b' -> Constants.BISHOP
                    'n' -> Constants.KNIGHT
                    'k' -> Constants.KING
                    'r' -> Constants.ROOK
                    'q' -> Constants.QUEEN
                    else -> throw UnsupportedOperationException("Invalid char in FEN: \'$c\'")
                }
                // Piece is white
                if (c.isUpperCase()) {
                    piece += Constants.WHITE
                }
                // Add to board
                board[row - 1][i + offset] = piece
            }
            row--
        }
        // Handle the extraneous rules
        extraneous.map { e -> e.trim() }
        // Which move
        whiteMove = when (extraneous[0]) {
            "w" -> true
            "b" -> false
            else -> throw UnsupportedOperationException("Invalid color to move")
        }
        // What forms of castling are allowed
        castleAllowed = mutableListOf(false, false, false, false)
        for (c in extraneous[1]) {
            when(c){
                'K' -> castleAllowed[0] = true
                'Q' -> castleAllowed[1] = true
                'k' -> castleAllowed[2] = true
                'q' -> castleAllowed[3] = true
            }
        }
        // TODO: En Passant Square
        // TODO: Half-move clock
        numMoves = extraneous[4][0].digitToInt()
    }

    override fun printToConsole() {
        println("Total Moves: $numMoves")
        if(whiteMove) println("White to Move")
        else println("Black to Move")

        for (row in board.reversedArray()) {
            println(row.joinToString(separator = " "))
        }
    }

    override fun getReadOnlyBoard(): Array<IntArray> {
        val readOnlyBoard = Array(Constants.BOARD_HEIGHT) { IntArray(Constants.BOARD_WIDTH) }
        for (i in board.indices) {
            val row = IntArray(board[i].size)
            for (j in row.indices) {
                row[j] = Constants.EMPTY_SQUARE
            }
            readOnlyBoard[i] = row
        }
        return readOnlyBoard
    }


}