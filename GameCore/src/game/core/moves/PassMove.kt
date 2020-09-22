package game.core.moves

import game.core.Move
import game.core.Piece

/**
 * Простейший ход.
 * Пропуск хода - ничего не делаем.
 * Пусть ходит противник.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class PassMove : Move {
    override fun doMove() {}
    override fun undoMove() {}
    override fun toString(): String = "Pass"

    override val piece: Piece?
        get() = null
}