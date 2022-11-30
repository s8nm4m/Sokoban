import figure.*
import pt.isel.canvas.*

data class Game(
    val dim: Dimension,
    val man: Man,
    val walls: List<Position>,
    val boxes: List<Position>,
    val targets: List<Position>
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
}

/**
 * Making the man and the box move by copying them
 */
fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls, boxes)
    val newBoxList = boxes.move(nextMan, walls)
    return copy(man = nextMan, boxes = newBoxList)
}

/**
 * Checking if the man can move the box and if the box can also move soo it doesn't phase through any walls
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
