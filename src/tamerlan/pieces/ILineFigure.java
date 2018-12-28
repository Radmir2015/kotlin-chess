package tamerlan.pieces;

import game.core.Square;

/**
 * Фигура которая двигается по вертикали и горизонтали.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ILineFigure {
    static boolean isCorrectMove(Square source, Square target) {
        if (source.isEmptyVertical(target))
            return true;

        return source.isEmptyHorizontal(target);
    }
}
