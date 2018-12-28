package checkers.moves;

import checkers.pieces.CheckersPiece;

/**
 * Составной ход - последовательность ходов-захватов фигур.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CompositeMove extends game.core.moves.CompositeMove<Capture> {
    public CompositeMove(CheckersPiece piece) {
        super(piece);
    }
}
