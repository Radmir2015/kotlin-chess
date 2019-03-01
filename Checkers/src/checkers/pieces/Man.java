package checkers.pieces;

import checkers.moves.Capture;
import checkers.moves.SimpleMove;
import game.core.*;

/**
 * Простая фигура в шашках.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Man extends CheckersPiece implements ITrackPiece {
    public Man(Square square, PieceColor color) {
        super(square, color);
    }

    private static boolean isCorrectMove(Man man, Square source, Square target) {
        // На занятую клетку ходить нельзя.
        if (!target.isEmpty())
            return false;

        // Вычислим смещение фигуры.
        int dv = target.v - source.v;
        int dh = man.isBlack()
                ? target.h - source.h  // Черная фигура идет вниз (от h=0 до h=7).
                : source.h - target.h; // Белая фигура идет вверх (от h=7 до h=0).

        // Отбросим ходы не по диагонали.
        // У диагонали смещения по абсолютной величине совпадают.
        boolean isDiagonal = (Math.abs(dh) == Math.abs(dv));
        if (!isDiagonal)
            return false;

        // Теперь у нас ход диагональный.
        if (Math.abs(dh) == 1) {
            // Смещение на 1 клетку по диагонали
            // - это простой ход без захвата.

            // Проверяем не хочет ли фигура пойти назад.
            // Если да, то ход неправильный.
            if (dh < 0)
                return false;

            // Проверяем не хочет ли фигура пойти занятую клетку.
            // Если да, то ход неправильный.
            if (!target.isEmpty())
                return false;

            // Может быть есть ходы с захватом.
            // В шашках такие ходы обязательны.
            return man.hasNotCaptures();// Все проверки фигура прошла. Ход правильный.
        } else if (Math.abs(dh) == 2)
            // Ход на две клетки - это простой ход с захватом?
            return man.isCapture(man, source, target);

        return false;
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square source = square;     // Клетка где стоит фигура.
        Square target = squares[0]; // Клетка куда должна пойти фигура.

        return isCorrectMove(this, source, target);
    }

    private boolean isCapture(CheckersPiece man, Square source, Square target) {
        // Смотрим клетку, через которую перепрыгнули.
        int capturedH = (source.h + target.h) / 2;
        int capturedV = (source.v + target.v) / 2;

        Board board = man.square.getBoard();
        Square capturedSquare = board.getSquare(capturedV, capturedH);

        // Прыгать через пустую клетку нельзя.
        if (capturedSquare.isEmpty())
            return false;

        Piece captured = capturedSquare.getPiece();

        // Прыгать через фигуру того же цвета нельзя.
        return !man.isFriend(captured);// Все проверки фигура прошла. Ход правильный.
    }

    @Override
    protected boolean hasCapture(Square square) {
        // Смотрим по всем диагоналям возможность захвата фигуры.
        for (Dirs d : Dirs.DIAGONAL) {
            if (!square.hasNext(d))
                continue;

            Square nextS = square.next(d);

            if (hasFriend(nextS))
                continue; // Нет вражеской фигуры для перепрыгивания.

            if (!nextS.hasNext(d))
                continue; // Вражеская фигура на краю доски.

            if (nextS.next(d).isEmpty())
                return true; // Клетка куда прыгаем пуста.
        }

        return false;
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0];
        Square target = squares[1];

        return createMove(this, source, target);
    }

    public Move createMove(CheckersPiece man, Square source, Square target) {
        boolean isCapture = Math.abs(target.v - source.v) == 2;
        boolean isPromotion = man.isBlack() ? target.h == 7 : target.h == 0;

        Move move;

        if (!isCapture)
            move = new SimpleMove(isPromotion, man, source, target);
        else {
            int capturedH = (source.h + target.h) / 2;
            int capturedV = (source.v + target.v) / 2;

            Board board = man.square.getBoard();
            Square capturedSquare = board.getSquare(capturedV, capturedH);

            Piece capturedPiece = capturedSquare.getPiece();

            move = new Capture(isPromotion, man, capturedPiece, source, target);
        }

        return move;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean hasCorrectMoveFrom(Square square) {
        return hasCapture(square);
    }
}
