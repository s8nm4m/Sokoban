import figure.Dimension
import figure.Direction
import figure.Man
import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE
import pt.isel.canvas.onFinish
import pt.isel.canvas.onStart


const val STATUS_BAR = 1
val CELL = Dimension(40, 54)

/**
 * Getting the actual game and setting the keys for next level , undo movement and reset
 */
fun main() {
    val levels = loadLevels("Classic.txt")
    val maxDimensions = levels.getDims()
    val board = Canvas(
        CELL.width * maxDimensions.width,
        CELL.height * (maxDimensions.height + STATUS_BAR),
        WHITE
    )
    var game = newGame(1, levels, maxDimensions)
    onStart {
        game.draw(board)
        board.onKeyPressed { k ->
            if (k.text == "R") game = newGame(game.level, levels, maxDimensions)
            if (game.boxes.filter { game.targets.contains(it) }.size != game.targets.size) {
                game = when (k.text) {
                    "Backspace" -> game//.undoMove()
                    "NumPad -", "Minus" ->
                        if (game.level > 1)
                            newGame(game.level - 1, levels, maxDimensions)
                        else
                            game

                    else -> game.move(k.code)
                }
            } else {
                if (k.text == "Space")
                    game = newGame(game.level + 1, levels, maxDimensions)
            }
            game = game.isGameOver()
            game.draw(board)
        }
    }
    onFinish {
    }
}

/**
 * Getting the positions and dimensions of the level for the game
 */

fun newGame(lvl: Int, maps: List<Maze>, maxDimension: Dimension): Game {
    val maze = maps[lvl - 1]
    val manPos = maze.positionOfType(Type.MAN)
    val boxList = maze.positionsOfType(Type.BOX)
    val wallList = maze.positionsOfType(Type.WALL)
    val targetList = maze.positionsOfType(Type.TARGET)
    val dim = Dimension(
        maxDimension.width - maze.width,
        maxDimension.height - maze.height
    )
    val man = Man(
        dim,
        manPos,
        Direction.DOWN
    )
    return Game(
        dim,
        man,
        wallList,
        boxList,
        targetList,
        lvl,
        moves = emptyList()
    )
}

/**
 * Getting dimensions for the level
 */
fun List<Maze>.getDims(): Dimension {
    val height = sortedBy { it.height }[size - 1].height
    val width = sortedBy { it.width }[size - 1].width
    return Dimension(width, height)
}

/**
 * SplitBy function needed for maze.kt to load levels
 */
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