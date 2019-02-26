package halma;

import game.core.*;
import game.core.players.Neznaika;
import halma.pieces.Stone;
import halma.players.Ants;
import halma.players.Beetles;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Halma extends Game {

    private static final short allowableBoardSizeNumb = 3;
    private static final short[] allowableBoardSize = {8, 10, 16};

    static {
        addPlayer(Halma.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Halma.class, new Neznaika());
        addPlayer(Halma.class, new Ants());
        addPlayer(Halma.class, new Beetles());
    }

    /**
     * Creates game board with proper sizes allowable for the Game.
     */
    public Halma(int boardSize) {
        super.initBoard(boardSize, boardSize);
        initializeParticularBoard(boardSize);

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    /**
     * Выдать счет для игрока играющего фигурами заданного цвета.
     *
     * @param board - доска для вычиления счета.
     * @param color - цвет фигур.
     * @return сумма расстояний от фигуры до клетки куда идет фигура.
     */
    static
    public int getScore(Board board, PieceColor color) {
        Square goal = getPieceGoal(board, color);

        return board.getPieces(color)
                .stream()
                .mapToInt(p -> p.square.distance(goal))
                .sum();
    }

    /**
     * В сторону какой клетки должна двигаться заданная фигура.
     *
     * @param piece - заданная фигура.
     * @return клетка в сторону которой фигура должна двигаться.
     */
    static
    public Square getPieceGoal(Piece piece) {
        return getPieceGoal(piece.square.getBoard(), piece.getColor());
    }

    private static Square getPieceGoal(Board board, PieceColor color) {
        return color == PieceColor.BLACK
                ? board.getSquare(0, 0)
                : board.getSquare(board.nV - 1, board.nH - 1);
    }

    @Override
    public int getScore(PieceColor color) {
        return getScore(board, color);
    }

    @Override
    public void initBoard(int nV, int nH) {
        super.initBoard(nV, nH);

        // Initialize board of the proper format
        for (short ind_sz = 0; ind_sz < Halma.allowableBoardSizeNumb; ++ind_sz)
            if (allowableBoardSize[ind_sz] == nV)
                initializeParticularBoard(allowableBoardSize[ind_sz]);
    }

    public Board getInitBoard1(int boardSize) {
        // Initialize board of the proper format
        for (short ind_sz = 0; ind_sz < Halma.allowableBoardSizeNumb; ++ind_sz)
            if (allowableBoardSize[ind_sz] == boardSize)
                return initializeParticularBoard(allowableBoardSize[ind_sz]);

        return null;
    }

    private Board initializeParticularBoard(int boardSize) {
        // Add Common Corner
        for (short i = 0; i < 4; ++i) {
            for (short j = 0; j < 4 - i; ++j) {
                new Stone(board.getSquare(i, j), PieceColor.WHITE);
                new Stone(board.getSquare(boardSize - i - 1, boardSize - j - 1), PieceColor.BLACK);
            }
        }
        // Add extra diagonal
        if (allowableBoardSize[1] <= boardSize) {
            for (short i = 0; i < 5; ++i) {
                new Stone(board.getSquare(i, 4 - i), PieceColor.WHITE);
                new Stone(board.getSquare(boardSize - i - 1, boardSize - 5 + i), PieceColor.BLACK);
            }
        }
        // Add short diagonal
        if (allowableBoardSize[2] == boardSize) {
            for (short i = 0; i < 4; ++i) {
                new Stone(board.getSquare(i + 1, 4 - i), PieceColor.WHITE);
                new Stone(board.getSquare(boardSize - i - 2, boardSize - 5 + i), PieceColor.BLACK);
            }
        }

        return board;
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(10, 10);
        initializeParticularBoard(10);
    }
}