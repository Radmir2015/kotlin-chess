package chinachess.pieces

import game.core.Piece
import game.core.PieceColor
import game.core.Square

/**
 * Фигура в игре [
 * Китайские шахматы](https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8)
 *
 * @author [Dmitriv Y.](mailto:y.o.dmitriv@gmail.com)
 */
abstract class ChinaChessPiece(square: Square, color: PieceColor) : Piece(square, color) {
    // Если идем на клетку, занятую фигурой
    // того же цвета, то ход не корректен.
    override fun isCorrectMove(vararg squares: Square): Boolean {
        val target = squares[0]
        val piece: Piece = target.getPiece() ?: return true
        return isEnemy(piece)
    }


    /**
     * Находится ли клетка **square** внутри крепости для фигур цвета
     * **color**?
     *
     * @param color  - цвет крепости.
     * @param square - проверяемая клетка.
     * @return внутри крепости или нет.
     */
    fun outCastle(color: PieceColor, square: Square): Boolean {
        val minV = 3
        val maxV = minV + 2
        val minH = if (color === PieceColor.BLACK) 0 else 6
        val maxH = if (color === PieceColor.BLACK) 2 else 8
        return (minV > square.v || square.v > maxV
                ||
                minH > square.h || square.h > maxH)
    }

    /**
     * Пошла ли фигура цвета **color** на клетку **target** расположенную
     * на вражеской территории.
     *
     * @param color  - цвет фигуры которая пошла на поле **target**.
     * @param square - проверяемая клетка.
     * @return клетка **target** на вражеской территории или нет
     */
    fun isEnemyPart(color: PieceColor, square: Square): Boolean {
        val board = square.board
        val whiteInBlacks = square.h >= board.nH / 2
        val blackInWhites = square.h < board.nH / 2
        return if (color === PieceColor.BLACK) whiteInBlacks else blackInWhites
    }
}