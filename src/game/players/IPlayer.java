package game.players;

import game.core.Board;
import game.core.GameOver;
import game.core.GameResult;
import game.core.PieceColor;

/**
 * Интерфейс для игроков (программ и человека).
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IPlayer {
    /**
     * Простейшая реализация игрока.
     * Все действия на доске делает человек.
     */
    IPlayer HOMO_SAPIENCE = new IPlayer() {
        @Override
        public String getName() {
            return "Homo sapience";
        }

        @Override
        public String getAuthorName() {
            return "Это я";
        }

        @Override
        public void doMove(Board board, PieceColor color) {
        }

        @Override
        public String toString() {
            return getName();
        }
    };

    /**
     * Выдать имя игрока для отображения имени на панели игры.
     *
     * @return имя игрока.
     */
    String getName();

    /**
     * Выдать имя автора программы для отображения имени на панели игры.
     *
     * @return имя автора программы.
     */
    String getAuthorName();

    /**
     * Сделать ход на доске фигурой заданного цвета.
     *
     * @param board - доска для хода.
     * @param color - фигура какого цвета должна сделать ход.
     * @throws GameOver во время выполнения хода может возникнуть ситуация GameOver.
     * @see GameResult
     */
    void doMove(Board board, PieceColor color) throws GameOver;
}
