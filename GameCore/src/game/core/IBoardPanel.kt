package game.core;

import game.core.listeners.IGameListener;
import game.core.listeners.IMouseMoveListener;

import java.util.List;

/**
 * Интерфейс панели изображающей доску для настольной игры.
 * Скрывает особености реализации такой панели с помощью
 * различных пакетов: Swing, SWT, JavaFX, ...
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
     * @param selectedPiece заданная фигура
     */
    void saveCursor(Piece selectedPiece);

    /**
     * Восстановить сохраненный курсор.
     */
    void restoreCursor();

    /**
     * Задать изображение курсора как у заданной фигуры.
     *
     * @param piece заданная фигура.
     */
    void pieceToCursor(Piece piece);

    /**
     * Получить фигуру заданного цвета стоящую на заданной клетке.
     *
     * @param mouseSquare заданая клетка
     * @param moveColor   заданный цвет
     * @return фигура
     */
    Piece getPiece(Square mouseSquare, PieceColor moveColor);

    /**
     * Задать слушателя нажатий кнопок мыши.
     *
     * @param listener слушатель
     */
    void setMouseClickListener(IGameListener listener);

    /**
     * Задать слушателя перемещения мыши.
     *
     * @param listener слушатель
     */
    void setMouseMoveListener(IMouseMoveListener listener);
}