package renju;

import game.core.Game;
import game.core.IPieceProvider;
import game.core.IPlayer;
import game.core.players.Vinni;
import renju.pieces.RenjuPiece;

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
}