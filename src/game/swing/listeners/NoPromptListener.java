package game.swing.listeners;

import game.core.Board;
import game.core.Piece;
import game.core.PieceColor;
import game.core.Square;
import game.swing.GameBoard;

import java.awt.*;


/**
 * Слушатель для игр в которых фигуры ставятся на доску.
 * Слушатель PutPiecePromptListener определяет клетки на которые можно поставить фигуру.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class NoPromptListener implements IMouseMoveListener {
    /**
     * Панель на которой отрисовывается доска.
     */
    private GameBoard boardPanel;

    public NoPromptListener(GameBoard boardPanel) {
        this.boardPanel = boardPanel;
    }

    @Override
    public void mouseMove(Square mouseSquare) {
        // Доска на которой расположены фигуры.
        Board board = boardPanel.getBoard();

        // Получим фигуру НЕ стоящую на клетке.
        PieceColor moveColor = board.getMoveColor();
        Piece piece = boardPanel.getPiece(mouseSquare, moveColor);

        if (piece == null)
            return;

        piece.remove(); // Уберем фигуру с доски.

        // Зададим изображение курсора такое как избражение у фигуры.
        Image pieceImage = boardPanel.getPieceImage(piece, moveColor);
        boardPanel.imageToCursor(pieceImage);
    }

//	/**
//	 * Выдать изображение фигуры заданного цвета.
//	 * 
//	 * @param piece - фигура.
//	 * @param color - цвет фигуры.
//	 * @return - изображение фигуры заданного цвета.
//	 */
//	abstract public Image getPieceImage(Piece piece, PieceColor color);
//
//	/**
//	 * Выдать фигуру заданного цвета.
//	 * 
//	 * @param square - клетка для фигуры.
//	 * @param color - цвет фигуры.
//	 * @return - фигура заданного цвета.
//	 */
//	abstract public Piece getPiece(Square square, PieceColor color);
}
