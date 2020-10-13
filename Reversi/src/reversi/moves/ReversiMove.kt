package reversi.moves

import game.core.*
import game.core.GameResult.Companion.lost
import game.core.GameResult.Companion.win
import game.core.moves.ICaptureMove
import game.core.moves.IPutMove

/**
 * Фигура-камень для
 * [Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class ReversiMove
/**
 * Создать ход игры в реверси.
 *
 * @param target   - клетка на которую идет фигура
 * @param captured - клетки на которых стоят захваченные (перекрашеные).
 */(
        /**
         * Фигура которая делает ход.
         */
        override val piece: Piece,
        /**
         * Клетка на которую ставится фигура.
         */
        override val target: Square,
        /**
         * Клетки на которых стоят захваченные в плен вражеские фигуры.
         * Эти пленные фигуры поменяют цвет и будут вовать на нашей стороне.
         */
        override val captured: List<Square>,
) : IPutMove, ICaptureMove {

    @Throws(GameOver::class)
    override fun doMove() {
        target.setPiece(piece)
        changeCapturedColor()

        //
        // Проверим остались ли пустые клетки на доске.
        //
        val board = target.board
        val empties = board.emptySquares
        if (!empties.isEmpty()) return

        //
        // Пустых клеток нет. Игра закончилась!
        //

        // Подсчитаем количество белых и черных фигур.
        // Выдадим наверх ситуацию GameOver - результат игры.
        val enemies = piece.enemies.size
        val friends = piece.friends.size
        if (enemies == friends) throw GameOver(GameResult.DRAWN)

        // Я выиграл?
        val iWin = enemies < friends
        val result = if (iWin) win(piece) else lost(piece)
        throw GameOver(result)
    }

    override fun undoMove() {
        piece.remove()
        changeCapturedColor()
    }

    /**
     * Изменить цвет захваченных фигур.
     */
    private fun changeCapturedColor() {
        for (square in captured) {
            val capturedPiece = square.getPiece()
            val color = capturedPiece!!.color
            val newColor = if (color === PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
            capturedPiece.color = newColor
        }
    }

    override fun toString(): String {
        return "" + target
    }
}