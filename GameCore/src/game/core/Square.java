package game.core;

/**
 * Клетка на доске для настольных игр.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Square {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Вертикаль клетки.
     */
    public int v;

    /**
     * Горизонталь клетки.
     */
    public int h;
    /**
     * Фигура которая, возможно, стоит на клетке.
     */
    Piece piece;
    /**
     * Доска на которой расположена клетка.
     */
    private Board board;

    /**
     * Создать клетку на доске.
     *  @param board - доска где расположена клетка.
     * @param v     - вертикаль клетки.
     * @param h     - горизонталь клетки.
     */
    Square(Board board, int v, int h) {
        this.v = v;
        this.h = h;
        this.board = board;
    }

    private void setPiece(Piece piece, Square square) {
        this.piece = piece;
        piece.square = square;
    }

    /**
     * @return - фигура которая стоит на клетке.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Поставить на клетку фигуру.
     *
     * @param piece -  какую фигуру поставить.
     */
    public void setPiece(Piece piece) {
        setPiece(piece, this);
    }

    /**
     * Удалить фигуру с клетки.
     */
    public void removePiece() {
        piece = null;
    }

    /**
     * Вернуть букву для вертикали доски.
     *
     * @return - буква для обозначения вертикали (a..z)
     */
    private String getVLetter() {
        return ALPHABET.substring(v, v + 1);
    }

    /**
     * Выдать номер горизонтали клетки.
     * Клетки нумеруются синизу вверх.
     *
     * @return - номер горизонтали клетки
     */
    private int getHNumber() {
        return board.nH - h;
    }

    /**
     * Получить доску клетки.
     *
     * @return - доска на которой находится клетка
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     *
     * @return Пустая ли клетка?
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Пустые ли клетки на диагонали между текущей клеткой и клеткой <b>s</b>?
     *
     * @param s - вторая клетка для сравнения.
     * @return - пустые ли клетки на диагонали между текущей клеткой и клеткой <b>s</b>.
     */
    public boolean isEmptyDiagonal(Square s) {
        if (!isDiagonal(s))
            return false;

        int n = Math.abs(v - s.v);
        int dv = (v < s.v) ? 1 : -1;
        int dh = (h < s.h) ? 1 : -1;

        for (int k = 1; k <= n - 1; k++)
            if (!board.isEmpty(v + dv * k, h + dh * k))
                return false;

        return true;
    }

    /**
     * Проходит ли диагональ между текущей клеткой и клеткой <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - расположены ли две клетки на диагонали.
     */
    public boolean isDiagonal(Square s) {
        return Math.abs(h - s.h) == Math.abs(v - s.v);
    }

    /**
     * Пустая ли горизонталь из текущей клетки в клетку <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - пустая ли горизонталь из текущей клетки в клетку <b>s</b>?
     */
    public boolean isEmptyVertical(Square s) {
        if (!isVertical(s))
            return false;

        int min = Math.min(h, s.h);
        int max = Math.max(h, s.h);

        for (int k = min + 1; k < max; k++)
            if (!board.isEmpty(v, k))
                return false;

        return true;
    }

    /**
     * Проходит ли вертикаль из текущей клетки в клетку <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - проходит ли вертикаль из текущей клетки в клетку <b>s</b>?
     */
    public boolean isVertical(Square s) {
        return v == s.v;
    }

    /**
     * Пустая ли горизонталь из текущей клетки в клетку <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - пустая ли горизонталь из текущей клетки в клетку <b>s</b>?
     */
    public boolean isEmptyHorizontal(Square s) {
        if (!isHorizontal(s))
            return false;

        int min = Math.min(v, s.v);
        int max = Math.max(v, s.v);

        for (int k = min + 1; k < max; k++)
            if (!board.isEmpty(k, h))
                return false;

        return true;
    }

    /**
     * Проходит ли горизонталь из текущей клетки в клетку <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - проходит ли горизонталь из текущей клетки в клетку <b>s</b>?
     */
    public boolean isHorizontal(Square s) {
        return h == s.h;
    }

    /**
     * Близко ли текущая клетка к клетке <b>s</b>?
     *
     * @param s - вторая клетка.
     * @return - близко ли текущая клетка к клетке <b>s</b>.
     */
    public boolean isNear(Square s) {
        return (Math.abs(h - s.h) <= 1) &&
                (Math.abs(v - s.v) <= 1);
    }

    /**
     * Переместить фигуру с текущей клетки на клетку <b>target</b>.
     *
     * @param target -
     *               на какую клетку переместить фигуру с текущей клетки.
     */
    public void movePieceTo(Square target) {
        // Переставили фигуру с текущей клетки на клетку target.
        target.setPiece(piece);

        // Очистили текущую клетку.
        piece = null;
    }

    @Override
    public String toString() {
        return getVLetter() + getHNumber();
    }

    /**
     * @param d - направление
     * @return Существует ли на доске следующая клетка в направлении <b>d</b> от текущей клетки.
     */
    public boolean hasNext(Dirs d) {
        return board.onBoard(v + d.dv, h + d.dh);
    }

    /**
     * @param d - направление
     * @return Существует ли на доске следующая клетка в направлении <b>d</b> от текущей клетки.
     */
    public Square next(Dirs d) {
        return board.getSquare(v + d.dv, h + d.dh);
    }

    /**
     * Выдать смещение между клетками - сумма смещений по вертикали и горизонтали.
     * Смещение между клетками может быть отрицательным.
     *
     * @param s - клетка куда идет фигура.
     * @return смещение между клетками target и source.
     */
    public int shift(Square s) {
        int dv = s.v - v;
        int dh = s.h - h;

        return dv + dh;
    }

    /**
     * Выдать расстояние между клетками доски -
     * сумму расстояний по вертикали и горизонтали.
     *
     * @param square@return Расстояние между клетками <b>target</b> и <b>source</b>.
     */
    public int distance(Square square) {
        int dv = square.v - this.v;
        int dh = square.h - this.h;

        return Math.abs(dv) + Math.abs(dh);
    }
}