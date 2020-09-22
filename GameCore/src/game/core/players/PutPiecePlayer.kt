package game.core.players

import game.core.Board
import game.core.IPieceProvider
import game.core.IPlayer
import game.core.Move

/**
 * Базовый класс для программ-игроков ставящих фигури на доску.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class PutPiecePlayer protected constructor(
        /**
         * Интерфейс для выдачи фигуры заданного цвета.
         * Это интерфейс должен быть реализованн для каждой игры.
         */
        @JvmField
        protected var pieceProvider: IPieceProvider,
) : IPlayer {
    /**
     * Фигура ставится на доску. Выдать все корректные ходы на доске фигурой
     * заданного цвета.
     *
     * @param board - доска на которой идет игра.
     * @return список допустимых ходов.
     */
    protected fun getCorrectMoves(board: Board): MutableList<Move> {
        val allCorrectMoves: MutableList<Move> = mutableListOf()

        // Соберем на доске все пустые клетки.
        val emptySquares = board.emptySquares
        if (emptySquares.isEmpty()) return allCorrectMoves // Пустых клеток нет, ходить некуда.

        // Какого цвета фигура должна пойти.
        val moveColor = board.moveColor

        // Получим НЕ стоящую на клетке фигуру заданного цвета.
        val square = emptySquares[0]
        val piece = pieceProvider.getPiece(square, moveColor)
        piece.remove() // Уберем фигуру с клетки доски.

        // Соберем пустые клетки, на которые можно поставить
        // новую фигуру заданного цвета.
        for (emptySquare in emptySquares)
            if (piece.isCorrectMove(emptySquare)) {
                val m = piece.makeMove(emptySquare)
                m.let { allCorrectMoves.add(it) }
            }
        return allCorrectMoves
    }
}