package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Пешка в игре [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Pawn(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val source = square
        val target = squares[0]
        val isBlack = color === PieceColor.BLACK
        val dv = abs(target.v - source.v)
        val dh = when {
            isBlack -> target.h - source.h // Черная фигура идет вниз.
            else -> source.h - target.h // Белая фигура идет вверх.
        }

        // Пешка назад не ходит.
        if (dh < 0) return false

        if (dh == 1 && dv == 0)
            return true // Пешка пошла на один ход вперед.

        // Если пешка стоит на вражской территории,
        // то может пойти еще влево и вправо.
        return when {
            isEnemyPart(color, square) -> dh == 0 && dv == 1
            else -> false
        }
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "P"
}