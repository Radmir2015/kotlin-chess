package checkers;

import checkers.pieces.Man;
import checkers.players.Skuperfield;
import checkers.players.Spruts;
import checkers.players.Znaika;
import game.core.Board;
import game.core.Game;
import game.core.IPlayer;
import game.core.PieceColor;
import game.players.Neznaika;

/**
 * Класс представляющий игру шашки.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Checkers extends Game {
    static {
        addPlayer(Checkers.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Checkers.class, new Neznaika());
        addPlayer(Checkers.class, new Znaika());
        addPlayer(Checkers.class, new Spruts());
        addPlayer(Checkers.class, new Skuperfield());
    }

    /**
     * Расстановка фигур шашек в начальную позицию.
     */
    public Checkers() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    /**
     * Расставить шашки на заданой горизонтали.
     *
     * @param board - доска на которую ставят шашки.
     * @param h     - горизонталь для расстановки
     * @param color - цвет шашек.
     */
    static private void setHorizontal(Board board, int h, PieceColor color) {
        for (int v = (h + 1) % 2; v < board.nV; v += 2)
            new Man(board.getSquare(v, h), color);
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);

        setHorizontal(board, 0, PieceColor.BLACK);
        setHorizontal(board, 1, PieceColor.BLACK);
        setHorizontal(board, 2, PieceColor.BLACK);

        setHorizontal(board, 5, PieceColor.WHITE);
        setHorizontal(board, 6, PieceColor.WHITE);
        setHorizontal(board, 7, PieceColor.WHITE);
    }
}