package game.swing.images

import java.awt.Image
import javax.imageio.ImageIO

object GameImages {
    val woodDark: Image = ImageIO.read(javaClass.getResource("wood_dark.png"))
    val woodLight: Image = ImageIO.read(javaClass.getResource("wood_light.png"))
    val woodMedium: Image = ImageIO.read(javaClass.getResource("wood_medium.png"))

    val imageStoneWhite: Image = ImageIO.read(javaClass.getResource("StoneWhite.png"))
    val imageStoneBlack: Image = ImageIO.read(javaClass.getResource("StoneBlack.png"))
}