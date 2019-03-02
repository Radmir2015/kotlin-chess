package game.core;

/**
 * Интерфейс для игр в которых можно показать текущий счет.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface IScorable {
    /**
     * Счет для Ыиграющего фигурами заданного цвета.
     *
     * @param color цвет фигур
     * @return счет игрока
     */
    int getScore(PieceColor color);
}