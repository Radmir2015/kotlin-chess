package chinachess.pieces;

import chinachess.moves.Capture;
import chinachess.moves.SimpleMove;
import game.core.Board;
import game.core.Move;
import game.core.PieceColor;
import game.core.Square;

import java.util.Optional;

/**
 * Император в игре <a href="https://ru.wikipedia.org/wiki/%D0%A1%D1%8F%D0%BD%D1%86%D0%B8">
 * Китайские шахматы</a>
 *
 * @author <a href="mailto:y.o.dmitriv@gmail.com">Dmitriv Y.</a>
 */
public class King extends ChinaChessPiece {

    public King(Square square, PieceColor color) {
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

        // Особый случай - ход вне крепости.
        // Король может захватить вражеского короля,
        // если между ними пустая вертикаль.
        Board board = target.getBoard();
        PieceColor color = getColor();
        PieceColor opponentColor = Board.getOpponentColor(color);

        Optional<King> opponentKingOpt =
                board.getPieces(opponentColor)
                        .stream()
                        .filter(piece -> piece.getClass() == King.class)
                        .map(piece -> (King) piece)
                        .findAny();

        if (opponentKingOpt.isPresent()) {
            King opponentKing = opponentKingOpt.get();
            Square opponentSquare = opponentKing.square;

            // Пытаемся ли захватить?
            boolean isAttempt = (target == opponentSquare);

            // Возможно ли захватить?
            boolean isPossible = source.isEmptyVertical(opponentSquare);

            if (isAttempt && isPossible)
                return true; // Это захват короля противника.
        }

        // Другие ходы вне крепости для короля запрещены.
        if (outCastle(color, target))
            return false;

        int dv = Math.abs(target.v - source.v);
        int dh = Math.abs(target.h - source.h);

        // Допустимы только ходы на одну клетку
        // по вертикали и горизонтали.
        return ((dh == 1) && (dv == 0)) ||
                ((dh == 0) && (dv == 1));
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
        return "K";
    }
}
