package game.core.players

import game.core.Board
import game.core.IPlayer
import game.core.Move
import game.core.PieceColor

/**
 * Базовый класс для программ-игроков передвигающих фигуры на доске.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class MovePiecePlayer : IPlayer {
    /**
     * Фигура перемещается по доске.
     * Выдать все корректные ходы на доске фигурой заданного цвета.
     *
     * @param board - доска на которой идет игра.
     * @param color - цвет фигуры которая должна сделать ход.
     * @return список допустимых ходов.
     */
    open fun getCorrectMoves(board: Board, color: PieceColor): MutableList<Move> {
        val correctMoves: MutableList<Move> = mutableListOf()

        for (p in board.getPieces(color)) {
            // Собрали все клетки-цели на которые допустим ход фигуры р.
            val targets = board
                    .getSquares()
                    .filter { p.isCorrectMove(it) }
            for (target in targets) {
                val source = p.square
                val correctMove = p.makeMove(source, target)
                correctMove.let { correctMoves.add(it) }
            }
        }
        return correctMoves
    }
}