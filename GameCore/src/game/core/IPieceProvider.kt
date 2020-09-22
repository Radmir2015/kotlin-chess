package game.core;

/**
 * Интерфейс для игр на которые ставятся фигуры.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IPieceProvider {
    /**
     * Предоставить фигуру заданного цвета стоящую на заданной клетке.
     *
     * @param square - клетка где должна встать фигура.
     * @param color  - цвет фигуры.
     * @return - преставляемая фигура.
     */
    Piece getPiece(Square square, PieceColor color);
}
