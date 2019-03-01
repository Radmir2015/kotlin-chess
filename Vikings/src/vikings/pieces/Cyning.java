package vikings.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import vikings.moves.Capture;
import vikings.moves.SimpleMove;

import java.util.List;

/**
 * Фигура - король Викингов.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Cyning extends VikingsPiece {
    public Cyning(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0]; // Откуда идет.
        Square target = squares[1]; // Куда идет.

        // Соберем захваченные вражеские фигуры.
        List<Piece> captured = Capture.collectCaptured(source, target);

        // Если захваченные фигуры есть, то вернем ход - захват фигур.
        if (!captured.isEmpty())
            return new Capture(captured, squares);

        return new SimpleMove(squares);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        if (square.isEmptyVertical(target))
            return true;

        return square.isEmptyHorizontal(target);
    }

    @Override
    public String toString() {
        return "K";
    }
}
