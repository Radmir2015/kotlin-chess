package chess.swing

import chess.Chess
import chess.images.ChessImages
import chess.moves.SimpleMove
import chess.pieces.*
import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.swing.EuropeBoard
import game.swing.GamePanel
import java.awt.BorderLayout
import java.awt.Cursor
import java.awt.Graphics
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

        add(gamePanel, BorderLayout.CENTER)
    }

    /**
     * Доска с расположенными на ней шахматными фигурами.
     *
     * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
     */
    inner class ChessBoardPanel(game: Game) : EuropeBoard(game.board) {
        override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
            return if (color == PieceColor.WHITE)
                whiteImages[piece.javaClass]
            else
                blackImages[piece.javaClass]
        }

        private var moveColor = PieceColor.WHITE
        private var selectedPiece: Piece? = null
        private var selectedSquare: Square? = null
        private var savedCursor: Cursor? = null

        init {
//
//            Chess.putPieces(board, PieceColor.WHITE)
//            Chess.putPieces(board, PieceColor.BLACK)
        }

        /*
		 * (non-Javadoc)
		 *
		 * @see game.ui.AbstractBoardPanel#getImage(game.core.Piece)
		 */
        override fun getImage(piece: Piece): Image? {
            return getChessPieceImage(piece)
        }
//
//        protected fun mouseDoubleClick(s: Square, button: Int) {}
//
//        /*
//		 * Захват фигуры на клетке s.
//		 */
//        protected fun mouseDown(s: Square, button: Int) {
//            if (s.isEmpty)
//                return
//
//            selectedPiece = s.piece
//            if (selectedPiece!!.color != moveColor)
//                return
//
//            selectedSquare = s
//            s.removePiece()
//
//            savedCursor = cursor
//            pieceToCursor(selectedPiece)
//
//            redraw()
//        }
//
//        /*
//		 * Отпускание фигуры на клетке s.
//		 */
//        protected fun mouseUp(s: Square, button: Int) {
//            if (selectedSquare == null)
//                return
//
//            // Возвращаем фигуру на начальную клетку.
//            selectedSquare!!.piece = selectedPiece
//
//            if (selectedPiece!!.isCorrectMove(s)) {
//                val move = selectedPiece!!.moveTo(s)
//                board.history.addMove(move)
//
//                // TODO Реализовать запрос фигуры для превращения пешки.
//
//                moveColor = if (moveColor == PieceColor.WHITE)
//                    PieceColor.BLACK
//                else
//                    PieceColor.WHITE
//            }
//
//            selectedPiece = null
//            selectedSquare = null
//
//            cursor = savedCursor
//
//            board.setBoardChanged()
//
//            redraw()
//        }

        /*
		 * Пометить на доске последний ход.
		 */
        protected fun markLastMove(gc: Graphics) {
            val moves = board.history.moves
            if (moves.isEmpty()) return

            val move = board.history.curMove as SimpleMove
//
//            if (move != null)
//                markLine(gc, move.source, move.target, Color.RED)
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
