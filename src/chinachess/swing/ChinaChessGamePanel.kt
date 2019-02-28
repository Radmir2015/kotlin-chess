/**
 *
 */
package chinachess.swing

import chinachess.ChinaChess
import chinachess.pieces.*
import chinachess.swing.images.ChinaChessImages
import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.core.listeners.MovePiecePromptListener
import game.swing.AsiaBoardWithCastle
import game.swing.GamePanel
import java.awt.Graphics
import java.awt.Image
import java.util.*

/**
 * Панель для игры в китайские шахматы.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ChinaChessGamePanel : GamePanel(ChinaChess()) {
    init {
        insertSquares(ChinaChessBoardPanel(game))
    }
}

/**
 * Панель для доски китайских шахмат.
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class ChinaChessBoardPanel(game: Game) : AsiaBoardWithCastle(game) {
    init {
        mouseMoveListener = MovePiecePromptListener(this)
        listener = MovePieceListener(this)
    }

    override fun getImage(piece: Piece): Image? {
        return getPieceImage(piece, piece.color)
    }

    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawSquare(g: Graphics, v: Int, h: Int, sw: Int, sh: Int) {
        val dv = sw / 2
        val dh = sh / 2

        val x = v * sw + dv
        val y = h * sh + dh

        val isBlackMargin = h == 4
        val isWhiteMargin = h == board.nH - 5

        if (isBlackMargin || isWhiteMargin) {
            // Рисуем "реку" между территориями противников.
            if (v != 0) g.drawLine(x, y, x - dv, y)
            if (v != board.nV - 1) g.drawLine(x, y, x + dv, y)

            if (!isWhiteMargin) g.drawLine(x, y, x, y - dh)
            if (!isBlackMargin) g.drawLine(x, y, x, y + dh)
        } else
        // Обычные клетки зиатской доски с двумя крепостями.
            super.drawSquare(g, v, h, sw, sh)
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        return getChinaChessPieceImage(piece)
    }

    companion object {
        private var whites: MutableMap<Class<out Piece>, Image> = HashMap()
        private var blacks: MutableMap<Class<out Piece>, Image> = HashMap()

        init {
            // Инициализируем карту изображений белых фигур.
            //
            whites[Pawn::class.java] = ChinaChessImages.imagePawnWhite
            whites[Rook::class.java] = ChinaChessImages.imageRookWhite
            whites[Knight::class.java] = ChinaChessImages.imageKnightWhite
            whites[Bishop::class.java] = ChinaChessImages.imageBishopWhite
            whites[Gun::class.java] = ChinaChessImages.imageGunWhite
            whites[King::class.java] = ChinaChessImages.imageKingWhite
            whites[Guardian::class.java] = ChinaChessImages.imageGuardWhite

            // Инициализируем карту изображений черных фигур.
            //
            blacks[Pawn::class.java] = ChinaChessImages.imagePawnBlack
            blacks[Rook::class.java] = ChinaChessImages.imageRookBlack
            blacks[Knight::class.java] = ChinaChessImages.imageKnightBlack
            blacks[Bishop::class.java] = ChinaChessImages.imageBishopBlack
            blacks[Gun::class.java] = ChinaChessImages.imageGunBlack
            blacks[King::class.java] = ChinaChessImages.imageKingBlack
            blacks[Guardian::class.java] = ChinaChessImages.imageGuardBlack
        }

        /**
         * Выдать изображение для заданной шахматной фигуры.
         *
         * @param piece фигура для которой выдать изображение.
         * @return изображение
         */
        fun getChinaChessPieceImage(piece: Piece): Image? {
            return if (piece.color == PieceColor.WHITE)
                whites[piece.javaClass]
            else
                blacks[piece.javaClass]
        }
    }
}