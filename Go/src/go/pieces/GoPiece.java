package go.pieces;

import game.core.*;
import go.moves.Capture;
import go.moves.SimpleMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Фигура для игры в <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GoPiece extends Piece {
    public GoPiece(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        if (!target.isEmpty())
            return false; // На занятую клетку пойти нельзя.

        // Поставим временно фигуру на доску и посмотрим
        // может ли она "дышать" на этой клетке.
        // Ход корректен, если может.
        target.setPiece(this);
        List<Square> group = new ArrayList<>();
        boolean hasDame = hasDame(this, group);
        this.remove();

        return hasDame;
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[0];

        // Поставим временно фигуру на клетку target и соберем клетки
        // на которых стоят окруженные фигуры противника.
        target.setPiece(this);
        List<Square> captured = collectCaptured(target);
        this.remove();

        if (!captured.isEmpty()) // Окруженные фигуры противника есть.
            return new Capture(this, target, captured); // Ход-захват.

        // Простой ход без захвата фигур противника.
        return new SimpleMove(this, target);
    }

    /**
     * Собрать захваченные фигуры противника,
     * после того, как данная фигура пойдет на поле target.
     *
     * @param target - на какое поле пойдет эта фигура.
     * @return список захваченных фигур противника.
     */
    private List<Square> collectCaptured(Square target) {
        Board board = target.getBoard();
        PieceColor myColor = getColor();

        List<Square> group = new ArrayList<>();
        List<Square> captured = new ArrayList<>();

        for (int v = 0; v < board.nV; v++)
            for (int h = 0; h < board.nH; h++) {
                Square s = board.getSquare(v, h);

                if (s.isEmpty())
                    continue;

                Piece p = s.getPiece();

                // Свои фигуры не захватываем.
                if (p.getColor() == myColor)
                    continue;

                // Фигуры противника захватываем,
                // если они не могут дышать.
                group.clear();
                if (!hasDame(p, group))
                    captured.add(s);
            }

        return captured;
    }

    /**
     * Может ли фигура "дышать" если ее поставить на клетку <b>target</b>?
     * Есть ли у этой клетки или у соседних клеток с друзьями
     * (фигурами того же цвета)по вертикали или горизонтали
     * есть пустая соседняя клетка, то дышать может.
     *
     * @param piece - проверяемая фигура.
     * @return может ли фигура "дышать".
     */
    private boolean hasDame(Piece piece, List<Square> group) {
        Square target = piece.square;
        Board board = target.getBoard();
        PieceColor myColor = piece.getColor();

        if (group.contains(target))
            return false; // Эта клетка уже в группе, ее уже проверяли.

        // Занесем клетку у группу для избежания повторной проверки.
        group.add(target);

        // Направления для поиска свободных клеток.
        Dirs[] cross = {Dirs.LEFT, Dirs.RIGHT, Dirs.DOWN, Dirs.UP};

        //
        for (Dirs dir : cross) {
            int nearV = target.v + dir.dv;
            int nearH = target.h + dir.dh;

            // Координаты (nearV, nearH) за границей доски,
            // смотрим другие соседние с target клетки.
            if (!board.onBoard(nearV, nearH))
                continue;

            // Нашли свободную соседнюю клетку.
            // Значит "дышать" фигура на клетке target может.
            Square nearSquare = board.getSquare(nearV, nearH);
            if (nearSquare.isEmpty())
                return true;

            Piece nearPiece = nearSquare.getPiece();
            if (nearPiece.getColor() != myColor)
                continue; // Рядом вражеская фигура.

            // На соседней клетке стоит дружеская фигура.
            // Если она может дышать, то и фигуры всей группы
            // могут дышать (и фигура на клетке target тоже может).
            if (hasDame(nearPiece, group))
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "" + square;
    }
}
