package renju;

import game.core.*;
import game.core.players.Vinni;
import renju.pieces.RenjuPiece;

import java.util.HashMap;
import java.util.Map;

/**
 * Игра <a href="https://ru.wikipedia.org/wiki/%D0%A0%D1%8D%D0%BD%D0%B4%D0%B7%D1%8E">Рэндзю</a>
 */
public class Renju extends Game {
    private static IPieceProvider pieceProvider = RenjuPiece::new;

    static {
        Game.addPlayer(Renju.class, IPlayer.HOMO_SAPIENCE);
        Game.addPlayer(Renju.class, new Vinni(pieceProvider));
    }

    public Renju() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Vinni(pieceProvider));
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(15, 15);
    }

    @Override
    public String getName() {
        return "Renju";
    }

    @Override
    public String getIconImageFile() {
        return "icoRenju.png";
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
                images.put(RenjuPiece.class, "StoneWhite.png");
                break;
            case BLACK:
                images.put(RenjuPiece.class, "StoneBlack.png");
                break;
        }

        return images;
    }

    @Override
    public Piece getPiece(Square square, PieceColor color) {
        return new RenjuPiece(square, color);
    }
}