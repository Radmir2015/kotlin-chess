package tamerlan.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import tamerlan.move.Capture;
import tamerlan.move.SimpleMove;

/**
 * Класс представляет шахматного коня.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Knight extends TamerlanPiece {
    public Knight(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        return IKnightFigure.isCorrectMove(square, target);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[1];

        if (!target.isEmpty())
            return new Capture(squares);

        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "N";
    }
}
