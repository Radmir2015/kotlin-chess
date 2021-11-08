package game.core.players

import game.core.Board
import game.core.PieceColor

/**
 * Незнайка - простой игрок для игр в которых передвигают фигуры.
 * Он случайным образом выбирает ход из всех допустимых ходов.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class RemotePlayer @JvmOverloads constructor(private val maxMoves: Int = 80) : MovePiecePlayer() {
    override val name: String
        get() = "По сети"
    override val authorName: String
        get() = "Акжигитов Р.Р."

    override fun doMove(board: Board, color: PieceColor) {
    }

    override fun toString(): String = name
}