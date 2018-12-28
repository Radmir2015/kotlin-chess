package tamerlan.swing

import game.core.Game
import game.core.Piece
import game.core.PieceColor
import game.core.Square
import game.core.listeners.MovePieceListener
import game.swing.EuropeBoard
import game.swing.GamePanel
import tamerlan.TamerlanChess
import tamerlan.pieces.*
import tamerlan.swing.images.TamerlanChessImages
import java.awt.Image
import java.util.*

/**
 * Панель для игры в шахматы.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class TamerlanChessGamePanel : GamePanel(TamerlanChess()) {

    init {
        insertSquares(TamerlanChessBoardPanel(game))
    }
}

/**
 * Панель для игры в
 * [Шахматы Тамерлана](https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
internal class TamerlanChessBoardPanel(game: Game) : EuropeBoard(game.board) {

    init {
        listener = MovePieceListener(this)
    }

    override fun getImage(piece: Piece): Image? {
        return getPieceImage(piece, piece.color)
    }

    override fun getPiece(mouseSquare: Square?, moveColor: PieceColor?): Piece {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPieceImage(piece: Piece, color: PieceColor): Image? {
        return getTamerlanPieceImage(piece)
    }

    companion object {
        private var whites: MutableMap<Class<out Piece>, Image> = HashMap()
        private var blacks: MutableMap<Class<out Piece>, Image> = HashMap()

        init {
            // Инициализируем карту изображений белых фигур.
            //
            whites[Pawn::class.java] = TamerlanChessImages.imagePawnWhite
            whites[Rook::class.java] = TamerlanChessImages.imageRookWhite
            whites[Knight::class.java] = TamerlanChessImages.imageKnightWhite
            whites[Bishop::class.java] = TamerlanChessImages.imageBishopWhite
            whites[Queen::class.java] = TamerlanChessImages.imageQueenWhite
            whites[King::class.java] = TamerlanChessImages.imageKingWhite
            whites[Vizir::class.java] = TamerlanChessImages.imageVizirWhite
            whites[WarMachine::class.java] = TamerlanChessImages.imageWarMachineWhite
            whites[Giraffe::class.java] = TamerlanChessImages.imageGiraffeWhite

            // Инициализируем карту изображений черных фигур.
            //
            blacks[Pawn::class.java] = TamerlanChessImages.imagePawnBlack
            blacks[Rook::class.java] = TamerlanChessImages.imageRookBlack
            blacks[Knight::class.java] = TamerlanChessImages.imageKnightBlack
            blacks[Bishop::class.java] = TamerlanChessImages.imageBishopBlack
            blacks[Queen::class.java] = TamerlanChessImages.imageQueenBlack
            blacks[King::class.java] = TamerlanChessImages.imageKingBlack
            blacks[Vizir::class.java] = TamerlanChessImages.imageVizirBlack
            blacks[WarMachine::class.java] = TamerlanChessImages.imageWarMachineBlack
            blacks[Giraffe::class.java] = TamerlanChessImages.imageGiraffeBlack
        }

        /**
         * Выдать изображение для заданной шахматной фигуры.
         *
         * @param piece фигура для которой выдать изображение.
         * @return изображение
         */
        fun getTamerlanPieceImage(piece: Piece): Image? {
            return if (piece.color == PieceColor.WHITE)
                whites[piece.javaClass]
            else
                blacks[piece.javaClass]
        }
    }
}