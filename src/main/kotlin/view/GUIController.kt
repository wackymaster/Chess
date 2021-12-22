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
        for(color in listOf(0, Constants.WHITE)){
            for(piece in 1..6){
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
        val squareWidth = (boardWidth - marginX) / 7.5
        val squareHeight = (boardHeight - marginY) / 7.5

        for (rank in 0..7) {
            for (column in 0..7) {
                // Shade in the square
                color = if ((rank + column) % 2 == 0) Color.DARKGREY
                else Color.WHITE
                // Get the piece
                val piece = board.getPiece(rank, column)
                drawSquare(
                    marginX + column * squareWidth,
                    marginY + (7 - rank) * squareHeight,
                    squareWidth,
                    squareHeight,
                    color,
                    piece
                )

            }
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
        if(piece != Constants.EMPTY_SQUARE){
            val img = pieceImages[piece]
            drawImage(img, x, y, width, height)
        }

    }

}