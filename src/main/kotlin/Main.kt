import figure.Dimension
import figure.Direction
import figure.Man
import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart

/**
 * Drawing the map and transforming the list of maze.kt to an actual map
 * Verifying if the boxes are on targets to end game by making the man unable to move anymore
 */

const val OFFSET = 1
fun main() {
    val dim = Dimension(40, 54)
    val levels = loadLevels("Classic.txt")
    val maze = levels.first()
    val dims = levels.getDims()
    val manPos = maze.positionOfType(Type.MAN)
    val boxList = maze.positionsOfType(Type.BOX)
    val wallList = maze.positionsOfType(Type.WALL)
    val targetList = maze.positionsOfType(Type.TARGET)
    val man = Man(dim, manPos, Direction.DOWN)
    val board = Canvas(dim.width * dims[1], dim.height * (dims[0] + OFFSET), WHITE)
    var game = Game(dim, man, wallList, boxList, targetList, moves = emptyList())
    onStart {
        game.draw(board)
        board.onKeyPressed { k ->
            if (game.boxes.filter { game.targets.contains(it) }.size != game.targets.size) {
                game = game.move(k.code)
                game.draw(board)
            }
        }
    }
    onFinish {
    }
}

fun List<Maze>.getDims(): List<Int> {
    val height = sortedBy { it.height }[size - 1].height
    val width = sortedBy { it.width }[size - 1].width
    return listOf(height, width)
}


// to load the last level we had to enter a new line with a few blank spaces in the txt file
fun List<String>.splitBy(s: (String) -> Boolean): List<List<String>> {
    var list = emptyList<List<String>>()
    var subList = emptyList<String>()
    for (line in this) {
        if (!s(line))
            subList = subList + line
        else {
            list = list + listOf(subList)
            subList = emptyList()
        }
    }
    return list
}

//fun List<String>.splitBy(s: (String) -> Boolean) = listOf(takeWhile { ! s(it) })