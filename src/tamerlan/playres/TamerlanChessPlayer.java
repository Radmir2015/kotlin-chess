package tamerlan.playres;

import game.core.*;
import game.players.MovePiecePlayer;
import tamerlan.pieces.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Базовый класс для всех игроков в шахматы Тамерлана.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class TamerlanChessPlayer extends MovePiecePlayer {
    /**
     * Максимальное расстояние между клетками доски.
     */
    static final int MAX_DISTANCE = 20;

    /**
     * Ценность (вес) поля на доске. Чем ближе поле к центру, том оно лучше.
     *
     * @param s - поле
     * @return вес поля.
     */
    static
    public int getSquareWeight(final Square s) {
        Board board = s.getBoard();
        final int tv = s.v;
        final int th = s.h;

        final double dv = Math.abs(tv - 0.5 * (board.nV - 1));
        final double dh = Math.abs(th - 0.5 * (board.nH - 1));
        int distance = (int) (dv + dh);

        return MAX_DISTANCE - distance;
    }

    @Override
    public void doMove(Board board, PieceColor color) throws GameOver {
        List<Move> correctMoves = getCorrectMoves(board, color);

        //		if (correctMoves.isEmpty()) // Пат.
        //			throw new GameOver(GameResult.DRAWN);

        if (correctMoves.isEmpty())
            return;

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

            // Распространяем инфрмацию об окончании игры.
            throw new GameOver(e.result);
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

    abstract Comparator<? super Move> getComparator();

    /**
     * Найти короля у фигур - врагов для фигуры piece.
     *
     * @param piece - фигура для которой ищем врага-короля.
     * @return вражеский король.
     */
    King getEnemyKing(Piece piece) {
        King enemyKing = null;
        for (Piece p : piece.getEnemies())
            if (p instanceof King) {
                enemyKing = (King) p;
                break;
            }
        return enemyKing;
    }

    /**
     * @param s1 первая клетка
     * @param s2 вторая клетка
     * @return расстояние между клетками.
     */
    protected int distance(Square s1, Square s2) {
        final double dv = Math.abs(s1.v - s2.v);
        final double dh = Math.abs(s1.h - s2.h);
        return (int) (dv + dh);
    }

    /**
     * Получить вес фигуры в шахматах Тамерлана.
     *
     * @param piece фигура в шахматах Тамерлана.
     * @return вес фигуры.
     */
    public int getPieceWeight(Piece piece) {
        if (piece instanceof Pawn) return 30 + 1;
        if (piece instanceof Knight) return 30 + 2;
        if (piece instanceof Bishop) return 30 + 3;
        if (piece instanceof Rook) return 30 + 4;
        if (piece instanceof Vizir) return 30 + 5;
        if (piece instanceof Queen) return 30 + 6;
        if (piece instanceof WarMachine) return 30 + 7;
        if (piece instanceof Giraffe) return 30 + 8;

        return 0;
    }
}