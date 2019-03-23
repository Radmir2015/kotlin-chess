package reversi.players;

import game.core.*;
import game.core.moves.PassMove;
import game.core.players.PutPiecePlayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Базовый класс для всех игроков в реверси.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class ReversiPlayer extends PutPiecePlayer {

    ReversiPlayer(IPieceProvider pieceProvider) {
        super(pieceProvider);
    }

    /**
     * @param s - проверяемая клетка.
     * @return Находится ли клетка на границе доски.
     */
    boolean isBorder(Square s) {
        Board b = s.getBoard();

        return (s.v == 0) ||
                (s.h == 0) ||
                (s.v == b.nV - 1) ||
                (s.h == b.nH - 1);
    }

    /**
     * @param s - проверяемая клетка.
     * @return Находится ли клетка в углу доски.
     */
    boolean isCorner(Square s) {
        Board b = s.getBoard();

        if ((s.v == 0) && (s.h == 0)) return true;
        if ((s.v == 0) && (s.h == b.nH - 1)) return true;
        if ((s.v == b.nV - 1) && (s.h == 0)) return true;
        return (s.v == b.nV - 1) && (s.h == b.nH - 1);
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        PieceColor enemyColor = Board.getOpponentColor(color);
        List<Piece> enemies = board.getPieces(enemyColor);

        if (enemies.isEmpty()) {
            // Врагов уже нет. Мы выиграли.
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.setResult(GameResult.win(color));

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            throw new GameOver(GameResult.win(color));
        }

        List<Move> correctMoves = getCorrectMoves(board, color);

        if (correctMoves.isEmpty()) {
            // Пропускаем ход.
            Move bestMove = new PassMove();

            // Сохраняем ход в истории игры.
            board.history.addMove(bestMove);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();
            return;
        }

        Collections.shuffle(correctMoves);

        correctMoves.sort(getComparator());
        Move bestMove = correctMoves.get(0);

        try {
            bestMove.doMove();
        } catch (GameOver e) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.addMove(bestMove);
            board.history.setResult(e.result);

            // Просим обозревателей доски показать
            // положение на доске, сделанный ход и
            // результат игры.
            board.setBoardChanged();

            throw new GameOver(GameResult.DRAWN);
        }

        // Сохраняем ход в истории игры.
        board.history.addMove(bestMove);

        // Просим обозревателей доски показать
        // положение на доске, сделанный ход и
        // результат игры.
        board.setBoardChanged();

        // Для отладки ограничим количество ходов в игре.
        // После этого результат игры ничья.
        if (board.history.getMoves().size() > 80) {
            // Сохраняем в истории игры последний сделанный ход
            // и результат игры.
            board.history.setResult(GameResult.DRAWN);

            // Сообщаем что игра закончилась ничьей.
            throw new GameOver(GameResult.DRAWN);
        }
    }

    protected abstract Comparator<? super Move> getComparator();
}