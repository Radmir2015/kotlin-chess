/*

 */
package game.core;

import java.util.List;

/**
 * Фигура стоящая на клетке доски.
 * Абстрактный базовый класс для всех фигур всех игр.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
abstract
public class Piece {
    /**
     * Цвет фигуры.
     */
    public PieceColor color;

    /**
     * Клетка на которой стоит фигура.
     */
    public Square square;

    public Piece(Square square, PieceColor color) {
        this.square = square;
        this.color = color;

        square.setPiece(this);
    }

    /**
     * @return вернуть цвет фигуры.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * @return задать цвет фигуры.
     */
    public void setColor(PieceColor color) {
        this.color = color;
    }

    /**
     * Удалить фигуру с доски.
     */
    public void remove() {
        if (square != null)
            square.piece = null;
        square = null;
    }

    /**
     * Переместить фигуру на указанную клетку.
     *
     * @param target - куда переместить.
     */
    public void moveTo(Square target) {
        if (square == null)
            System.out.println("" + target);

        square.piece = null;

        target.setPiece(this);
        square = target;
    }

    /**
     * Стоит ли на клетке <b>s</b> вражеская фигура.
     *
     * @param s - проверяемая клетка.
     * @return
     */
    public boolean hasEnemy(Square s) {
        if (s.isEmpty())
            return false;

        return s.getPiece().getColor() != getColor();
    }

    /**
     * Стоит ли на клетке <b>s</b> своя фигура.
     *
     * @param s - проверяемая клетка.
     * @return
     */
    public boolean hasFriend(Square s) {
        if (s.isEmpty())
            return false;

        return s.getPiece().getColor() == getColor();
    }

    /**
     * Стоит ли по направлению <b>d</b> вражеская фигура.
     *
     * @param d - проверяемое направление
     * @return
     */
    public boolean hasEnemy(Dirs d) {
        Square s = square.getBoard().getSquare(square.v + d.dv, square.h + d.dh);

        if (s.isEmpty())
            return false;

        return s.getPiece().getColor() != getColor();
    }

    /**
     * Стоит ли по направлению <b>d</b> своя фигура.
     *
     * @param d - проверяемое направление
     * @return
     */
    public boolean hasFriend(Dirs d) {
        Square s = square.getBoard().getSquare(square.v + d.dv, square.h + d.dh);

        if (s.isEmpty())
            return false;

        return s.getPiece().getColor() == getColor();
    }

    /**
     * Существует ли на доске следующая клетка в направлении <b>d</b> от текущей клетки.
     *
     * @param d - направление
     * @return
     */
    public boolean hasNext(Dirs d) {
        return square.getBoard().onBoard(square.v + d.dv, square.h + d.dh);
    }

    /**
     * Существует ли на доске следующая клетка в направлении <b>d</b> от текущей клетки.
     *
     * @param d
     * @return
     */
    public Square next(Dirs d) {
        return square.getBoard().getSquare(square.v + d.dv, square.h + d.dh);
    }

    /**
     * Вернуть список своих фигур для этой фигуры.
     *
     * @return - список друзей.
     */
    public List<Piece> getFriends() {
        return square.getBoard().getPieces(color);
    }

    /**
     * Вернуть список своих фигур для этой фигуры.
     *
     * @return - список друзей.
     */
    public List<Piece> getEnemies() {
        PieceColor enemyColor = Board.getOpponentColor(color);

        return square.getBoard().getPieces(enemyColor);
    }

    /**
     * Являются ли две фигуры врагами (они разного цвета).
     *
     * @param piece - проверяемая фигура.
     * @return разного цвета или нет.
     */
    public boolean isEnemy(Piece piece) {
        return piece.getColor() != getColor();
    }

    /**
     * Являются ли две фигуры друзья (они одного цвета).
     *
     * @param piece - проверяемая фигура.
     * @return одного цвета или нет.
     */
    public boolean isFriend(Piece piece) {
        return piece.getColor() == getColor();
    }

    public boolean isWhite() {
        return getColor() == PieceColor.WHITE;
    }

    public boolean isBlack() {
        return getColor() == PieceColor.BLACK;
    }

    /**
     * Является ли корректным ход фигурой для заданой последовательности клеток?
     *
     * @param squares - последтвательность клеток через которые перемещается фигура.
     * @return корректен ход или нет.
     */
    abstract public boolean isCorrectMove(Square... squares);

    /**
     * Сделать ход фигурой для заданой последовательности клеток
     * и вернуть описание хода для сохранения его в истории.
     *
     * @param squares - последтвательность клеток через которые перемещается фигура.
     * @return экжемпляр класса реализующего интерфейс <b>Move</b>.
     */
    abstract public Move makeMove(Square... squares);

}
