package chess.pieces;

import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;

/**
 * Базовый класс для всех шахматных фигур.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class ChessPiece extends Piece {
    public ChessPiece(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        if (target.isEmpty())
            return true;

        // Если идем на клетку, занятую фигурой
        // того же цвета, то ход не корректен.
        return getColor() != target.getPiece().getColor();
    }
}
