package renju.swing


import game.core.*
import game.core.listeners.PutPieceListener
import game.swing.AsiaBoard
import game.swing.GamePanel
import game.swing.images.GameImages
import renju.Renju
import renju.pieces.RenjuPiece
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Image

/**
 */
class RenjuBoard : GamePanel(Renju()) {
    init {
        val gamePanel = RenjuBoardPanel(game)
        insertSquares(gamePanel)

        add(gamePanel, BorderLayout.CENTER)
    }
}

/**
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class RenjuBoardPanel(game: Game) : AsiaBoard(game.board), IPieceProvider {
    override fun getImage(piece: Piece): Image? {
        return getPieceImage(piece, piece.color)
    }

    init {
        listener = PutPieceListener(this)

        // Слушатель мыши выдающий подсказки для клеток -
        // можно ли ставить фигуру на клетку доски.
        //		mouseMoveListener = new PutPiecePromptListener(this);
//        mouseMoveListener = NoPromptListener(this)

        promptColor = Color.GREEN
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        return if (color == PieceColor.WHITE)
            GameImages.stoneWhite
        else
            GameImages.stoneBlack
    }

    override fun getPiece(square: Square, color: PieceColor): Piece {
        return RenjuPiece(square, color)
    }
}

