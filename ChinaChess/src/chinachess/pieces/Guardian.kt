package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Телохранитель (советник) в игре [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Guardian(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false
        val target = squares[0]

        // Ходы вне крепости для короля запрещены.
        if (outCastle(color, target)) return false
        val dv = abs(target.v - square.v)
        val dh = abs(target.h - square.h)

        // Допустимы только ходы на одну клетку
        // по диагонали.
        return dh == 1 && dv == 1
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "A"
}