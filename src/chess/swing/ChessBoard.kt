package chess.swing

import chess.Chess
import chess.pieces.*
import chess.swing.images.ChessImages
import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.core.listeners.MovePiecePromptListener
import game.swing.EuropeBoard
import game.swing.GamePanel
import java.awt.Image
import java.util.*

/**
 * Панель для доски стандартных шахмат.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ChessBoard : GamePanel(Chess()) {
    init {
        val gamePanel = ChessBoardPanel(game)
        insertSquares(gamePanel)

//        add(gamePanel, BorderLayout.CENTER)
    }

    /**
     * Доска с расположенными на ней шахматными фигурами.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    inner class ChessBoardPanel(game: Game) : EuropeBoard(game.board) {
        init {
            listener = MovePieceListener(this)
            mouseMoveListener = MovePiecePromptListener(this)
        }

        override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
            return if (color == PieceColor.WHITE)
                whiteImages[piece.javaClass]
            else
                blackImages[piece.javaClass]
        }

        override fun getImage(piece: Piece): Image? {
            return getChessPieceImage(piece)
        }
    }

    companion object {
        /**
         * Изображения белых фигур.
         */
        private val whiteImages = HashMap<Class<out Piece>, Image>()

        /**
         * Изображения черных фигур.
         */
        private val blackImages = HashMap<Class<out Piece>, Image>()

        /**
         * Инициализация статических переменных - изображений белых и черных фигур.
         */
        init {
            // Изображения белых фигур.
            whiteImages[Pawn::class.java] = ChessImages.imagePawnWhite
            whiteImages[Rook::class.java] = ChessImages.imageRookWhite
            whiteImages[Knight::class.java] = ChessImages.imageKnightWhite
            whiteImages[Bishop::class.java] = ChessImages.imageBishopWhite
            whiteImages[Queen::class.java] = ChessImages.imageQueenWhite
            whiteImages[King::class.java] = ChessImages.imageKingWhite

            // Изображения черных фигур.
            blackImages[Pawn::class.java] = ChessImages.imagePawnBlack
            blackImages[Rook::class.java] = ChessImages.imageRookBlack
            blackImages[Knight::class.java] = ChessImages.imageKnightBlack
            blackImages[Bishop::class.java] = ChessImages.imageBishopBlack
            blackImages[Queen::class.java] = ChessImages.imageQueenBlack
            blackImages[King::class.java] = ChessImages.imageKingBlack
        }

        /**
         * Выдать изображение для заданной шахматной фигуры.
         *
         * @param piece фигура для которой выдать изображение.
         * @return изображение
         */
        fun getChessPieceImage(piece: Piece): Image? {
            return if (piece.color == PieceColor.WHITE)
                whiteImages[piece.javaClass]
            else
                blackImages[piece.javaClass]
        }
    }
}