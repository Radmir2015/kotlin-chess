package game.core.moves

import game.core.Move
import game.core.Square

/**
 * Интерфейс для игр на которых фигура передвигается на доске.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface ITransferMove : Move {
    /**
     * Вернуть клетку откуда пошла фигура.
     */
    val source: Square

    /**
     * Вернуть клетку куда пошла фигура.
     */
    val target: Square
}