package checkers.pieces;

import checkers.moves.Capture;
import checkers.moves.SimpleMove;
import game.core.*;

/**
 * Дамка в шашках.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends CheckersPiece {

    public King(Square square, PieceColor color) {
        super(square, color);
    }

    /**
     * Корректен ли ход королем из клетки source на клетку target.
     *
     * @param king   - король.
     * @param source - откуда идет кололь.
     * @param target - куда идет король.
     * @return корректен ли ход.
     */
    private static boolean isMoveCorrect(King king, Square source, Square target) {
        // На занятую клетку ходить нельзя.
        if (!target.isEmpty())
            return false;

        if (target.isDiagonal(source)) {
            if (target.isEmptyDiagonal(source)) {
                // Это простой ход дамкой.

                // Проверим может быть есть ходы с захватом фигуры.
                // В шашках такие ходы-захваты обязательны.
                // Простой ход не делаем.
                return king.hasNotCaptures();
            }

            Piece captured = getOneEnemyDiagonalPiece(source, target);
            return captured != null;
        }

        // TODO Checkers Сделать проверку правильности хода
        // из клетки source в клетку target.

        return false;
    }

    /**
     * Получить одну БЛИЖАЙШУЮ вражескую фигуру, стоящую на диагонали
     * из клетки source в клетку target, за которой пустая клетка.
     *
     * @param source - откуда идет король.
     * @param target - куда идет король.
     * @return вражеская фигура.
     */
    static
    private Piece getOneEnemyDiagonalPiece(Square source, Square target) {
        if (!source.isDiagonal(target) || source.isEmpty())
            return null;

        Board board = source.getBoard();
        Piece piece = source.getPiece();

        int n = Math.abs(source.v - target.v);
        int dv = source.v > target.v ? -1 : 1;
        int dh = source.h > target.h ? -1 : 1;

        int nEnemies = 0;
        Piece oneDiagonalPiece = null;

        for (int k = 1; k <= n - 1; k++) {
            int v = source.v + k * dv;
            int h = source.h + k * dh;

            Square tempSquare = board.getSquare(v, h);

            if (tempSquare.isEmpty()) {
                // Пустая клетка после 1-й найденой вражеской фигуры?
                if (nEnemies == 1)
                    return oneDiagonalPiece; // Нашли фигуру, выходим.

                // Продолжаем движение по пустым клеткам.
                continue;
            }

            Piece tempPiece = tempSquare.getPiece();

            if (tempPiece.isEnemy(piece)) {
                // Нашли вражескую фигуру.
                oneDiagonalPiece = tempPiece;
                nEnemies++;
            }
        }

        return null;
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square source = square;
        Square target = squares[0];

        return isMoveCorrect(this, source, target);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0];
        Square target = squares[1];

        return createMove(this, source, target);
    }

    /**
     * Создать ход королем из клетки source на клетку target.
     *
     * @param source - откуда идет король.
     * @param target - куда идет король.
     * @return ход королем.
     */
    public Move createMove(CheckersPiece piece, Square source, Square target) {
        Piece captured = getOneEnemyDiagonalPiece(source, target);

        assert captured != null;
        return source.isEmptyDiagonal(target)
                ? new SimpleMove(false, piece, source, target)
                : new Capture(false, piece, captured, source, target);
    }

    @Override
    protected boolean hasCapture(Square square) {
        // Двигаемся по всем диагоналям.
        for (Dirs d : Dirs.DIAGONAL) {
            Square s = square;

            while (s.hasNext(d)) {
                s = s.next(d);

                if (s.isEmpty()) // Клетка пустая.
                    continue;    // Продолжим движение дамки в направлении d.

                if (hasFriend(s))
                    break; // Через свою фигуру не перепрыгнешь.

                if (!s.hasNext(d))
                    break; // Вражеская фигура на краю доски.

                if (!s.next(d).isEmpty())
                    break; // Клетка для прыжка дамки занята.

                return true; // Клетка для прыжка дамки свободна.
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "K";
    }
}
