val inventory: MutableSet<Item> = mutableSetOf()
val availableRooms: MutableSet<Room> = mutableSetOf(cellar)
var currentRoom: Room = cellar

var dogsafety = false
var gameOver = false

fun main() {
    println("[You are in a cellar. What do you do? Type 'help', if you need help.]\n[Note that the names of the items change depending on your knowledge about them.]")
    while (!gameOver) {
        val userInput = readLine() ?: return
        when {
            userInput.contains("go to") -> goTo(userInput)
            userInput.contains("inventory") -> inventory()
            userInput.contains("help") -> help()
            userInput == "rooms" -> rooms()
            userInput == "look" -> look()
            userInput.contains("take") -> take(userInput)
            userInput.contains("look at") -> lookAt(userInput)
            userInput.contains("open") -> open(userInput)
            userInput.contains("use") -> use(userInput)
            userInput.contains("talk") -> talk(userInput)
            else -> println("[Don't know how to $userInput.]")
        }
    }
}