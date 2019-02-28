package checkers.swing

import checkers.Checkers
import checkers.moves.Capture
import checkers.pieces.King
import checkers.pieces.Man
import checkers.swing.images.CheckersImages
import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.TrackPieceListener
import game.swing.EuropeBoard
import game.swing.GamePanel
import java.awt.Image
import java.util.*

/**
 * Панель для игры в шашки.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class CheckersGamePanel : GamePanel(Checkers()) {
    init {
        insertSquares(CheckersBoardPanel(game))
    }
}

/**
 * Доска для игры в шашки.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class CheckersBoardPanel(game: Game) : EuropeBoard(game) {
    init {
        listener = TrackPieceListener<Capture>(this)
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        return pieceImages[color]?.get(piece.javaClass)
    }

    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage(piece: Piece): Image? {
        return getChessPieceImage(piece)
    }

    companion object {
        private val whites: MutableMap<Class<out Piece>, Image> = HashMap()
        private val blacks: MutableMap<Class<out Piece>, Image> = HashMap()

        private val pieceImages: MutableMap<PieceColor, Map<Class<out Piece>, Image>> = HashMap()

        init {
            pieceImages[PieceColor.WHITE] = whites
            pieceImages[PieceColor.BLACK] = blacks

            whites[Man::class.java] = CheckersImages.imageManWhite
            blacks[Man::class.java] = CheckersImages.imageManBlack

            whites[King::class.java] = CheckersImages.imageKingWhite
            blacks[King::class.java] = CheckersImages.imageKingBlack
        }

        /**
         * Выдать изображение для заданной шахматной фигуры.
         *
         * @param piece фигура для которой выдать изображение.
         * @return изображение
         */
        fun getChessPieceImage(piece: Piece): Image? {
            return if (piece.color == PieceColor.WHITE)
                whites[piece.javaClass]
            else
                blacks[piece.javaClass]
        }
    }
}