package game.core;

import java.util.List;

/**
 * Интерфейс панели изображающей доску для настольной игры.
 * Скрывает особености реализации такой панели с помощью
 * различных пакетов: Swing, SWT, JavaFX
 */
public interface IBoardPanel {
    /**
     * Выдать изображаемую доску для игры.
     *
     * @return доска игры изображаемая на этой панели.
     */
    Board getPanelBoard();

    /**
     * Выдать список клеток для подсказки клеток
     * на которые может пойти выбранная фигура.
     *
     * @return список клеток
     */
    List<Square> getPrompted();

    /**
     * Обновить изображение для сделаных изменений на доске.
     */
    void updateBoard();

    /**
     * Сохранить текущий курсор заменив его на курсор с изображением заданной фигуры.
     *
     * @param selectedPiece заданая фигура
     */
    void saveCursor(Piece selectedPiece);

    /**
     * Восстановить сохраненый курсор.
     */
    void restoreCursor();

    /**
     * Задать изображение курсора как у заданной фигуры.
     *
     * @param piece - заданая фигура.
     */
    void pieceToCursor(Piece piece);

    /**
     * Получить фигуру заданного цвета стоящую на заданной клетке.
     *
     * @param mouseSquare - заданая клетка
     * @param moveColor   -заданный цвет
     * @return фигура
     */
    Piece getPiece(Square mouseSquare, PieceColor moveColor);
}