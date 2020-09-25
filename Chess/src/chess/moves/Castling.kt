package chess.moves

import game.core.Square

/**
 * Ход европейских шахмат - рокировка.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Castling(source: Square, target: Square) : SimpleMove(source, target) {
    private var rookSource: Square
    private var rookTarget: Square

    override fun toString(): String = if (source.v < target.v) "O-O" else "O-O-O"

    init {
        val board = source.board
        if (source.v < target.v) {
            // Короткая рокировка.
            rookSource = board.getSquare(source.v + 3, source.h)!!
            rookTarget = board.getSquare(source.v + 1, source.h)!!
        } else {
            // Длинная рокировка.
            rookSource = board.getSquare(source.v - 4, source.h)!!
            rookTarget = board.getSquare(source.v - 1, source.h)!!
        }
    }

    /*
     * Переставить короля и ладью.
     */
    override fun doMove() {
        rookSource.movePieceTo(rookTarget)
        super.doMove()
    }

    /*
     * Вернуть короля и ладью в исходной состояние.
     */
    override fun undoMove() {
        rookTarget.movePieceTo(rookSource)
        super.undoMove()
    }
}