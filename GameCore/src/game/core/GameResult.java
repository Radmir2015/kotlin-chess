package game.core;

/**
 * Возможные результаты игры.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum GameResult {
    WHITE_WIN, BLACK_WIN, DRAWN, UNKNOWN;

    static
    public GameResult lost(PieceColor color) {
        return color == PieceColor.WHITE ? BLACK_WIN : WHITE_WIN;
    }

    static
    public GameResult win(PieceColor color) {
        return color == PieceColor.BLACK ? BLACK_WIN : WHITE_WIN;
    }

    static
    public GameResult win(Piece piece) {
        return win(piece.getColor());
    }

    static
    public GameResult lost(Piece piece) {
        return lost(piece.getColor());
    }

    @Override
    public String toString() {
        switch (this) {
            case WHITE_WIN:
                return "1-0";
            case BLACK_WIN:
                return "0-1";
            case DRAWN:
                return "1/2-1/2";
            case UNKNOWN:
                return "*";
        }
        return "";
    }
}
