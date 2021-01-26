fun open(userInput: String) {
    val openItem: Item? = sumOfItems.firstOrNull { userInput == "open ${it.name}" }
    when {
        ((openItem != null && openItem.room == currentRoom && openItem.openable) && !openItem.open) && !openItem.locked -> {
            when (openItem) {
                cellarDoor -> {
                    openItem.open = true
                    println("[You open the door. It leads to the kitchen.]")
                    availableRooms.add(kitchen)
                    cellarDoor.name = "kitchen door"
                    cellarKey.name = "cellar key"
                }
                cellarBox -> {
                    openItem.open = true
                    println("[You opened the box. There is a ${cellarKey.name} inside.]")
                    cellarKey.hidden = false
                    cellarBox.description = "[It is still the same rotting box, but it is open.]"
                }
                kitchenCupboard -> {
                    println("[It is locked.]")
                }
                kitchenDrawer -> {
                    openItem.open = true
                    println("You open the drawer. Inside you see a knife.")
                    kitchenSilverware.hidden = false
                }
                kitchenDoorToHallway -> {
                    openItem.open = true
                    println("[You open the other door. It leads into the hallway.")
                    availableRooms.add(hallway)
                }
                hallwayDoor -> {
                    if (dogsafety) {
                        openItem.open = true
                        println("You open the door that leads outside.")
                        availableRooms.add(outside)
                    } else println("[The dog will surely feast on your flesh as it feasts on your fears, if you try to open the door.]")
                }
            }
        }
        (openItem != null && openItem.room == currentRoom && openItem.openable) && openItem.open -> {
            println("[It is already open.]")
        }
        else -> println("[That doesn't work.]")
    }
}
fun use(userInput: String) {
    val useItem1: Item? = sumOfItems.firstOrNull { userInput == "use ${it.name}" }
    if (useItem1 != null) {
        println("[With what?]")
        val userInputUse = readLine()
        val useItem2: Item? = sumOfItems.firstOrNull { userInput == it.name }
        when {
            useItem1 == cellarKey && useItem2 == cellarDoor && !cellarKey.hidden && cellarKey in inventory -> {
                println("[You unlock the ${cellarDoor.name}.]")
                cellarDoor.locked = false
            }
            useItem1 == cellarKey && useItem2 == cellarDoor && !cellarKey.hidden && cellarKey !in inventory -> {
                println("[You don't have the ${cellarKey.name}.]")
            }
            useItem1 == cellarStone && useItem2 == kitchenCupboard && currentRoom == kitchen -> {
                println("[You happily smash the glass window.]\n[Behind the other door a dog starts barking.]")
                kitchenCupboard.description =
                    "[It is still a big cupboard, but with smashed windows.]\n[Glass shards are lying all over the place.]"
                kitchenKey.hidden = false
            }
            useItem1 == cellarStone && useItem2 != kitchenCupboard && useItem2 != null -> {
                println("[You try to smash the ${useItem2.name} with the stone, but nothing happens.]")
            }
            useItem1 == kitchenKey && useItem2 == kitchenDoorToHallway -> {
                println("[You unlock the other door.]")
                kitchenDoorToHallway.locked = false
            }
            useItem1 == kitchenSilverware && useItem2 == hallwayDog -> {
                println("[You try to skin the dog with your knife. It doesn't like that and takes a bite out of your throat.]")
                gameOver = true
            }
            useItem1 == kitchenSilverware && useItem2 != cellarChair && useItem2 != null -> {
                println("[You try to cut the ${useItem2.name}, but nothing happens.]")
            }
            useItem1 == kitchenSilverware && useItem2 == cellarChair -> {
                println("[You cut open the cushion. Inside is a piece of paper.]")
                cellarPaper.hidden = false
            }
            else -> println("[You can't use the ${useItem1.name} with the $userInputUse, yet.]")
        }

    } else println("[You can't $userInput.]")
}

val conversationOptions = mutableListOf("Woof.", "Arf.", "Ruff.", "Grrrrrr.")
val conversationOptions2 = mutableListOf("Worf.", "A-ROOF!", "Wuff.", "Yip yip yip.")
fun talk(userInput: String) {
    var dogAggressive = 0
    val talkTo: Item? = sumOfItems.firstOrNull { userInput == "talk to ${it.name}" }
    when {
        talkTo == hallwayDog && hallwayDog.room == currentRoom -> {
            println("[Please enter your choice:]")
            conversationOptions.forEachIndexed { i, e -> println("${i + 1} - $e") }
            val userInputTalk1 = readLine()
            if (cellarPaper in inventory && userInputTalk1 == "5") {
                println(
                    "[The dog replies: 'RUFF? " +
                            "Aruff roof! " +
                            "Woof-woof arf woof... " +
                            "warroof, Mêlée Island™!]"
                )
                println("[What do you respond?]")
                conversationOptions2.forEachIndexed { i, e -> println("${i + 1} - $e") }
                val userInputTalk2 = readLine()
                if (userInputTalk2 == "3") {
                    println("[The dog seems to be friendly now.]")
                    dogsafety = true
                }
            } else {
                dogAggressive += 1
                when (dogAggressive) {
                    1 -> println("[You should not have said that. The dog gets more aggressive.]")
                    2 -> println("[How can you be so rude? You are so close of getting killed by the dog.]")
                    3 -> {
                        println("[The dog has enough of you and kills you.]")
                        gameOver = true
                    }
                }
            }
        }
        else -> println("[You want to $userInput. Are you insane?]")
    }
}

