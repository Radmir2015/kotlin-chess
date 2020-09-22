package game.core.moves

import game.core.Move
import game.core.Square

/**
 * Интерфейс для игр на которых фигура может захватывать фигуры противника.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface ICaptureMove : Move {
    /**
     * Вернуть клетки на которых стоят захваченные фигуры.
     */
    val captured: List<Square>
}