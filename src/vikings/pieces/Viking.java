package vikings.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import vikings.moves.Capture;
import vikings.moves.SimpleMove;

import java.util.List;

/**
 * Фигура - викинг.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Viking extends VikingsPiece {
    public Viking(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        // Викинг не может пойти на трон.
        if (isExit(target))
            return false;

        // Викинг не может пойти на клетку выхода.
        if (isExit(target))
            return false;

        // Допустим ход по пустой вертикали.
        if (square.isEmptyVertical(target))
            return true;

        // Допустим ход по пустой горизонтали.
        if (square.isEmptyHorizontal(target))
            return true;

        return false;
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
    public String toString() {
        return "";
    }
}
