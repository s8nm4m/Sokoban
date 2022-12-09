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
    val maxDimensions = levels.getDims()
    val board = Canvas(dim.width * maxDimensions.width, dim.height * (maxDimensions.height + OFFSET), WHITE)
    var game = newGame(1, levels, dim)
    onStart {
        game.draw(board)
        board.onKeyPressed { k ->
            if (k.text == "R") game = newGame(game.level, levels, dim)
            if (game.boxes.filter { game.targets.contains(it) }.size != game.targets.size) {
                game = when (k.text) {
                    "Backspace" -> game//.undoMove()
                    "NumPad -", "Minus" -> if (game.level > 1) newGame(game.level - 1, levels, dim) else game
                    else -> game.move(k.code)
                }
            } else {
                if (k.text == "Space")
                    game = newGame(game.level + 1, levels, dim)
            }
            game = game.isGameOver()
            game.draw(board)
        }
    }
    onFinish {
    }
}

fun newGame(lvl: Int, maps: List<Maze>, dim: Dimension): Game {
    val maze = maps[lvl - 1]
    val manPos = maze.positionOfType(Type.MAN)
    val boxList = maze.positionsOfType(Type.BOX)
    val wallList = maze.positionsOfType(Type.WALL)
    val targetList = maze.positionsOfType(Type.TARGET)
    val man = Man(dim, manPos, Direction.DOWN)
    return Game(dim, man, wallList, boxList, targetList, lvl, moves = emptyList())
}

fun List<Maze>.getDims(): Dimension {
    val height = sortedBy { it.height }[size - 1].height
    val width = sortedBy { it.width }[size - 1].width
    return Dimension(width, height)
}

fun List<String>.splitBy(lambda: (String) -> Boolean): List<List<String>> {
    var list = emptyList<List<String>>()
    var subList = emptyList<String>()
    forEach {
        if (!lambda(it))
            subList = subList + it
        else {
            list = list + listOf(subList)
            subList = emptyList()
        }
    }
    return list + listOf(subList)
}