fun rooms() {
    val roomNameSet: MutableSet<String> = mutableSetOf()
    for ((name: String) in availableRooms) roomNameSet.add(name)
    val roomNameSetToString: String = roomNameSet.joinToString()
    println("[You can go to the following rooms: $roomNameSetToString.]")
}

fun goTo(userInput: String) {
    val goToRoom: Room? = availableRooms.firstOrNull { (userInput == "go to ${it.name}") }
    if (currentRoom == goToRoom) {
        println("[You are already there.]")
    }
    if (goToRoom != null && currentRoom != goToRoom) {
        currentRoom = goToRoom
        println("[You go to the ${currentRoom.name}.]")
    }
    if (goToRoom == null) {
        println("[You can't go there.]")
    }
    if (goToRoom == outside) {
        gameOver = true
        println("[Congratulations and have a nice day.]")
    }
}

fun inventory() {
    if (inventory.isEmpty()) {
        println("[Your inventory is empty.]")
    } else {
        print("[Your inventory:")
        val inventoryNameSet: MutableSet<String> = mutableSetOf()
        for ((name) in inventory) inventoryNameSet.add(name)
        val inventoryNameListToString = inventoryNameSet.joinToString()
        print(" $inventoryNameListToString")
        println(".]")
    }
}

fun help() {
    println(
        "[Use the following commands to make your way through the game:]" +
                "\n['look' - gives you a description of the room you are in]" +
                "\n['look at [item]' - gives you a description of an item in the room or in your inventory]" +
                "\n['open [item]' - does the opposite of closing an item]" +
                "\n['inventory' - shows your inventory]" +
                "\n['take [item]' - What could that do?]" +
                "\n['go to [room]' - let you change between rooms]" +
                "\n['use [item] (with) [item]']" +
                "\n['rooms' - gives you a list of the rooms you can go to.]" +
                "\n['talk to [entity]' - starts a conversation.]"
    )
}

fun look() {
    print(currentRoom.description)
    val roomObjectsNameSet: MutableSet<String> = mutableSetOf()
    val filtered: List<Item> = sumOfItems.filter { it.room == currentRoom && !it.hidden && it !in inventory }
    for ((name: String) in filtered) roomObjectsNameSet.add(name)
    val roomObjectsNameSetToString: String = roomObjectsNameSet.joinToString()
    println(" You also see the following things: $roomObjectsNameSetToString.]")
}

fun take(userInput: String) {
    val itemToTake: Item? = sumOfItems.firstOrNull { userInput == "take ${it.name}" }
    when {
        (itemToTake != null && itemToTake.room == currentRoom) && itemToTake.pickable && !itemToTake.hidden && itemToTake !in inventory -> {
            inventory.add(itemToTake)
            println("[You take the ${itemToTake.name}.]")

            when (itemToTake) {
                kitchenSilverware -> {
                    kitchenDrawer.description = "[It contains some silverware. For some reasons, a knife is missing.]"
                }
                cellarPaper -> {
                    conversationOptions.add("Wuff.")
                    conversationOptions2.add(2, "Warf.")

                }
            }
        }
        itemToTake in inventory -> println("[You already have it.]")
        else -> println("[You can't $userInput.]")
    }
}

fun lookAt(userInput: String) {
    val itemSelectInventory: Item? = inventory.firstOrNull { userInput == "look at ${it.name}" }
    val interiorItem: Item? = sumOfItems.firstOrNull { userInput == "look at ${it.name}" }

    when {
        (itemSelectInventory in inventory && itemSelectInventory != null) -> {
            println(itemSelectInventory.description)
        }
        (interiorItem != null && interiorItem.room == currentRoom) && !interiorItem.hidden && interiorItem !in inventory -> {
            println(interiorItem.description)
        }
        (interiorItem != null && interiorItem.room == currentRoom) && interiorItem.hidden -> {
            println("[How do you know that it exists?]")
        }
        else -> println("[You can't look at it.]")
    }
}