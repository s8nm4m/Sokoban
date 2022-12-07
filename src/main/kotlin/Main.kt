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
        val game2 = game // to facilitate reloading current level
        game.draw(board)
        board.onKeyPressed { k ->
            if (game.boxes.filter { game.targets.contains(it) }.size != game.targets.size) {
                game = game.move(k.code)
                game.draw(board)
                game = when (k.text) {
                    "R" -> game2
                    "Backspace" -> game//.undoMove()
                    "NumPad -", "Minus" -> game//.previousLevel()
                    else -> game
                }
                game.draw(board)
            } else {
                // if (k.text == "Space") //.nextLevel()
                game.draw(board)
            }
        }
    }
    onFinish {
    }
}
/*
KeyEvent(char=r, code=82, text=R)
KeyEvent(char= , code=32, text=Space)
KeyEvent(char, code=8, text=Backspace)
KeyEvent(char=-, code=109, text=NumPad -)
KeyEvent(char=-, code=45, text=Minus)
KeyEvent(char=￿, code=37, text=Left)
KeyEvent(char=￿, code=38, text=Up)
KeyEvent(char=￿, code=39, text=Right)
KeyEvent(char=￿, code=40, text=Down)
KeyEvent(char=, code=27, text=Escape)
 */

fun List<Maze>.getDims(): List<Int> {
    val height = sortedBy { it.height }[size - 1].height
    val width = sortedBy { it.width }[size - 1].width
    return listOf(height, width)
}
/*
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
}*/