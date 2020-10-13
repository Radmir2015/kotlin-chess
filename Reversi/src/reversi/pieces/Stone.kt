package reversi.pieces

import game.core.*
import game.core.moves.PassMove
import reversi.moves.ReversiMove

/**
 * Фигура-камень для [
 * Реверси](https://ru.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B2%D0%B5%D1%80%D1%81%D0%B8)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Stone(square: Square, color: PieceColor) : Piece(square, color) {
    override fun isCorrectMove(vararg squares: Square): Boolean {
        val target = squares[0]

        // Ход на занятую клетку не корректен.
        if (!target.isEmpty) return false

        // Если рядом с клеткой нет вражеских фигур,
        // то ход туда не корректен.
        return if (!hasEnemy(target)) false else isPossibleCapture(target)

        // Если при ходе на клетку target не произойдет захват
        // вражеских фигур, то ход туда не корректен.
    }

    /**
     * Будут ли у фигуры соседи-враги (фигуры противоположного цвета),
     * если ее поставить на клетку target.
     *
     * Если врагов нет, то захватывать некого,
     * и такой ход в реверси недопустим.
     *
     * @param target - проверяемая клетка.
     * @return - есть ли враги при постановке фигуры на эту клетку.
     */
    private fun hasEnemy(target: Square): Boolean {
        val board = target.board
        val tv = target.v
        val th = target.h

        // Цикл по всем 8-и направлениям.
        for (d in Dirs.ALL) {
            val v = tv + d.dv
            val h = th + d.dh

            // Клетки с координатами (v,h) нет,
            // вышли за пределы доски.
            if (!board.onBoard(v, h)) continue

            val nearSquare = board.getSquare(v, h) ?: continue
            val piece = nearSquare.getPiece() ?: continue

            if (piece.color !== color) return true // Нашли рядом врага.
        }
        return false // Не нашли стоящего рядом врага.
    }

    /**
     * Проверить произойдет ли захват вражеских фигур при постановке фигуры на
     * клетку.
     *
     * @param target - куда ставят фигуру.
     * @return - возможен ли захват вражеских фигур.
     */
    private fun isPossibleCapture(target: Square): Boolean {
        // Цикл по всем 8-и направлениям.
        Dirs.ALL.forEach { if (hasCaptured(target, it)) return true }

        return false // Захват фигур хотя бы по одному направлению не возможен.
    }

    /**
     * Возможен ли захват вражеских фигур при движении из заданной клетки
     * в заданном направлении.
     *
     * @param source    - из какой клетки двигаемся.
     * @param direction - в каком направлении двигаемся.
     * @return - возможен ли захват вражеских фигур.
     */
    private fun hasCaptured(source: Square, direction: Dirs): Boolean {
        val board = source.board
        var sv = source.v + direction.dv
        var sh = source.h + direction.dh
        var nCaptured = 0

        while (board.onBoard(sv, sh)) {
            val nextSquare = board.getSquare(sv, sh) ?: break

            // На другом конце друга нет. Окружить нельзя.
            if (nextSquare.isEmpty) return false
            val piece = nextSquare.getPiece() ?: return false

            // Это друг. Окружаем врагов я с одной стороны, он с другой.
            if (piece.color === color) return nCaptured > 0 // Стоят ли враги между нами?

            // Фигура другого цвета. Это враг.
            // Сосчитаем его и ищем следующего.
            nCaptured++

            // Смещаемся в заданном направлении.
            sv += direction.dv
            sh += direction.dh
        }
        return false
    }

    /**
     * Двигаясь из клетки **source** (куда ставится наша фигура), в заданном
     * направлении, в список captured собираются клетки, на которых стоят
     * вражеские фигуры.
     *
     * @param source    - из какой клетки движемся.
     * @param direction - в каком направлении движемся.
     * @param captured  - список в который добавляем клетки с вражескими фигурами.
     */
    private fun collectCaptured(source: Square, direction: Dirs, captured: MutableList<Square>) {
        val board = source.board
        var sv = source.v + direction.dv
        var sh = source.h + direction.dh

        while (board.onBoard(sv, sh)) {
            val nextSquare = board.getSquare(sv, sh) ?: break

            // На другом конце фигуры нет. Окружить нельзя.
            val piece = nextSquare.getPiece() ?: return

            // Эта фигура - друг.
            if (piece.color == color) return  // Окружение врагов закончили.

            // Фигура другого цвета. Это враг.
            // Запомним клетку на которой он стоит,
            // ищем следующего врага для окружения.
            captured.add(nextSquare)

            // Смещаемся в заданном направлении.
            sv += direction.dv
            sh += direction.dh
        }
    }

    override fun makeMove(vararg squares: Square): Move {
        val target = squares[0]

        // Соберем захваченные вражеские фигуры.
        val captured = collectCaptured(target)
        return if (captured.isEmpty()) PassMove() else ReversiMove(this, target, captured)
    }

    /**
     * Собрать все клетки, на которых стоят захваченные в плен (окруженные)
     * фигуры противника.
     *
     * @param target - клетка куда поставлена фигура.
     * @return - клетки с захваченными в плен фигурами противника.
     */
    private fun collectCaptured(target: Square): List<Square> {
        val captured: MutableList<Square> = ArrayList()

        // Цикл по всем 8-и направлениям.
        for (direction in Dirs.ALL)
            if (hasCaptured(target, direction))
                collectCaptured(target, direction, captured)
        return captured
    }

    override fun toString(): String = "$square"
}