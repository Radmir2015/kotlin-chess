package chess;

import chess.pieces.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import game.core.*;
import game.core.players.Neznaika;
import game.core.players.RemotePlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс представляющий игру шахматы.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Chess extends Game {

    static {
        // TODO Акжигитов Реализация алгоритма игры двух человек
        // addPlayer(Chess.class, new ManPlayer());
        addPlayer(Chess.class, IPlayer.HOMO_SAPIENCE);
        addPlayer(Chess.class, new Neznaika());
        addPlayer(Chess.class, new RemotePlayer());
    }

    /**
     * Расстановка шахматных фигур в начальную позицию.
     */
    public Chess() {
        initDbConnection();
        initBoardDefault();

        board.setWhitePlayer(IPlayer.HOMO_SAPIENCE);
        board.setBlackPlayer(IPlayer.HOMO_SAPIENCE);
//        board.setBlackPlayer(new RemotePlayer());

    }

    public void initDbConnection() {
        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("Chess/src/chess/chess-notebook-firebase-adminsdk-yimvv-8c036f7e0f.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chess-notebook-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }

    @Override
    public void initBoardDefault() {
        super.initBoardPanel(8, 8);

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
            default:
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

    @Override
    public Piece getPiece(Square square, PieceColor pieceColor) {
        return null;
    }
}