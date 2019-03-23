package vikings.moves;

import game.core.GameOver;
import game.core.GameResult;
import game.core.Piece;
import game.core.Square;
import game.core.moves.ITransferMove;
import vikings.pieces.Cyning;
import vikings.pieces.VikingsPiece;

/**
 * Простой ход викингов - перемещение фигуры на пустую клетку.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class SimpleMove implements ITransferMove {
    /**
     * Какая фигура перемещается.
     */
    final Piece piece;

    /**
     * Откуда перемещается.
     */
    final Square source;

    /**
     * Куда перемещается.
     */
    final Square target;

    /**
     * Простой ход без захвата фигур противника.
     *
     * @param squares - клетки хода.
     *
     *                <pre>
     *                                                                                                                         squares[0] откуда идет (source)
     *                                                                                                                         squares[1] куда идет (target)
     *                                                                                                                         </pre>
     */
    public SimpleMove(Square[] squares) {
        source = squares[0];
        target = squares[1];

        piece = source.getPiece();
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public Square getSource() {
        return source;
    }

    @Override
    public void doMove() throws GameOver {
        piece.moveTo(target);

        // Белый король добрался до выхода.
        if ((piece instanceof Cyning) && VikingsPiece.isExit(target))
            throw new GameOver(GameResult.WHITE_WIN);
    }

    @Override
    public void undoMove() {
        piece.moveTo(source);
    }

    @Override
    public String toString() {
        return "" + piece + source + "-" + target;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
