package game.core.moves

import game.core.Move
import game.core.Square

/**
 * Интерфейс для игр на которых фигура ставится на доску.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IPutMove : Move {
    /**
     * Вернуть клетку куда пошла фигура.
     */
    val target: Square
}