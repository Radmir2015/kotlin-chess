package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Слон в игре [Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Bishop(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val target = squares[0]
        if (!square.isDiagonal(target)) return false

        // Слон не может пойти на вражескую территорию.
        if (isEnemyPart(color, target)) return false

        // Слон ходит не дальше 2-х клеток.
        return abs(target.h - square.h) <= 2
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "E"
}