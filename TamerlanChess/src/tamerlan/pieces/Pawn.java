package tamerlan.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import tamerlan.move.Capture;
import tamerlan.move.SimpleMove;

/**
 * Класс представляющий на доске пешку европейских шахмат.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Pawn extends TamerlanPiece {
    public Pawn(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square source = square;
        Square target = squares[0];

        // Вычислим смещение фигуры.
        int dv = Math.abs(target.v - source.v);
        int dh = getColor() == PieceColor.BLACK
                ? target.h - source.h  // Черная фигура идет вниз.
                : source.h - target.h; // Белая фигура идет вверх.

        // Пошли по диагонали на 1 клетку не пустую.
        if ((dv == 1) && (dh == 1) && !target.isEmpty())
            return true;

        // Пошли вперед на 1 клетку пустую.
        return (dv == 0) && (dh == 1) && target.isEmpty();
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[1];

        if (!target.isEmpty())
            return new Capture(squares);
        else return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "";
    }
}
