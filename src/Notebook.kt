package games.ui

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main(args: Array<String>) {
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNoteBook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(400, 400)
    frame.location = Point(600, 200)
    frame.layout = BorderLayout()

    val tabbedPane = JTabbedPane()
    frame.add(tabbedPane, BorderLayout.CENTER)

    val chessPanel = EuropeBoard(nV = 8, nH = 8)
    val renjuPanel = AsiaBoard(nV = 10, nH = 10)
//    chessPanel.size = Dimension(400, 400)

    tabbedPane.add(chessPanel, "Chess")
    tabbedPane.add(renjuPanel, "Renju")

    frame.isVisible = true
}

