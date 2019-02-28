package chess;

import chess.pieces.*;
import game.core.Game;
import game.core.IPlayer;
import game.core.Piece;
import game.core.PieceColor;
import game.core.players.Neznaika;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
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

        URL resource = Chess.class.getResource("./images/wPawnZurich.gif");
        try {
            Image imagePawnWhite = ImageIO.read(resource);
            imagePawnWhite.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        images.put(Pawn.class, "wPawnZurich.gif");

        return images;
    }
}