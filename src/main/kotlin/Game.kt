import figure.*
import figure.Target
import pt.isel.canvas.BLACK
import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas
import pt.isel.canvas.RED

data class Game(
    val dim: Dimension,
    val man: Man,
    val walls: List<Position>,
    val boxes: List<Position>,
    val targets: List<Position>,
    val level: Int = 1,
    val moves: List<Move>,
    val gameOver: Boolean = false
)

/**
 *Drawing everything including walls,targets,boxes and the man
 */
fun Game.draw(canvas: Canvas) {
    canvas.erase()
    walls.forEach { Wall(dim, it).draw(canvas) }
    targets.forEach { Target(dim, it).draw(canvas) }
    boxes.forEach { Box(dim, it).draw(canvas, targets) }
    man.draw(canvas, boxes)
    drawInfo(canvas)
}

const val THREE = 3
const val TWO = 2
fun Game.drawInfo(canvas: Canvas) {
    canvas.drawRect(
        0,
        canvas.height - dim.height,
        canvas.width,
        dim.height,
        CYAN
    )
    canvas.drawText(
        dim.width,
        canvas.height - dim.height / THREE,
        "Level: $level",
        BLACK
    )
    canvas.drawText(
        canvas.width * TWO / THREE,
        canvas.height - dim.height / THREE,
        "Moves: ${moves.size}",
        BLACK
    )
    if (gameOver)
        canvas.drawText(
            canvas.width / THREE,
            canvas.height - dim.height / THREE,
            "Game Over",
            RED
        )
}

fun Game.gameOver() = copy(gameOver = true)

fun Game.isGameOver(): Game {
    return if (boxes.filter { targets.contains(it) }.size == targets.size) gameOver()
    else this
}

/**
 * Making the man and the box move by returning a new game with the updated positions
 */
fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls, boxes)
    val newBoxList = boxes.move(nextMan, walls)
    val boxMove = newBoxList.filter { boxes.contains(it) }.size != boxes.size
    val m =
        if (nextMan.pos != man.pos)
            moves + Move(nextMan.dir, boxMove)
        else
            moves
    return copy(man = nextMan, boxes = newBoxList, moves = m, gameOver = false)
}

/**
 * Checking if the man can move the box and if the box can also move, so it doesn't phase through any walls
 */
fun List<Position>.move(nextMan: Man, walls: List<Position>): List<Position> {
    if (contains(nextMan.pos)) {
        val newBox = nextMan.pos.newPos(nextMan.dir)
        val newList = map { i -> if (i == nextMan.pos) newBox else i }
        newList.forEach { if (walls.contains(it)) return this }
        return newList
    }
    return this
}
