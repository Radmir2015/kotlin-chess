package notebook.swing

import chess.images.ChessImages
import chess.swing.ChessBoard
import renju.swing.RenjuBoard
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main(args: Array<String>) {
    val frame = JFrame("Games Notebook")
    frame.iconImage = ImageIcon("images/GamesNotebook.png").image
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(400, 400)
    frame.location = Point(600, 200)
    frame.layout = BorderLayout()

    val tabbedPane = JTabbedPane()
    frame.add(tabbedPane, BorderLayout.CENTER)

    val chessPanel = ChessBoard()
    val renjuPanel = RenjuBoard()

    tabbedPane.addTab("Chess", ChessImages.icoChess, chessPanel)
    tabbedPane.add(renjuPanel, "Renju")

    frame.isVisible = true
}