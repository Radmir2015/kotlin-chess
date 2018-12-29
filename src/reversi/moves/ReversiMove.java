package reversi.moves;

import game.core.*;
import game.core.moves.ICaptureMove;
import game.core.moves.IPutMove;

import java.util.List;

/**
 * Фигура-камень для
 * <a href="https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8">Реверси</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class ReversiMove implements IPutMove, ICaptureMove {
    /**
     * Клетка на которую ставится фигура.
     */
    private Square target;

    /**
     * Клетки на которых стоят захваченные в плен вражеские фигуры.
     * Эти пленные фигуры поменяют цвет и будут вовать на нашей стороне.
     */
    private List<Square> captured;

    /**
     * Фигура которая делает ход.
     */
    private Piece piece;

    /**
     * Создать ход игры в реверси.
     *
     * @param target   - клетка на которую идет фигура
     * @param captured - клетки на которых стоят захваченные (перекрашеные).
     */
    public ReversiMove(Piece piece, Square target, List<Square> captured) {
        this.target = target;
        this.captured = captured;

        this.piece = piece;
    }

    @Override
    public void doMove() throws GameOver {
        target.setPiece(piece);
        changeCapturedColor();

        //
        // Проверим остались ли пустые клетки на доске.
        //
        Board board = target.getBoard();

        List<Square> empties = board.getEmptySquares();
        if (!empties.isEmpty()) return;

        //
        // Пустых клеток нет. Игра закончилась!
        //

        // Подсчитаем количество белых и черных фигур.
        // Выдадим наверх ситуацию GameOver - результат игры.
        int enemies = piece.getEnemies().size();
        int friends = piece.getFriends().size();

        if (enemies == friends)
            throw new GameOver(GameResult.DRAWN);

        // Я выиграл?
        boolean iWin = (enemies < friends);

        GameResult result = iWin
                ? GameResult.win(piece)
                : GameResult.lost(piece);
        throw new GameOver(result);
    }

    @Override
    public void undoMove() {
        piece.remove();
        changeCapturedColor();
    }

    /**
     * Изменить цвет захваченных фигур.
     */
    private void changeCapturedColor() {
        for (Square square : captured) {
            Piece capturedPiece = square.getPiece();
            PieceColor color = capturedPiece.getColor();
            PieceColor newColor = (color == PieceColor.WHITE
                    ? PieceColor.BLACK
                    : PieceColor.WHITE);
            capturedPiece.setColor(newColor);
        }
    }

    @Override
    public String toString() {
        return "" + target;
    }

    @Override
    public Square getTarget() {
        return target;
    }

    @Override
    public List<Square> getCaptured() {
        return captured;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
