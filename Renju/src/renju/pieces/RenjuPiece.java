package renju.pieces;

import game.core.Move;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import renju.moves.RenjuMove;

/**
 */
public class RenjuPiece extends Piece {
    public RenjuPiece(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        Square target = squares[0];

        return target.isEmpty();
    }

    @Override
    public Move makeMove(Square... squares) {
        Square target = squares[0];

        return new RenjuMove(this, target);
    }

    @Override
    public String toString() {
        return "" + square;
    }
}
