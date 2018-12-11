package game.core;

/**
 * Интерфейс который должны булут реализовать все классы представляющие ходы
 * на доске настольных игр.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface Move {
    /**
     * Вернуть фигуру которая делает ход.
     *
     * @return
     */
    Piece getPiece();

    /**
     * Сделать ход (расстановку или перемещение фигур на доске).
     *
     * @throws GameOver
     */
    void doMove() throws GameOver;

    /**
     * Вернуть фигуры в состояние до сделанного ходя.
     */
    void undoMove();
}
