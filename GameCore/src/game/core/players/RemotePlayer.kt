package game.core.players

import game.core.Board
import game.core.PieceColor

/**
 * Виртуальный игрок для возможности игры по сети
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