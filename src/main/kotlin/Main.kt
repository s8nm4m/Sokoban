import figure.*
import pt.isel.canvas.*

/**
 * Drawing the map and transforming the list of maze.kt to an actual map
 * Verifying if the boxes are on targets to end game by making the man to not move anymore
 */
fun main() {
    val dim = Dimension(40, 54)
    val maze = loadMap(level1)
    val manPos = maze.positionOfType(Type.MAN)
    val boxList = maze.positionsOfType(Type.BOX)
    val wallList = maze.positionsOfType(Type.WALL)
    val targetList = maze.positionsOfType(Type.TARGET)
    val man = Man(dim, manPos, Direction.DOWN)
    val board = Canvas(dim.width * maze.width, dim.height * maze.height, WHITE)
    var game = Game(dim, man, wallList, boxList, targetList)
    onStart {
        game.draw(board)
        board.onKeyPressed { k ->
            if (game.boxes != game.targets) {
                game = game.move(k.code)
                game.draw(board)
            }
        }
    }
    onFinish {
    }
}