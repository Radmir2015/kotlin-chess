package reversi.pieces;

import game.core.*;
import game.core.moves.PassMove;
import reversi.moves.ReversiMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Фигура-камень для <a href=
 * "https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">
 * Реверси</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Stone extends Piece {

    public Stone(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        // Ход на занятую клетку невохможен.
        if (!target.isEmpty())
            return false;

        // Если рядом с клеткой нет вражеских фигур,
        // то ход туда не корректен.
        if (!hasEnemy(target))
            return false;

        // Если при ходе на клетку target не произойдет захват
        // вражеских фигур, то ход туда не корректен.
        return isPossibleCapture(target);
    }

    /**
     * Будут ли у фигуры враги (фигуры противоположного цвета), если ее
     * поставить на клетку target.
     * <p>
     * Если врагов нет, захватывать некого, и такой ход в реверси недопустим.
     *
     * @param target - проверяемая клетка.
     * @return - есть ли враги при постановке фигуры на эту клетку.
     */
    private boolean hasEnemy(Square target) {
        Board board = target.getBoard();
        PieceColor myColor = getColor();

        int tv = target.v;
        int th = target.h;

        // Цикл по всем 8-и направлениям.
        for (Dirs d : Dirs.ALL) {
            int v = tv + d.dv;
            int h = th + d.dh;

            // Клетки с координатами (v,h) нет,
            // вышли за пределы доски.
            if (!board.onBoard(v, h))
                continue;

            Square nearSquare = board.getSquare(v, h);
            if (nearSquare.isEmpty())
                continue; // Рядом пустая клетка.

            PieceColor pieceColor = nearSquare.getPiece().getColor();
            if (pieceColor != myColor)
                return true; // Нашли рядом врага.
        }

        return false; // Не нашли рядом рядом врага.
    }

    /**
     * Проверить произойдет захват вражеских фигур при постановке фигуры на
     * клетку.
     *
     * @param target - куда ставят фигуру.
     * @return - возможен ли захват вражеских фигур.
     */
    private boolean isPossibleCapture(Square target) {
        // Цикл по всем 8-и направлениям.
        for (Dirs direction : Dirs.ALL)
            if (hasCaptured(target, direction))
                return true; // Захват фигур хотя бы по одному направлению
        // возможен.

        return false;
    }

    /**
     * Возможен ли захват фигуры при движении из заданной клетки
     * в заданном направлении.
     *
     * @param source    - из какой клетки двигаемся.
     * @param direction - в каком направлении двигаемся.
     * @return - возможен ли захват вражеских фигур.
     */
    private boolean hasCaptured(Square source, Dirs direction) {
        Board board = source.getBoard();

        int sv = source.v + direction.dv;
        int sh = source.h + direction.dh;

        PieceColor myColor = getColor();
        int nCaptured = 0;

        while (board.onBoard(sv, sh)) {
            Square nextSquare = board.getSquare(sv, sh);

            // На другом конце друга нет. Окружить нельзя.
            if (nextSquare.isEmpty())
                return false;

            PieceColor nextColor = nextSquare.getPiece().getColor();

            // Это друг. Окружаем я с одной стороны, он с другой.
            if (nextColor == myColor)
                return nCaptured > 0; // Стоят ли враги между нами?

            // Фигура другого цвета. Это враг.
            // Сосчитаем его и ищем следующего.
            nCaptured++;

            // Смещаемся в заданном направлении.
            sv += direction.dv;
            sh += direction.dh;
        }

        return false;
    }

    /**
     * Двигаясь из клетки <b>source</b> (куда ставится наша фигура), в заданном
     * направлении, в список captured собираются клетки, на которых стоят
     * вражеские фигуры.
     *
     * @param source    - из какой клетки движемся.
     * @param direction - в каком направлении движемся.
     * @param captured  - список в который добавляем клетки с вражескими фигурами.
     */
    private void collectCaptured(Square source, Dirs direction, List<Square> captured) {
        Board board = source.getBoard();

        int sv = source.v + direction.dv;
        int sh = source.h + direction.dh;

        PieceColor myColor = getColor();

        while (board.onBoard(sv, sh)) {
            Square nextSquare = board.getSquare(sv, sh);

            // На другом конце друга нет. Окружить нельзя.
            if (nextSquare.isEmpty())
                return;

            PieceColor nextColor = nextSquare.getPiece().getColor();

            // Это друг.
            if (nextColor == myColor)
                return; // Окружение закончили.

            // Фигура другого цвета. Это враг.
            // Запомним клетку на которой он стоит
            // ищем следующего.
            captured.add(nextSquare);

            // Смещаемся в заданном направлении.
            sv += direction.dv;
            sh += direction.dh;
        }
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[0];

        // Соберем захваченные вражеские фигуры.
        List<Square> captured = collectCaptured(target);

        if (captured.isEmpty())
            return new PassMove();

        return new ReversiMove(this, target, captured);
    }

    /**
     * Собрать все клетки, на которых стоят захваченные в плен (окруженные)
     * фигуры противника.
     *
     * @param target - клетка куда поставлена фигура.
     * @return - клетки с захваченными в плен фигурами противника.
     */
    private List<Square> collectCaptured(Square target) {
        List<Square> captured = new ArrayList<>();

        // Цикл по всем 8-и направлениям.
        for (Dirs direction : Dirs.ALL)
            if (hasCaptured(target, direction))
                collectCaptured(target, direction, captured);

        return captured;
    }

    @Override
    public String toString() {
        return "" + square;
    }
}
