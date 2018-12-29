package tamerlan.pieces;

import game.core.Square;

/**
 * Общие методы для диагональных фигур.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
interface IDiagonalFigure {
    static boolean isCorrectMove(Square square, Square target) {
        return square.isEmptyDiagonal(target);
    }
}
