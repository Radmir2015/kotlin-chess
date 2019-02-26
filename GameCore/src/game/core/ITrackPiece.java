package game.core;

/**
 * Фигура, которая может делать последовательность коротких ходов.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ITrackPiece {
    /**
     * Есть ли правильный ход из клетки square.
     *
     * @param square - проверяемая клетка
     * @return есть ли правильных ход из клетки.
     */
    boolean hasCorrectMoveFrom(Square square);
}
