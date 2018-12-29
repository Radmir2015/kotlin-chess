package chess.moves;

/**
 * Интерфейс для ходов и обязательным или возможным захватом фигуры противника.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
interface ICapture {
    /**
     * @return Действительно ли происходит захват фигуры противника?
     */
    default boolean isCapture() {
        return true;
    }

    /**
     * Удалить с доски фигуру противника.
     */
    void removePiece();

    /**
     * Восттановить на доске фигуру противника.
     */
    void restorePiece();
}
