package game.core.moves;

import game.core.Move;
import game.core.Piece;

/**
 * Простейший ход.
 * Пропуск хода - ничего не делаем.
 * Пусть ходит противник.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class PassMove implements Move {
    @Override
    public void doMove() {
    }

    @Override
    public void undoMove() {
    }

    @Override
    public String toString() {
        return "Pass";
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
