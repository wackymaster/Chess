package model

import java.lang.UnsupportedOperationException

class BoardImpl : Board {
    //    private var board: Array<IntArray> = Array(Constants.BOARD_HEIGHT) { IntArray(Constants.BOARD_WIDTH) }
    private var whiteMove = true
    private var castleAllowed = mutableListOf(true, true, true, true) // w-k, w-q, b-k, b-q
    private var numMoves = 0

    // Map of location to piece
    private var whitePieces = HashMap<Pair<Int, Int>, Piece>()
    private var blackPieces = HashMap<Pair<Int, Int>, Piece>()

    override fun clear() {
        whiteMove = true
        castleAllowed = mutableListOf(true, true, true, true)
        numMoves = 0
        whitePieces.clear()
        blackPieces.clear()
    }

    override fun getPiece(rank: Int, column: Int): Int {
        val loc = Pair(rank, column)
        if (whitePieces.containsKey(loc)) {
            return whitePieces[loc]?.toValue() ?: Constants.EMPTY_SQUARE
        } else if (blackPieces.containsKey(loc)) {
            return blackPieces[loc]?.toValue() ?: Constants.EMPTY_SQUARE
        }
        return Constants.EMPTY_SQUARE
    }

    override fun getReadOnlyBoard(): Array<IntArray> {
        val readOnlyBoard = Array(Constants.BOARD_HEIGHT) { IntArray(Constants.BOARD_WIDTH) }
        for (i in readOnlyBoard.indices) {
            val row = IntArray(readOnlyBoard[i].size)
            for (j in row.indices) {
                row[j] = getPiece(i, j)
            }
            readOnlyBoard[i] = row
        }
        return readOnlyBoard
    }

    override fun getMoves(): MutableList<Move> {
        TODO("Not yet implemented")
    }

    override fun loadFEN(s: String) {
        clear()

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
                // Check color
                var white = false

                // Convert the char to piece
                val piece = when (c.lowercaseChar()) {
                    'p' -> Pawn()
                    'b' -> Bishop()
                    'n' -> Knight()
                    'k' -> King()
                    'r' -> Rook()
                    'q' -> Queen()
                    else -> throw UnsupportedOperationException("Invalid char in FEN: \'$c\'")
                }
                // Add to board
                if (c.isUpperCase()) {
                    piece.setWhite()
                    whitePieces[Pair(row, i + offset + 1)] = piece
                } else {
                    blackPieces[Pair(row, i + offset + 1)] = piece
                }
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
            when (c) {
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


}