package renju.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

object RenjuImages {
    private val imageReversi = ImageIO.read(javaClass.getResource("icoRenju.png"))
    val icoReversi = ImageIcon(imageReversi.getScaledInstance(20, 20, Image.SCALE_DEFAULT))
}