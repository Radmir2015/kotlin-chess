package halma.pieces;

import game.core.*;
import halma.moves.Jump;
import halma.moves.Step;

/**
 * Фигура для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Stone extends Piece {

    public Stone(Square square, PieceColor color) {
        super(square, color);
    }

    static
    public boolean isCorrectMove(Stone stone, Square source, Square target) {
        Board board = stone.square.getBoard();

        int dv = Math.abs(target.v - source.v);
        int dh = Math.abs(target.h - source.h);

        if (!target.isEmpty())
            return false; // На занятую клетку не ходим.

        if (dv > 0 && dh > 0)
            return false; // Ход не по горизонтали или вертикали.

        if ((dv == 2) && (dh == 0)) {
            // Есть ли через кого перепрыгнуть?
            int cv = (target.v + source.v) / 2;
            return !board.getSquare(cv, source.h).isEmpty();
        }

        if ((dv == 0) && (dh == 2)) {
            // Есть ли через кого перепрыгнуть?
            int ch = (target.h + source.h) / 2;
            return !board.getSquare(source.v, ch).isEmpty();
        }

        return (dv <= 1) && (dh <= 1);
    }

    private static Move createMove(Square source, Square target) {
        return source.distance(target) == 1
                ? new Step(source, target)
                : new Jump(source, target);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square source = square;
        Square target = squares[0];

        return isCorrectMove(this, source, target);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0];
        Square target = squares[1];

        return createMove(source, target);
    }
}
