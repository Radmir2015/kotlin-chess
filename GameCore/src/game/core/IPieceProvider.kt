package game.core

/**
 * Интерфейс для игр на которые ставятся фигуры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface IPieceProvider {
    /**
     * Предоставить фигуру заданного цвета стоящую на заданной клетке.
     *
     * @param square - клетка где должна встать фигура.
     * @param color  - цвет фигуры.
     * @return - преставляемая фигура.
     */
    fun getPiece(square: Square, color: PieceColor): Piece
}