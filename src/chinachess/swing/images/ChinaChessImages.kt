package chinachess.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Класс для доступа к уникальным изображениям шахматных фигур.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object ChinaChessImages {
    private val imageChinaChess = ImageIO.read(javaClass.getResource("icoChinaChess.png"))
    val icoChinaChess = ImageIcon(imageChinaChess.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    val imageKingWhite: Image = ImageIO.read(javaClass.getResource("wKing.png"))
    val imageKingBlack: Image = ImageIO.read(javaClass.getResource("bKing.png"))

    val imageGuardWhite: Image = ImageIO.read(javaClass.getResource("wGuard.png"))
    val imageGuardBlack: Image = ImageIO.read(javaClass.getResource("bGuard.png"))

    val imageRookWhite: Image = ImageIO.read(javaClass.getResource("wRook.png"))
    val imageRookBlack: Image = ImageIO.read(javaClass.getResource("bRook.png"))

    val imagePawnWhite: Image = ImageIO.read(javaClass.getResource("wPawn.png"))
    val imagePawnBlack: Image = ImageIO.read(javaClass.getResource("bPawn.png"))

    val imageGunWhite: Image = ImageIO.read(javaClass.getResource("wGun.png"))
    val imageGunBlack: Image = ImageIO.read(javaClass.getResource("bGun.png"))

    val imageBishopWhite: Image = ImageIO.read(javaClass.getResource("bBishop.png"))
    val imageBishopBlack: Image = ImageIO.read(javaClass.getResource("wBishop.png"))

    val imageKnightWhite: Image = ImageIO.read(javaClass.getResource("wKnight.png"))
    val imageKnightBlack: Image = ImageIO.read(javaClass.getResource("bKnight.png"))
}