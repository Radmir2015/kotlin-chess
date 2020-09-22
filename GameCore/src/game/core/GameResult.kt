package game.core

/**
 * Возможные результаты игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
enum class GameResult {
    WHITE_WIN, BLACK_WIN, DRAWN, UNKNOWN;

    override fun toString(): String = when (this) {
        WHITE_WIN -> "1-0"
        BLACK_WIN -> "0-1"
        DRAWN -> "1/2-1/2"
        UNKNOWN -> "*"
    }

    companion object {
        @JvmStatic
        fun lost(color: PieceColor): GameResult = if (color === PieceColor.WHITE) BLACK_WIN else WHITE_WIN

        @JvmStatic
        fun win(color: PieceColor): GameResult = if (color === PieceColor.BLACK) BLACK_WIN else WHITE_WIN

        @JvmStatic
        fun win(piece: Piece): GameResult = win(piece.color)

        @JvmStatic
        fun lost(piece: Piece): GameResult = lost(piece.color)
    }
}