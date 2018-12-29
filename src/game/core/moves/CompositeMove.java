package game.core.moves;

import game.core.GameOver;
import game.core.Move;
import game.core.Piece;
import game.core.Square;

import java.util.ArrayList;

/**
 * Составной ход - последовательность простых ходов фигурой одного цвета.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class CompositeMove<T extends ITransferMove> implements ITransferMove {
    /**
     * Последовательность простых ходов.
     */
    private ArrayList<T> moves;

    /**
     * фигура которая делает ход.
     */
    private Piece piece;

    private CompositeMove() {
        moves = new ArrayList<>();
    }

    public CompositeMove(T move) {
        moves = new ArrayList<>();

        this.piece = move.getPiece();
        addMove(move);
    }

    public CompositeMove(Piece p) {
        moves = new ArrayList<>();

        this.piece = p;
    }

    /**
     * @return Вернуть последовательность простых ходов.
     */
    public ArrayList<T> getMoves() {
        return moves;
    }

    /**
     * Добавить простой ход фигурой фигуры к последовательности ходов.
     *
     * @param move - простой ход фигурой
     */
    public void addMove(T move) {
        moves.add(move);
    }

    @Override
    public void doMove() throws GameOver {
        for (Move move : moves)
            try {
                move.doMove();
            } catch (GameOver e) {
                throw new GameOver(e.result);
            }
    }

    @Override
    public void undoMove() {
        for (int k = moves.size() - 1; k >= 0; k--)
            moves.get(k).undoMove();
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public Square getSource() {
        return moves.get(0).getSource();
    }

    @Override
    public Square getTarget() {
        return moves.get(moves.size() - 1).getSource();
    }

    public CompositeMove<T> getClone() {
        CompositeMove<T> clone = new CompositeMove<>();
        clone.piece = piece;

        clone.moves.addAll(moves);

        return clone;
    }

    public boolean isEmpty() {
        return moves.isEmpty();
    }

    /**
     * @param square - проверяемая клетка.
     * @return Допустим ли ход на клетку square.
     */
    public boolean isAcceptable(Square square) {
        // Если фигура уже была на этой клетке, то ход недопустим.
        return moves
                .stream()
                .noneMatch(move -> move.getSource() == square);
    }

    @Override
    public String toString() {
        if (moves.isEmpty())
            return "????";

        StringBuilder s = new StringBuilder("" + moves.get(0).getSource());

        for (T c : moves)
            s.append("x").append(c.getTarget());

        return s.toString();
    }
}
