package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square
import kotlin.math.abs

/**
 * Конь в игре [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Knight(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false

        val knight = square
        val target = squares[0]
        val dh = abs(target.h - square.h)
        val dv = abs(target.v - square.v)
        val isKnightMove = dh == 1 && dv == 2 || dh == 2 && dv == 1
        if (!isKnightMove) return false

        // Китайские лошади не могут перепрыгнуть через фигуру.
        // Они маленькие, наверное их плохо кормят.
        // Посмотрим есть ли у лошади на пути фигура-барьер.
        var hBarier = 0
        var vBarier = 0
        if (dh > dv) {
            // Прыжок по горизонтали и ход в вертикали.
            hBarier = (knight.h + target.h) / 2
            vBarier = knight.v
        } else if (dh < dv) {
            // Прыжок по вертикали и ход в горизонтали.
            vBarier = (knight.v + target.v) / 2
            hBarier = knight.h
        }

        // Клетка на пути не пустая?
        return target.board.isEmpty(vBarier, hBarier)
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "H"
}