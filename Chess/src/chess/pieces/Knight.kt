package chess.pieces

import chess.moves.Capture
import chess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Класс представляет шахматного коня.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Knight(square: Square?, color: PieceColor?) : ChessPiece(square!!, color!!) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Используем умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val target = squares[0]
        val dh = abs(target.h - square.h)
        val dv = abs(target.v - square.v)
        return dh == 1 && dv == 2 || dh == 2 && dv == 1
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (!target.isEmpty) Capture(square, target) else SimpleMove(square, target)
    }

    override fun toString(): String = "N"
}