package game.core

/**
 * Фигура, которая может делать последовательность коротких ходов.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface ITrackPiece {
    /**
     * Есть ли правильный ход из клетки square.
     *
     * @param square - проверяемая клетка
     * @return есть ли правильных ход из клетки.
     */
    fun hasCorrectMoveFrom(square: Square?): Boolean
}