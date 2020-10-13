package reversi;

import game.core.*;
import game.core.players.Vinni;
import reversi.pieces.Hole;
import reversi.pieces.Stone;
import reversi.players.Owl;
import reversi.players.Tiger;

import java.util.HashMap;
import java.util.Map;

/**
 * Игра
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Reversi extends Game implements IScorable {
    private static final IPieceProvider pieceProvider = Stone::new;

    static {
        Game.addPlayer(Reversi.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Reversi.class, new Vinni(pieceProvider));
        Game.addPlayer(Reversi.class, new Owl(pieceProvider));
        Game.addPlayer(Reversi.class, new Tiger(pieceProvider));
    }

    /**
     * Вернуть инициализированную доску для игры в реверси.
     *
     * @param nHoles - количество случайно расположенных отверстий.
     */
    private Reversi(int nHoles) {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));

        if (nHoles != 0) {
            int randomV = (int) (8 * Math.random());
            int randomH = (int) (8 * Math.random());

            Square randomSquare = board.getSquare(randomV, randomH);
            new Hole(randomSquare, PieceColor.BLACK);
        }
    }

    public Reversi() {
        this(0);
    }

    @Override
    public void initBoardDefault() {
        super.initBoardPanel(8, 8);

        new Stone(board.getSquare(3, 3), PieceColor.BLACK);
        new Stone(board.getSquare(4, 4), PieceColor.BLACK);

        new Stone(board.getSquare(3, 4), PieceColor.WHITE);
        new Stone(board.getSquare(4, 3), PieceColor.WHITE);
    }

    @Override
    public String getName() {
        return "Reversi";
    }

    @Override
    public String getIconImageFile() {
        return "icoReversi.png";
    }

    @Override
    public BoardKind getBoardKind() {
        return BoardKind.PLAIN;
    }

    @Override
    public MoveKind getMoveKind() {
        return MoveKind.PIECE_PUT;
    }

    @Override
    public Map<Class<? extends Piece>, String> getPieceImages(PieceColor color) {
        Map<Class<? extends Piece>, String> images = new HashMap<>();

        switch (color) {
            case WHITE:
                images.put(Stone.class, "StoneWhite.png");
                break;
            case BLACK:
                images.put(Stone.class, "StoneBlack.png");
                break;
            default:
                break;
        }

        return images;
    }

    @Override
    public Piece getPiece(Square square, PieceColor color) {
        return new Stone(square, color);
    }
}