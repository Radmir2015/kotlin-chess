package tamerlan.move;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ICaptureMove;
import tamerlan.pieces.King;

import java.util.Collections;
import java.util.List;

/**
 * Ход шахмат Тамерлана - перемещение фигуры на клетку
 * с захватом фигуры противника.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Capture extends SimpleMove implements ICaptureMove {
    private Square capturedSquare;
    private Piece capturedPiece;

    public Capture(Square[] squares) {
        super(squares);

        capturedSquare = squares[1];
        capturedPiece = capturedSquare.getPiece();
    }

    @Override
    public void doMove() throws GameOver {
        capturedPiece.remove();
        super.doMove();

        if (capturedPiece instanceof King)
            throw new GameOver(GameResult.lost(capturedPiece));
    }

    @Override
    public void undoMove() {
        super.undoMove();
        capturedSquare.setPiece(capturedPiece);
    }

    @Override
    public String toString() {
        return "" + piece + source + "x" + target;
    }

    @Override
    public List<Square> getCaptured() {
        return Collections.singletonList(capturedSquare);
    }
}