package chess.moves

import game.core.Piece
import game.core.Square
import game.core.moves.ITransferMove

/**
 * Простой ход европейских шахмат - перемещение фигуры на пустую клетку.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class SimpleMove(
        /**
         * Откуда перемещается.
         */
        override val source: Square,
        /**
         * Куда перемещается.
         */
        override val target: Square,
) : ITransferMove {
    /**
     * Какая фигура перемещается.
     */
    override var piece: Piece? = null

    init {
        piece = source.getPiece()!!
    }

    override fun doMove() {
        piece?.moveTo(target)
    }

    override fun undoMove() {
        piece?.moveTo(source)
    }

    override fun toString(): String = "$piece$source-$target"
}