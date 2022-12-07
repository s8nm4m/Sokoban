import figure.*
import figure.Target
import pt.isel.canvas.BLACK
import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas

data class Game(
    val dim: Dimension,
    val man: Man,
    val walls: List<Position>,
    val boxes: List<Position>,
    val targets: List<Position>,
    val level: Int = 1,
    val moves: List<Direction>
)

const val INFO_H = 10

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

fun Game.drawInfo(canvas: Canvas) {
    canvas.drawRect(0, dim.height * 10 - dim.height, dim.width * 10, INFO_H, CYAN)
    canvas.drawText(dim.width, dim.height * 10 - (dim.height / 2), "Level: $level", BLACK)
    canvas.drawText(dim.width * 10 - (dim.width * 2), dim.height * 10 - (dim.height / 2), "Moves: ${moves.size}", BLACK)
}

/**
 * Making the man and the box move by returning a new game with the updated positions
 */
fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls, boxes)
    val newBoxList = boxes.move(nextMan, walls)
    return copy(man = nextMan, boxes = newBoxList, moves = moves + nextMan.dir)
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
