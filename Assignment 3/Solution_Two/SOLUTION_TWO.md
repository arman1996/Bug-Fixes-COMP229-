Scalable Rabbit Solution:
The features mentioned in SOLUTION_ONE.md still hold and the following has been implemented on top of what already
exists.
Within the update() method a check is done through the characters inside the allCharacters arrayList for characters
possessing the description, "rabbit". Their moves aren't performed. So, there can be 100 rabbits, that can be running
on their own threads performing moves without affecting the motion of the rest of the characters. However, the rabbits
are still characters and thus are still with the allCharacters list. Their movement functionality has been decoupled
from the rest of the program due to them causing the game to lag.
The movement of the rabbit can also be turned into its own behaviour called RabbitMotion, which each rabbit can
implement, thus further making the game scalable.

Scalable Wolf Solution:
The synchronized feature which locked "getCharacterMoves()" and "aiMoves()" together no longer hold in this solution.
Synchronized causes method calls to wait, and if there were 100 wolves or sheep, the execution of their moves wouldn't
be instant as they would all have to wait in a queue for the character in front of them to finish calculating its'
path.
Thus, a way around this is to gravitate away from the use of the ticked variable. Instead, a HashSet (which i called ryan)
was implemented which contained all the Cells that had been visited by any given character. Since threads are spawned
for each character, that are responsible finding each of their paths, the HashSet had to be made local to the calling
method, movesBetween(). Cells that were visited would be added to the HashSet, and since this data structure is uses
Hash's of each Cell, duplicates cannot be created. This is also very memory efficient, since only Hash's of Cells are
stored rather than a whole Cell object, of an entire memory address to a Cell.

When a key is pressed, since each character has its own thread, in which it makes its own  method call of movesBetween(),
they all use their own HashSet, rather than sharing one between all the characters. This removes any chances of there
being a race condition.