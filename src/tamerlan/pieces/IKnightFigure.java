package tamerlan.pieces;

import game.core.Square;

/**
 * Интерфейс фигур ходящих как конь.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class IKnightFigure {
    public static boolean isCorrectMove(Square source, Square target) {
        int dh = Math.abs(target.h - source.h);
        int dv = Math.abs(target.v - source.v);

        return ((dh == 1 && dv == 2) || (dh == 2 && dv == 1));
    }
}