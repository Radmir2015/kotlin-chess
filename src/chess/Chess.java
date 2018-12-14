package chess;

import chess.pieces.*;
import game.core.Game;
import game.core.IPlayer;
import game.core.PieceColor;
import game.players.Neznaika;

/**
 * Класс представляющий игру шахматы.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Chess extends Game {

    static {
        addPlayer(Chess.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Chess.class, new Neznaika());
    }

    /**
     * Расстановка шахматных фигур в начальную позицию.
     */
    public Chess() {
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(new Neznaika());
    }

    @Override
    public void initBoardDefault() {
        super.initBoard(8, 8);

        // Расставляем пешки.
        for (int v = 0; v < board.nV; v++) {
            new Pawn(board.getSquare(v, 1), PieceColor.BLACK);
            new Pawn(board.getSquare(v, 6), PieceColor.WHITE);
        }

        // Расставляем ладьи.
        new Rook(board.getSquare(0, 0), PieceColor.BLACK);
        new Rook(board.getSquare(7, 0), PieceColor.BLACK);
        new Rook(board.getSquare(0, 7), PieceColor.WHITE);
        new Rook(board.getSquare(7, 7), PieceColor.WHITE);

        // Расставляем ладьи.
        new Knight(board.getSquare(1, 0), PieceColor.BLACK);
        new Knight(board.getSquare(6, 0), PieceColor.BLACK);
        new Knight(board.getSquare(1, 7), PieceColor.WHITE);
        new Knight(board.getSquare(6, 7), PieceColor.WHITE);

        // Расставляем слонов.
        new Bishop(board.getSquare(2, 0), PieceColor.BLACK);
        new Bishop(board.getSquare(5, 0), PieceColor.BLACK);
        new Bishop(board.getSquare(2, 7), PieceColor.WHITE);
        new Bishop(board.getSquare(5, 7), PieceColor.WHITE);

        // Расставляем ферзей.
        new Queen(board.getSquare(3, 0), PieceColor.BLACK);
        new Queen(board.getSquare(3, 7), PieceColor.WHITE);

        // Расставляем королей.
        new King(board.getSquare(4, 0), PieceColor.BLACK);
        new King(board.getSquare(4, 7), PieceColor.WHITE);
    }
}
