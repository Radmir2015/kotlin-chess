package halma;

import game.core.*;
import game.core.listeners.TrackPieceListener;
import game.core.players.Neznaika;
import halma.moves.Jump;
import halma.pieces.Stone;
import halma.players.Ants;
import halma.players.Beetles;

import java.util.HashMap;
import java.util.Map;

/**
 * Игра <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Halma extends Game implements ISizeable, IScorable {

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
    private Halma(int boardSize) {
        super.initBoardPanel(boardSize, boardSize);
        initializeParticularBoard(boardSize);

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    public Halma() {
        this(10);
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
    public void initBoardPanel(int nV, int nH) {
        super.initBoardPanel(nV, nH);

        // Initialize board of the proper format
        for (short ind_sz = 0; ind_sz < Halma.allowableBoardSizeNumb; ++ind_sz)
            if (allowableBoardSize[ind_sz] == nV)
                initializeParticularBoard(allowableBoardSize[ind_sz]);
    }

    private void initializeParticularBoard(int boardSize) {
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

    }

    @Override
    public void initBoardDefault() {
        super.initBoardPanel(10, 10);
        initializeParticularBoard(10);
    }

    @Override
    public void initBoardPanel(IBoardPanel board) {
        board.setMouseClickListener(new TrackPieceListener<Jump>(board));
    }

    @Override
    public String getIconImageFile() {
        return "icoHalma.png";
    }

    @Override
    public String getName() {
        return "Halma";
    }

    @Override
    public BoardKind getBoardKind() {
        return BoardKind.ASIA;
    }

    @Override
    public MoveKind getMoveKind() {
        return MoveKind.PIECE_TRACK;
    }

    @Override
    public Map<Class<? extends Piece>, String> getPieceImages(PieceColor color) {
        Map<Class<? extends Piece>, String> images = new HashMap<>();

        switch (color) {
            case WHITE:
                images.put(Stone.class, "wStone.png");
                break;
            case BLACK:
                images.put(Stone.class, "bStone.png");
                break;
            default:
                break;
        }

        return images;
    }

    @Override
    public int[][] getSizes() {
        return new int[][]{
                {8, 8},
                {10, 10},
                {16, 16}
        };
    }

    @Override
    public Piece getPiece(Square square, PieceColor pieceColor) {
        return null;
    }
}