package tamerlan.pieces;

import game.core.Move;
import game.core.PieceColor;
import game.core.Square;
import tamerlan.move.Capture;
import tamerlan.move.SimpleMove;

public class Giraffe extends TamerlanPiece {

    public Giraffe(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[1];

        if (!target.isEmpty())
            return new Capture(squares);

        return new SimpleMove(squares);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        return IKnightFigure.isCorrectMove(square, target) ||
                IDiagonalFigure.isCorrectMove(square, target) ||
                ILineFigure.isCorrectMove(square, target);
    }

    @Override
    public String toString() {
        return "G";
    }
}
