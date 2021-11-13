package game.core

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Клетка на доске для настольных игр.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Square(
        /**
         * Доска на которой расположена клетка.
         */
        val board: Board,
        /**
         * Вертикаль клетки.
         */
        @kotlin.jvm.JvmField
        val v: Int,
        /**
         * Горизонталь клетки.
         */
        @kotlin.jvm.JvmField
        val h: Int,
) {
    /**
     * Фигура которая, возможно, стоит на клетке.
     */
    @kotlin.jvm.JvmField
    var piece: Piece? = null

    /**
     * Получить доску клетки.
     *
     * @return - доска на которой находится клетка
     */
    private fun setPiece(piece: Piece, square: Square) {
        this.piece = piece
        piece.square = square
    }

    /**
     * @return - фигура которая стоит на клетке.
     */
    fun getPiece(): Piece? = piece

    /**
     * Поставить на клетку фигуру.
     *
     * @param piece -  какую фигуру поставить.
     */
    fun setPiece(piece: Piece) = setPiece(piece, this)

    /**
     * Удалить фигуру с клетки.
     */
    fun removePiece() {
        piece = null
    }

    /**
     * Вернуть букву для вертикали доски.
     *
     * @return - буква для обозначения вертикали (a..z)
     */
    private val vLetter: String
        get() = ALPHABET.substring(v, v + 1)

    /**
     * Выдать номер горизонтали клетки.
     * Клетки нумеруются снизу вверх.
     *
     * @return - номер горизонтали клетки
     */
    private val hNumber: Int
        get() = board.nH - h

    /**
     * @return Пустая ли клетка?
     */
    val isEmpty: Boolean
        get() = piece == null

    /**
     * Пустые ли клетки на диагонали между текущей клеткой и клеткой **s**?
     *
     * @param s - вторая клетка для сравнения.
     * @return - пустые ли клетки на диагонали между текущей клеткой и клеткой **s**.
     */
    fun isEmptyDiagonal(s: Square): Boolean {
        if (!isDiagonal(s)) return false
        val n = abs(v - s.v)
        val dv = if (v < s.v) 1 else -1
        val dh = if (h < s.h) 1 else -1
        for (k in 1 until n) if (!board.isEmpty(v + dv * k, h + dh * k)) return false
        return true
    }

    /**
     * Проходит ли диагональ между текущей клеткой и клеткой **s**?
     *
     * @param s - вторая клетка.
     * @return - расположены ли две клетки на диагонали.
     */
    fun isDiagonal(s: Square): Boolean = abs(h - s.h) == abs(v - s.v)

    /**
     * Пустая ли горизонталь из текущей клетки в клетку **s**?
     *
     * @param s - вторая клетка.
     * @return - пустая ли горизонталь из текущей клетки в клетку **s**?
     */
    fun isEmptyVertical(s: Square): Boolean {
        if (!isVertical(s)) return false
        val min = min(h, s.h)
        val max = max(h, s.h)
        for (k in min + 1 until max) if (!board.isEmpty(v, k)) return false
        return true
    }

    /**
     * Проходит ли вертикаль из текущей клетки в клетку **s**?
     *
     * @param s - вторая клетка.
     * @return - проходит ли вертикаль из текущей клетки в клетку **s**?
     */
    fun isVertical(s: Square): Boolean = v == s.v

    /**
     * Пустая ли горизонталь из текущей клетки в клетку **s**?
     *
     * @param s - вторая клетка.
     * @return - пустая ли горизонталь из текущей клетки в клетку **s**?
     */
    fun isEmptyHorizontal(s: Square): Boolean {
        if (!isHorizontal(s)) return false
        val min = min(v, s.v)
        val max = max(v, s.v)
        for (k in min + 1 until max) if (!board.isEmpty(k, h)) return false
        return true
    }

    /**
     * Проходит ли горизонталь из текущей клетки в клетку **s**?
     *
     * @param s - вторая клетка.
     * @return - проходит ли горизонталь из текущей клетки в клетку **s**?
     */
    fun isHorizontal(s: Square): Boolean = h == s.h

    /**
     * Близко ли текущая клетка к клетке **s**?
     *
     * @param s - вторая клетка.
     * @return - близко ли текущая клетка к клетке **s**.
     */
    fun isNear(s: Square): Boolean = abs(h - s.h) <= 1 && abs(v - s.v) <= 1

    /**
     * Переместить фигуру с текущей клетки на клетку **target**.
     *
     * @param target -
     * на какую клетку переместить фигуру с текущей клетки.
     */
    fun movePieceTo(target: Square) {
        // Переставили фигуру с текущей клетки на клетку target.
        piece?.let { target.setPiece(it) }

        // Очистили текущую клетку.
        piece = null
    }

    override fun toString() = vLetter + hNumber

    /**
     * @param d - направление
     * @return Существует ли на доске следующая клетка в направлении **d** от текущей клетки.
     */
    fun hasNext(d: Dirs) = board.onBoard(v + d.dv, h + d.dh)

    /**
     * @param d - направление
     * @return Существует ли на доске следующая клетка в направлении **d** от текущей клетки.
     */
    fun next(d: Dirs) = board.getSquare(v + d.dv, h + d.dh)

    /**
     * Выдать смещение между клетками - сумма смещений по вертикали и горизонтали.
     * Смещение между клетками может быть отрицательным.
     *
     * @param s - клетка куда идет фигура.
     * @return смещение между клетками target и source.
     */
    fun shift(s: Square): Int {
        val dv = s.v - v
        val dh = s.h - h
        return dv + dh
    }

    /**
     * Выдать расстояние между клетками доски -
     * сумму расстояний по вертикали и горизонтали.
     *
     * @param square@return Расстояние между клетками **target** и **source**.
     */
    fun distance(square: Square): Int {
        val dv = square.v - v
        val dh = square.h - h
        return abs(dv) + abs(dh)
    }

    companion object {
        private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

        fun parseSquaresFromMovesString(moves: String, board: Board): Pair<Square?, Square?> {
            val squares = moves.split("-")
                .map { it.replace("[A-Z]+".toRegex(), "") }

            return Pair(
                board.getSquare(ALPHABET.indexOf(squares[0][0]), 8 - squares[0][1].toString().toInt()),
                board.getSquare(ALPHABET.indexOf(squares[1][0]), 8 - squares[1][1].toString().toInt())
            )
        }
    }
}