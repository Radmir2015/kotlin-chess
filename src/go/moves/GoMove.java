package go.moves;

import game.core.*;
import game.core.moves.IPutMove;

import java.util.List;

public abstract class GoMove implements IPutMove {
    /**
     * Какую фигуру поставят.
     */
    Piece piece;

    @Override
    public Piece getPiece() {
        return piece;
    }

    void checkGameEnd(Piece piece) throws GameOver {
        // Проверим остались ли пустые клетки на доске.
        Board board = piece.square.getBoard();

        List<Square> empties = board.getEmptySquares();
        if (!empties.isEmpty()) return;

        // Подсчитаем количество белых и черных.
        // Выдадим результат игры.
        int enemies = piece.getEnemies().size();
        int friends = piece.getFriends().size();

        if (enemies == friends)
            throw new GameOver(GameResult.DRAWN);

        boolean iWin = (enemies < friends);

        GameResult result = iWin
                ? GameResult.win(piece)
                : GameResult.lost(piece);

        throw new GameOver(result);
    }

}