package game.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Общий предок всех игр.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class Game {

    /**
     * Карта для регистрации игроков для каждой из игр.
     */
    private static
    Map<Class<? extends Game>, List<IPlayer>> allPlayers = new HashMap<>();

    /**
     * Доска на которой происходит игра.
     */
    public Board board;

    protected Game() {
        board = new Board();
    }

    /**
     * Добавить игрока для данной игры.
     *
     * @param game   - игра для которой добавляют игрока.
     * @param player - игрок (программа или человек).
     */
    public static void addPlayer(Class<? extends Game> game, IPlayer player) {
        List<IPlayer> players = allPlayers.get(game);

        if (players == null) {
            // Игроков для данной игры еще не добавляли.
            players = new ArrayList<>();
            allPlayers.put(game, players);
        }

        players.add(player);
    }

    /**
     * Выдать счет для фигур заданного цвета.
     *
     * @param color - цвет фигур.
     * @return счет для заданного цвета фигур.
     */
    public int getScore(PieceColor color) {
        return board.getPieces(color).size();
    }

    /**
     * Получить список всех игрокода для заданной игры.
     *
     * @param game - заданная игра.
     * @return список игроков для этой игры.
     */
    public List<IPlayer> getPlayers(Class<? extends Game> game) {
        return allPlayers.get(game);
    }

    /**
     * Задать для игры новые размеры доски.
     *
     * @param nV - количество вертикалей.
     * @param nH - количество горизонталей.
     */
    public void initBoard(int nV, int nH) {
        board.reset(nV, nH);
    }

    /**
     * Сделать доску умалчиваемого размера
     * с умалчиваемой расстановкой фигур на доске.
     */
    abstract
    public void initBoardDefault();

    public Map<Class<? extends Piece>, String> getPieceImages(PieceColor color) {
        return null;
    }
}