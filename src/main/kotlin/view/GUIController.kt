package view

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage
import model.*
import tornadofx.add

class GUIController(primaryStage: Stage) {
    private var chessCanvas: Canvas
    private var board: Board = BoardImpl()
    private var pieceImages = HashMap<Int, Image>()

    init {
        // Set up the game board
        board.loadFEN(Constants.STARTING_FEN)
        // Get the images of each piece
        for (color in listOf(0, Constants.WHITE)) {
            for (piece in 1..6) {
                val coloredPiece = color + piece
                val url = javaClass.getResource("$coloredPiece.png") ?: continue
                pieceImages[coloredPiece] = Image(url.toString())
            }
        }


        // JavaFX GUI Setup
        val width = GUIConstants.WINDOW_WIDTH
        val height = GUIConstants.WINDOW_HEIGHT
        val root = Group()
        val s = Scene(root, width, height)
        chessCanvas = Canvas(width, height)
        root.add(chessCanvas)
        primaryStage.apply {
            scene = s
        }
        draw()
    }

    private fun draw() {
        val graphicsContext = chessCanvas.graphicsContext2D
        graphicsContext.drawBoard()
    }

    private fun GraphicsContext.drawBoard() {
        // Color whole background
        fill = Color.WHITE
        fillRect(0.0, 0.0, GUIConstants.WINDOW_WIDTH, GUIConstants.WINDOW_HEIGHT)

        // Draw the board outline
        val boardWidth = GUIConstants.BOARD_WIDTH * GUIConstants.WINDOW_WIDTH
        val boardHeight = GUIConstants.BOARD_HEIGHT * GUIConstants.WINDOW_HEIGHT
        val marginX = (GUIConstants.WINDOW_WIDTH - boardWidth) / 2
        val marginY = (GUIConstants.WINDOW_HEIGHT - boardHeight) / 2
        stroke = Color.BLACK
        strokeRect(marginX, marginY, boardWidth, boardHeight)

        // Draw the squares
        var color: Color
        val squareWidth = boardWidth / 8
        val squareHeight = boardHeight / 8
        for (rank in 1..8) {
            for (column in 1..8) {
                // Shade in the square
                color = if ((rank + column) % 2 == 0) GUIConstants.DARK_COLOR
                else GUIConstants.LIGHT_COLOR
                // Get the piece
                val piece = board.getPiece(rank, column)
                drawSquare(
                    marginX + (column - 1) * squareWidth,
                    marginY + (8 - rank) * squareHeight,
                    squareWidth,
                    squareHeight,
                    color,
                    piece
                )
            }
        }
        drawGuide(marginX, marginY, squareWidth, squareHeight, boardHeight)
    }

    private fun GraphicsContext.drawGuide(
        marginX: Double,
        marginY: Double,
        squareWidth: Double,
        squareHeight: Double,
        boardHeight: Double
    ) {
        // Draw the numbers/letters
        fill = Color.BLACK
        for (rank in 1..8) {
            fillText((9 - rank).toString(), marginX - 10, marginY + (rank - 1) * squareHeight + squareHeight / 2)
        }
        for (column in 1..8) {
            val letter = 'a'.code + (column - 1)
            fillText(
                letter.toChar().toString(),
                marginX + (column - 1) * squareWidth + squareWidth / 2,
                boardHeight + marginY + 10
            )
        }
    }

    private fun GraphicsContext.drawSquare(
        x: Double,
        y: Double,
        width: Double,
        height: Double,
        col: Color,
        piece: Int
    ) {
        fill = col
        fillRect(x, y, width, height)
        if (piece != Constants.EMPTY_SQUARE) {
            val img = pieceImages[piece]
            drawImage(img, x, y, width, height)
        }
    }

}