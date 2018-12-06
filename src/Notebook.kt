package games.ui

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import javax.swing.ImageIcon
import javax.swing.JFrame

fun main(args: Array<String>) {
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNoteBook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(400, 400)
    frame.location = Point(600, 200)
    frame.layout = BorderLayout()

    val boardPanel = BoardPanel(nV = 8, nH = 8)
    boardPanel.size = Dimension(400, 400)
    frame.add(boardPanel, BorderLayout.CENTER)

    frame.isVisible = true
}

