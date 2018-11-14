Bug One (A):
Hovering over the rabbit character would freeze the game.

The Main.java class spawns a thread which then spawns a GUI thread as part of its execution. This GUI thread is the
paint() method which is called 50 times a second and repaints the characters, their path and the grid. When the mouse
is hovered over any character, a for loop is run and a contains() method is used to check if the mouse is positioned
on a Cell which belongs to a character. If the mouse is hovered over the rabbit, the aiMoves() method is called for the
RabbitAdapter.java class. This, in turn calls the method nextMove() which belongs to the Rabbit.java class in the bos
package. This method possesses a sleep function which puts the current execution thread to sleep for 4 seconds. The
thread which makes the method call is the GUI thread (paint() method inside Stage.java) and thus this thread ends up
sleeping for 4 seconds. This results in the program being non-functional for that period of time, and appears to "freeze".

Bug One (B):
When keys are pressed, all other characters except the rabbit move. The rabbit executes its moves four seconds later.

When keys are pressed, due to the Observer pattern the update() method within Stage.java is activated. This is
responsible for updating the locations of all the characters on the grid. A for loop is run which iterates through
a list of all the characters and spawns a thread for each. Each thread is sent off to calculate the intended path of
the character which it is "responsible" for. This called aiMoves(), which in turn calls movesBetween to return a list
of moves the character is meant to perform to reach its end goal. Once the "moves" variable is populated, the first
move of this arrayList is performed for each character. In this instance, each thread is only responsible for retrieving
the results of the calculations made by the path-finding algorithm.

So, four threads are spawned at the same time, for every key press. The diagram below shows how pressing the 'w' and the
'a' keys can affect the spawning of threads. Notice how the thread labelled "R" takes a long time to finish execution.
This is again, due to the sleep function which has been implemented within the method nextMove() for the Rabbit.java
class. Thus, when keys are pressed in quick succession, all characters except the rabbit execute their moves instantly.
The rabbit wait 4 seconds before performing its moves that it has been prompted to do. However, the time elapsed
between each of the rabbit's moves are not 4 seconds like it should be. Instead, the time between the performing of
each move related to the rabbit follow the pattern in which the keys are pressed.

For example, if a user presses 'w'... 'w''s', then the rabbit will wait 4 seconds and then do 'move'... 'move'move'.

   Main
    |     Stage
    |       |
    -       |       S       Sh      W       R
         'w'|       |       |       |       |
            |       -       -       -  (4s sleep)
         'a'|                                       S       Sh      W       R
            |                                       |       |       |       |
            |                                       -       -       -   (4s sleep)
            |
            |
            |
            |                               |
            |                               -
            |                                                               |
            |                                                               -
            |
            -

Bug Two:
Wolf executes wrong set of moves even though the path (overlay) shown is correct.

Each Cell on the grid possesses a boolean variable called ticked which is set to false when the path-finding algorithm
is used to calculate the path of a character. The only two characters that require a path are the sheep and wolf. Due
to the unique unpredictable way in which threads execute, the sheep's thread stars its path finding calculation and
sets all the Cells' ticked variable to false. As it performs its path-finding calculations, it changes the ticked
variable of the Cells, it has visited to true. In the mean time, the wolf's thread also decides to start execution and
sets all the Cells' ticked variables to false. Since both these threads are reading adn writing to the same memory
locations at the same time, a race condition occurs where the wolf receives the wrong information about the ticked
variable of each Cell it is trying to check. Thus, the completed path of the wolf ends up being incorrect. Note however,
when the mouse is hovered over the wolf, it shows the correct path. This is because, the race condition isn't occurring
as only one character is trying to access the ticked variable of each Cell, this time.
