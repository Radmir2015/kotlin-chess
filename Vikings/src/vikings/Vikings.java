package vikings;

import game.core.*;
import game.core.players.Neznaika;
import vikings.pieces.Cyning;
import vikings.pieces.Viking;
import vikings.players.Haraldr;
import vikings.players.Rurik;
import vikings.players.William;

import java.util.HashMap;
import java.util.Map;

/**
 * Игра
 * <a href="https://ru.wikipedia.org/wiki/%D0%A5%D0%BD%D0%B5%D1%84%D0%B0%D1%82%D0%B0%D1%84%D0%BB">Викинги (Хнефатафл, Тавлеи) </a>.
 * <br/>
 *
 * <a href="http://celtica.narod.ru/hnef/default.html">Хнефатафл - игра викингов.</a>.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Vikings extends Game {

    static {
        addPlayer(Vikings.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Vikings.class, new Neznaika(180));
        addPlayer(Vikings.class, new Rurik());
        addPlayer(Vikings.class, new Haraldr());
        addPlayer(Vikings.class, new William());
    }

    private int center;

    /**
     * Создание доски заданного размера
     * и расстановка фигур для этого размера доски.
     *
     * @param boardSize - размер доски.
     */
    private Vikings(int boardSize) {
        super.initBoardPanel(boardSize, boardSize);

        switch (boardSize) {
            case 9:
                initBoardPanel(9, 9);
                break;
            case 11:
                initBoardPanel(11, 11);
                break;
        }

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    public Vikings() {
        this(9);
    }

    private static void setBlack(Board board, int v, int h) {
        Square square = board.getSquare(v, h);
        new Viking(square, PieceColor.BLACK);

        for (LineDirs dir : LineDirs.ALL) {
            if (board.onBoard(v + dir.dv, h + dir.dh)) {
                square = board.getSquare(v + dir.dv, h + dir.dh);
                new Viking(square, PieceColor.BLACK);
            }
        }
    }

    @Override
    public void initBoardPanel(int nV, int nH) {
        super.initBoardPanel(nV, nH);

        if (nV == 11) {
            center = 5;
            setRhombus(0, center, PieceColor.BLACK, 1);
            setRhombus(center, 0, PieceColor.BLACK, 1);
            setRhombus(nV - 1, center, PieceColor.BLACK, 1);
            setRhombus(center, nH - 1, PieceColor.BLACK, 1);

            setRhombus(center, center, PieceColor.WHITE, 2);
//			new Cyning(board.getSquare(center, center), PieceColor.WHITE);
        } else {
            center = 4;

            new Cyning(board.getSquare(center, center), PieceColor.WHITE);

            for (LineDirs dir : LineDirs.ALL)
                for (int k = 1; k < 3; k++) {
                    Square square = board.getSquare(center + k * dir.dv, center + k * dir.dh);
                    new Viking(square, PieceColor.WHITE);
                }

            for (LineDirs dir : LineDirs.ALL)
                setBlack(board, center + 4 * dir.dv, center + 4 * dir.dh);
        }
    }

    private void setRhombus(int centerV, int centerH, PieceColor color, int n) {
        int startV = centerV;
        int size = 1;
        int maxH = centerH + n + 1;

        for (int h = centerH - n; h < maxH; h++) {
            int maxV = startV + size;

            for (int v = startV; v < maxV; v++) {
                if (!board.onBoard(v, h))
                    continue;

                Square square = board.getSquare(v, h);

                if ((v == center) && (h == center))
                    new Cyning(square, PieceColor.WHITE);
                else new Viking(square, color);
            }
            int delta = (h >= centerH ? -1 : 1);
            size += 2 * delta;
            startV -= delta;
        }
    }

    /**
     * Создание доски размером 9х9
     * и расстановка фигур для этого размера доски.
     */
    private void initBoard9() {
        int c = 4;

        new Cyning(board.getSquare(c, c), PieceColor.WHITE);

        for (LineDirs dir : LineDirs.ALL)
            for (int k = 1; k < 3; k++) {
                Square square = board.getSquare(c + k * dir.dv, c + k * dir.dh);
                new Viking(square, PieceColor.WHITE);
            }

        for (LineDirs dir : LineDirs.ALL)
            setBlack(board, c + 4 * dir.dv, c + 4 * dir.dh);
    }

    @Override
    public void initBoardDefault() {
        super.initBoardPanel(9, 9);
        initBoard9();
    }

    @Override
    public String getName() {
        return "Vikings";
    }

    @Override
    public String getIconImageFile() {
        return "icoVikings.png";
    }

    @Override
    public BoardKind getBoardKind() {
        return BoardKind.PLAIN;
    }

    @Override
    public MoveKind getMoveKind() {
        return MoveKind.PIECE_MOVE;
    }

    public Map<Class<? extends Piece>, String> getPieceImages(PieceColor color) {
        Map<Class<? extends Piece>, String> images = new HashMap<>();

        switch (color) {
            case WHITE:
                images.put(Cyning.class, "wCyning.png");
                images.put(Viking.class, "bViking.png");
                break;
            case BLACK:
                images.put(Cyning.class, "bCyning.png");
                images.put(Viking.class, "bViking.png");
                break;
        }

        return images;
    }
}