/*

 */
package game.core

import game.core.Board.Companion.getOpponentColor

/**
 * Фигура стоящая на клетке доски.
 * Абстрактный базовый класс для всех фигур всех игр.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class Piece protected constructor(@JvmField var square: Square, var color: PieceColor) {
    init {
        square.setPiece(this)
    }

    /**
     * Удалить фигуру с доски.
     */
    fun remove() {
        square.piece = null
    }

    /**
     * Переместить фигуру на указанную клетку.
     *
     * @param target - куда переместить.
     */
    fun moveTo(target: Square) {
        square.piece = null
        target.setPiece(this)
        square = target
    }

    /**
     * Стоит ли на клетке **s** вражеская фигура.
     *
     * @param s - проверяемая клетка.
     * @return стоит ли фигура
     */
    fun hasEnemy1(s: Square): Boolean = if (s.isEmpty) false else s.getPiece()!!.color != color

    /**
     * Стоит ли на клетке **s** своя фигура.
     *
     * @param s - проверяемая клетка.
     * @return стоит ли фигура
     */
    fun hasFriend(s: Square): Boolean = if (s.isEmpty) false else s.getPiece()!!.color == color

    /**
     * Стоит ли по направлению **d** вражеская фигура.
     *
     * @param d - проверяемое направление
     * @return стоит ли фигура
     */
    fun hasEnemy(d: Dirs): Boolean {
        val s = square.board.getSquare(square.v + d.dv, square.h + d.dh)
        return if (s!!.isEmpty) false else s.getPiece()!!.color != color
    }

    /**
     * Стоит ли по направлению **d** своя фигура.
     *
     * @param d - проверяемое направление
     * @return стоит ли фигура
     */
    fun hasFriend(d: Dirs): Boolean {
        val s = square.board.getSquare(square.v + d.dv, square.h + d.dh)
        return if (s!!.isEmpty) false else s.getPiece()!!.color == color
    }

    /**
     * Существует ли на доске следующая клетка в направлении **d** от текущей клетки.
     *
     * @param d - направление
     * @return есть ли клетка
     */
    fun hasNext(d: Dirs): Boolean = square.board.onBoard(square.v + d.dv, square.h + d.dh)

    /**
     * Существует ли на доске следующая клетка в направлении **d** от текущей клетки.
     *
     * @param d направление просмотра
     * @return есть ли клетка
     */
    fun next(d: Dirs): Square? = square.board.getSquare(square.v + d.dv, square.h + d.dh)

    /**
     * Вернуть список своих фигур для этой фигуры.
     *
     * @return - список друзей.
     */
    val friends: List<Piece>
        get() = square.board.getPieces(color)

    /**
     * Вернуть список своих фигур для этой фигуры.
     *
     * @return - список друзей.
     */
    val enemies: List<Piece>
        get() {
            val enemyColor = getOpponentColor(color)
            return square.board.getPieces(enemyColor)
        }

    /**
     * Являются ли две фигуры врагами (они разного цвета).
     *
     * @param piece - проверяемая фигура.
     * @return разного цвета или нет.
     */
    fun isEnemy(piece: Piece): Boolean = piece.color != color

    /**
     * Являются ли две фигуры друзья (они одного цвета).
     *
     * @param piece - проверяемая фигура.
     * @return одного цвета или нет.
     */
    fun isFriend(piece: Piece): Boolean = piece.color == color

    val isWhite: Boolean
        get() = color == PieceColor.WHITE
    val isBlack: Boolean
        get() = color == PieceColor.BLACK

    /**
     * Является ли корректным ход фигурой для заданой последовательности клеток?
     *
     * @param squares - последтвательность клеток через которые перемещается фигура.
     * @return корректен ход или нет.
     */
    abstract fun isCorrectMove(vararg squares: Square?): Boolean

    /**
     * Сделать ход фигурой для заданой последовательности клеток
     * и вернуть описание хода для сохранения его в истории.
     *
     * @param squares - последтвательность клеток через которые перемещается фигура.
     * @return экжемпляр класса реализующего интерфейс **Move**.
     */
    abstract fun makeMove(vararg squares: Square?): Move?
}