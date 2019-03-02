package game.swing

import game.core.Square
import java.awt.Color
import java.awt.Graphics

/**
 * Класс - утилита для отрисовки маркеров на доске и фигурах.
 */
object Markers {
    /**
     * Пометить клетку цветным маркером.
     *
     * @param g графический контекст.
     * @param square помечаемая клетка.
     * @param markColor цвет маркера.
     */
    fun markSquare(g: Graphics, square: Square, sw: Int, sh: Int, markColor: Color) {
        val d = 10

        val v = square.v
        val h = square.h

        val x = v * sw + (sw - d) / 2
        val y = h * sh + (sh - d) / 2

        // gc.setLineWidth(3) TODO Line Width
        g.color = markColor
        g.fillOval(x, y, d, d)
    }

    /**
     * Соединить линией центры двух клеток.
     *
     * @param g
     * - графический контекст.
     * @param source
     * - откуда линия.
     * @param target
     * - куда линия.
     * @param color
     * - цвет линии.
     */
    fun markLine(g: Graphics, source: Square, target: Square, sw: Int, sh: Int, color: Color) {
        val v1 = sw * source.v + sw / 2
        val h1 = sh * source.h + sh / 2

        val v2 = sw * target.v + sw / 2
        val h2 = sh * target.h + sh / 2

        g.color = color
        g.drawLine(v1, h1, v2, h2)
    }

    /**
     * Нарисовать на клетке перекрестье.
     *
     * @param g
     * - графический контекст.
     * @param where
     * - откуда линия.
     * @param color
     * - цвет линии.
     */
    fun markCross(g: Graphics, where: Square, sw: Int, sh: Int, color: Color) {
        val v1 = sw * where.v
        val h1 = sh * where.h

        val v2 = sw * where.v + sw
        val h2 = sh * where.h + sh

        // gc.setLineWidth(3) TODO Line Width
        g.color = color
        g.drawLine(v1, h1, v2, h2)
        g.drawLine(v2, h1, v1, h2)
    }

    /**
     * Пометить две клетки рамками заданного цвета.
     *
     * @param g
     * - графический конеткст для рисования.
     * @param source
     * - клетка откуда идет фигура.
     * @param target
     * - клетка куда идет фигура.
     * @param color
     * - цвет рамки.
     */
    fun markSquares(g: Graphics, source: Square, target: Square, sw: Int, sh: Int, color: Color) {
        val v1 = sw * source.v + sw / 2
        val h1 = sh * source.h + sh / 2

        val v2 = sw * target.v + sw / 2
        val h2 = sh * target.h + sh / 2

        g.color = color
        g.drawRect(v1 * sw, h1 * sh, sw, sh)
        g.drawRect(v2 * sw, h2 * sh, sw, sh)
    }
}