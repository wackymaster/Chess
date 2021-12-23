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

    override fun inBoard(rank: Int, column: Int): Boolean {
        return (rank in 1..8 && column in 1..8)
    }

    override fun getPieceVal(rank: Int, column: Int): Int {
        val loc = Pair(rank, column)
        if (whitePieces.containsKey(loc)) {
            return whitePieces[loc]?.toValue() ?: Constants.EMPTY_SQUARE
        } else if (blackPieces.containsKey(loc)) {
            return blackPieces[loc]?.toValue() ?: Constants.EMPTY_SQUARE
        }
        return Constants.EMPTY_SQUARE
    }

    override fun getPiece(rank: Int, column: Int): Piece? {
        val loc = Pair(rank, column)
        if (whitePieces.containsKey(loc)) {
            return whitePieces[loc]
        } else if (blackPieces.containsKey(loc)) {
            return blackPieces[loc]
        }
        return null
    }

    override fun getReadOnlyBoard(): Array<IntArray> {
        val readOnlyBoard = Array(Constants.BOARD_HEIGHT) { IntArray(Constants.BOARD_WIDTH) }
        for (i in readOnlyBoard.indices) {
            val row = IntArray(readOnlyBoard[i].size)
            for (j in row.indices) {
                row[j] = getPieceVal(i, j)
            }
            readOnlyBoard[i] = row
        }
        return readOnlyBoard
    }

    override fun getMoves(): MutableList<Move> {
        val allMoves = mutableListOf<Move>()
        if (whiteMove) {
            whitePieces.forEach { (loc, piece) -> allMoves.addAll(piece.getMoves(loc, this)) }
        } else {
            blackPieces.forEach { (loc, piece) -> allMoves.addAll(piece.getMoves(loc, this)) }
        }
        return allMoves
    }

    private fun updatePosition(piece: Piece, loc: Pair<Int, Int>) {
        if (piece.isWhite()) {
            val oldLocations = whitePieces.filterValues { it == piece }.keys
            whitePieces.remove(oldLocations.first())
            whitePieces[loc] = piece
        } else {
            val oldLocations = blackPieces.filterValues { it == piece }.keys
            blackPieces.remove(oldLocations.first())
            blackPieces[loc] = piece
        }
    }

    private fun capturePiece(piece: Piece, loc: Pair<Int, Int>) {
        if (piece.isWhite()) {
            blackPieces.remove(loc)
        } else {
            whitePieces.remove(loc)
        }
    }

    private fun promotePiece(piece: Piece, loc: Pair<Int, Int>) {
        if (piece.isWhite()) {
            whitePieces.remove(loc)
            val newPiece = Queen()
            newPiece.setWhite()
            whitePieces[loc] = newPiece
        } else {
            blackPieces.remove(loc)
            val newPiece = Queen()
            newPiece.setBlack()
            blackPieces[loc] = newPiece
        }
    }

    override fun performMove(move: Move) : Boolean {
        val legalMoves = getMoves()
        if (move !in legalMoves) {
            return false
        }
        val corrMove = legalMoves.first { it == move }
        updatePosition(corrMove.getPiece(), corrMove.getLoc()) // Update position of piece
        if (corrMove.isCapture()) capturePiece(corrMove.getPiece(), corrMove.getLoc())
        if (corrMove.isPromote()) promotePiece(corrMove.getPiece(), corrMove.getLoc())
        whiteMove = !whiteMove
        return true
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