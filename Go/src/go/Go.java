package go;

import game.core.*;
import game.core.players.Vinni;
import go.pieces.GoPiece;

import java.util.HashMap;
import java.util.Map;

/**
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%93%D0%BE">Го</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Go extends Game {
    private static final IPieceProvider pieceProvider = GoPiece::new;

    static {
        Game.addPlayer(Go.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Go.class, new Vinni(pieceProvider));
    }

    private Go(int boardSize) {
        super.initBoardPanel(boardSize, boardSize);

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }

    public Go() {
        this(10);
    }

    @Override
    public void initBoardDefault() {
        super.initBoardPanel(10, 10);
    }

    @Override
    public String getIconImageFile() {
        return "icoGo.png";
    }

    @Override
    public String getName() {
        return "Go";
    }

    @Override
    public BoardKind getBoardKind() {
        return BoardKind.ASIA;
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
                images.put(GoPiece.class, "wStone.png");
                break;
            case BLACK:
                images.put(GoPiece.class, "bStone.png");
                break;
            default:
                break;
        }

        return images;
    }

    @Override
    public Piece getPiece(Square square, PieceColor color) {
        return new GoPiece(square, color);
    }
}