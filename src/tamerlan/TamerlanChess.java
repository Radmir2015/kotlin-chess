package tamerlan;

import game.core.Board;
import game.core.Game;
import game.core.IPlayer;
import game.core.PieceColor;
import game.core.players.Neznaika;
import tamerlan.pieces.*;
import tamerlan.playres.Bayezid;
import tamerlan.playres.Nasir;
import tamerlan.playres.Tamerlan;
import tamerlan.playres.Tuqtamish;

/**
 * Расстановка фигур для <a href=
 * "https://ru.wikipedia.org/wiki/%D0%92%D0%B5%D0%BB%D0%B8%D0%BA%D0%B8%D0%B5_%D1%88%D0%B0%D1%85%D0%BC%D0%B0%D1%82%D1%8B">
 * Шахмат Тамерлана</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class TamerlanChess extends Game {

    static {
        addPlayer(TamerlanChess.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(TamerlanChess.class, new Neznaika());
        addPlayer(TamerlanChess.class, new Tamerlan());
        addPlayer(TamerlanChess.class, new Tuqtamish());
        addPlayer(TamerlanChess.class, new Bayezid());
        addPlayer(TamerlanChess.class, new Nasir());
    }

    public TamerlanChess() {
        super.initBoard(10, 10);

        putPieces(board, PieceColor.BLACK);
        putPieces(board, PieceColor.WHITE);

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    private static void putPieces(Board board, PieceColor color) {
        int rookH = (color == PieceColor.BLACK ? 0 : board.nH - 1);
        int pawnH = (color == PieceColor.BLACK ? 1 : board.nH - 2);
        int dh = (color == PieceColor.BLACK ? 1 : -1);

        // Расставляем пешки.
        for (int v = 0; v < board.nV; v++) {
            int ph = ((v != 4 && v != 5)) ? pawnH : pawnH + dh;

            new Pawn(board.getSquare(v, ph), color);
        }

        // Расставляем ладьи.
        new Rook(board.getSquare(0, rookH), color);
        new Rook(board.getSquare(9, rookH), color);

        // Расставляем коней.
        new Knight(board.getSquare(1, rookH), color);
        new Knight(board.getSquare(8, rookH), color);

        // Расставляем слонов.
        new Bishop(board.getSquare(2, rookH), color);
        new Bishop(board.getSquare(7, rookH), color);

        // Расставляем ферзей.
        new Queen(board.getSquare(3, rookH), color);

        // Расставляем королей.
        new King(board.getSquare(4, rookH), color);

        // Расставляем жирафов.
        new Giraffe(board.getSquare(5, rookH), color);

        // Расставляем визирей.
        new Vizir(board.getSquare(6, rookH), color);

        // Расставляем машины.
        new WarMachine(board.getSquare(4, pawnH), color);
        new WarMachine(board.getSquare(5, pawnH), color);
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(10, 10);

        putPieces(board, PieceColor.BLACK);
        putPieces(board, PieceColor.WHITE);
    }
}
