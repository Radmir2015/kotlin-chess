package chinachess.pieces

import chinachess.moves.Capture
import chinachess.moves.SimpleMove
import game.core.Move
import game.core.PieceColor
import game.core.Square

/**
 * Пушка в игре [Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
class Gun(square: Square, color: PieceColor) : ChinaChessPiece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(*squares)) return false
        val gun = square
        val target = squares[0]

        // По пустым вертикалям и горизонталям
        // ходит как ладья не захватывая фигур.
        if (gun.isEmptyHorizontal(target)) return target.isEmpty
        if (gun.isEmptyVertical(target)) return target.isEmpty

        // Ни по кому не стреляем?
        if (target.isEmpty) return false

        // По НЕ пустым вертикалям и горизонталям можем стрелять,
        // если:
        // 		- между пушкой и целью есть только одна фигура-барьер.
        //  	- этот барьер рядом с пушкой.
        val board = square.board
        var v = target.v
        var h = target.h
        var nBarier = 0
        var barier: Square? = null

        // Стреляем по горизонтали?
        if (gun.isHorizontal(target)) {
            // Двинемся в сторону цели.
            val dv = if (target.v > gun.v) +1 else -1
            v = gun.v
            while (v != target.v) {
                val s = board.getSquare(v, h)

                // Пропустим пустую клетку.
                if (s!!.isEmpty) {
                    v += dv
                    continue
                }
                nBarier++
                barier = s
                v += dv
            }

            // Барьеров слишом много.
            return if (nBarier != 1) false else barier!!.isNear(square)

            // Барьер далеко.
        }

        // Стреляем по вертикали?
        if (gun.isVertical(target)) {
            // Двинемся в сторону цели.
            val dh = if (target.h > gun.h) +1 else -1
            h = gun.h + dh
            while (h != target.h) {
                val s = board.getSquare(v, h)

                // Пропустим пустую клетку.
                if (s!!.isEmpty) {
                    h += dh
                    continue
                }
                nBarier++
                barier = s
                h += dh
            }
            return if (nBarier != 1) false else barier!!.isNear(square)

            // Барьер далеко.
        }
        return false
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[1]
        return if (target.isEmpty) SimpleMove(*squares) else Capture(*squares)
    }

    override fun toString() = "C"
}