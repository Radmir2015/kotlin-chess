package chess.pieces;

import chess.moves.Capture;
import chess.moves.Castling;
import chess.moves.SimpleMove;
import game.core.*;

/**
 * Класс представляет шахматного короля.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class King extends ChessPiece {
    public King(Square square, PieceColor color) {
        super(square, color);
    }

    @Override
    public boolean isCorrectMove(Square... squares) {
        // Пока используем только умалчиваемую проверку
        // выполняемую в базовом классе.
        if (!super.isCorrectMove(squares))
            return false;

        Square target = squares[0];

        int kingV = square.v;
        int kingH = square.h;

        int dv = target.v - kingV;
        int dh = target.h - kingH;

        if (dv == 2) { // Ход вправо.
            //
            // Возможно короткая рокировка
            //

            if (outStartPosition())
                return false; // Ход не с начальной позиции.

            if (dh != 0)
                return false; // Ход не по горизонтали.

            Board board = square.getBoard();

            if (!board.isEmpty(kingV + 1, kingH))
                return false; // Между королем и ладьей фигура.

            if (!board.isEmpty(kingV + 2, kingH))
                return false; // Между королем и ладьей фигура.

            if (board.isEmpty(kingV + 3, kingH))
                return false; // Ладьи нет.

            Piece pieceH = board.getSquare(kingV + 3, kingH).getPiece();
            if (!(pieceH instanceof Rook))
                return false; // На вертикали H не ладья.

            // На вертикали H ладья противника.
            return pieceH.getColor() == getColor();
        }

        if (dv == -2) { // Ход влево.
            //
            // Возможно длинная рокировка
            //

            if (outStartPosition())
                return false; // Ход не с начальной позиции.

            if (dh != 0)
                return false; // Ход не по горизонтали.

            Board board = square.getBoard();

            if (!board.isEmpty(kingV - 1, kingH))
                return false; // Между королем и ладьей фигура.

            if (!board.isEmpty(kingV - 2, kingH))
                return false; // Между королем и ладьей фигура.

            if (!board.isEmpty(kingV - 3, kingH))
                return false; // Между королем и ладьей фигура.

            if (board.isEmpty(kingV - 4, kingH))
                return false; // Ладьи нет.

            Piece pieceA = board.getSquare(kingV - 4, kingH).getPiece();
            if (!(pieceA instanceof Rook))
                return false; // На вертикали A не ладья.

            // На вертикали A ладья противника.
            return pieceA.getColor() == getColor();
        }

        return square.isNear(target);
    }

    /**
     * Находится ли король в начальной позиции.
     *
     * @return начальная позиция?
     */
    private boolean outStartPosition() {
        int kingV = square.v;
        int kingH = square.h;

        if (kingV != 4) return true;

        final PieceColor color = getColor();

        if ((color == PieceColor.BLACK) && (kingH == 0))
            return false;

        return (color != PieceColor.WHITE) || (kingH != 7);
    }

    @Override
    public Move makeMove(Square... squares) {
        Square source = squares[0];
        Square target = squares[1];

        int dv = Math.abs(target.v - source.v);
        if (dv > 1)
            return new Castling(squares);

        if (!target.isEmpty())
            return new Capture(squares);

        return new SimpleMove(squares);
    }

    @Override
    public String toString() {
        return "K";
    }
}
