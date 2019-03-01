package chess;

import chess.pieces.*;
import game.core.*;
import game.core.players.Neznaika;

import java.util.HashMap;
import java.util.Map;

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

        // Расставляем коней.
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

    public Map<Class<? extends Piece>, String> getPieceImages(PieceColor color) {

        Map<Class<? extends Piece>, String> images = new HashMap<>();

        switch (color) {
            case WHITE:
                images.put(Pawn.class, "wPawnZurich.gif");
                images.put(Rook.class, "wRookZurich.gif");
                images.put(Knight.class, "wKnightZurich.gif");
                images.put(Bishop.class, "wBishopZurich.gif");
                images.put(Queen.class, "wQueenZurich.gif");
                images.put(King.class, "wKingZurich.gif");
                break;
            case BLACK:
                images.put(Pawn.class, "bPawnZurich.gif");
                images.put(Rook.class, "bRookZurich.gif");
                images.put(Knight.class, "bKnightZurich.gif");
                images.put(Bishop.class, "bBishopZurich.gif");
                images.put(Queen.class, "bQueenZurich.gif");
                images.put(King.class, "bKingZurich.gif");
                break;
        }

        return images;
    }

    @Override
    public MoveKind getMoveKind() {
        return MoveKind.PIECE_MOVE;
    }

    @Override
    public BoardKind getBoardKind() {
        return BoardKind.EUROPE;
    }

    @Override
    public String getName() {
        return "Chess";
    }

    @Override
    public String getIconImageFile() {
        return "bKnightZurich.gif";
    }
}