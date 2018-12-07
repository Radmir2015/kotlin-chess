package games.ui

import java.awt.BorderLayout
import java.awt.Graphics
import javax.swing.JPanel

abstract class AbstractBoardPanel(var nV: Int, var nH: Int) : JPanel(BorderLayout()) {
    override fun paint(gc: Graphics) {
        drawBack(gc)

        for (v in 0 until nV)
            for (h in 0 until nH)
                drawSquare(gc, v, h)
    }

    protected abstract fun drawBack(gc: Graphics)

    protected abstract fun drawSquare(gc: Graphics, v: Int, h: Int)
}