package chinachess.moves

import game.core.GameOver
import game.core.Piece
import game.core.Square
import game.core.moves.ITransferMove

/**
 * Простой ход китайских шахмат - перемещение фигуры на пустую клетку.
 *
 *
 * Игра [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class SimpleMove(vararg squares: Square) : ITransferMove {
    /**
     * Какая фигура перемещается.
     */
//    @JvmField
    override val piece: Piece?

    /**
     * Откуда перемещается.
     */
//    @JvmField
    override val source: Square = squares[0]

    /**
     * Куда перемещается.
     */
//    @JvmField
    final override val target: Square = squares[1]

    init {
        piece = source.getPiece()
    }

    @Throws(GameOver::class)
    override fun doMove() {
        piece!!.moveTo(target)
    }

    override fun undoMove() {
        piece!!.moveTo(source)
    }

    override fun toString(): String = "$piece$source-$target"
}