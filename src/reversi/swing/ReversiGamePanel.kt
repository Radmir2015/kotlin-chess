package reversi.swing

import game.core.*
import game.core.listeners.PutPieceListener
import game.core.listeners.PutPiecePromptListener
import game.swing.GamePanel
import game.swing.GreenBoard
import game.swing.ScorePanel
import game.swing.images.GameImages
import reversi.Reversi
import reversi.pieces.Hole
import reversi.pieces.Stone
import reversi.swing.images.ReversiImages
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Image
import javax.swing.Box

/**
 * Доска для игры в [
 * Го](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ReversiGamePanel(nHoles: Int) : GamePanel(Reversi(nHoles)) {
    constructor() : this(0)

    init {
        background = GREEN

        val gameBoard = ReversiBoardPanel(game)
        insertSquares(gameBoard)

        val scorePanel = ScorePanel(game)
        scorePanel.alignmentX = Component.CENTER_ALIGNMENT

        control.add(Box.createRigidArea(Dimension(0, 5)))
        control.add(scorePanel)
    }

    /**
     * Доска для игры в [
 * Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    inner class ReversiBoardPanel
    /**
     * Создать доску для игры в реверси.
     *
     * @param game
     */
    (game: Game) : GreenBoard(game.board), IPieceProvider {

        init {
            // Слушатель мыши для постановки новой фигуры на доску.
            listener = PutPieceListener(this)

            // Слушатель мыши для отрисовки подсказки на доске -
            // можно ли ставить фигуру на клетку на доски.
            mouseMoveListener = PutPiecePromptListener(this)
        }

        override fun getPiece(square: Square, color: PieceColor): Piece {
            return Stone(square, color)
        }

        override fun getPieceImage(piece: Piece, color: PieceColor): Image {
            if (piece is Hole)
                return ReversiImages.imageHoleBlack

            return if (color == PieceColor.WHITE)
                GameImages.stoneWhite
            else
                GameImages.stoneBlack
        }

        override fun getImage(piece: Piece): Image? {
            return getPieceImage(piece, piece.color)
        }
    }

    companion object {
        private val GREEN = Color(0, 192, 0, 255)
    }
}