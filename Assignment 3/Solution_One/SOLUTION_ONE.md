Rabbit Solution:
Two variables were used called firstRabbitMove and secondRabbitMove to store the rabbits future and current moves.
This was because, even if the rabbit had performed a move, when someone would hover over the rabbit, the move they would
be able to see, would be the one the rabbit had already performed. Thus secondRabbitMove is used as well to look
one move ahead. So, after execution of a move, there will always be another preloaded move for the rabbit character
to show the people playing the game.

The nextMove() method contains a sleep feature which slows down the GUI thread and thus was decoupled from it. A new
method called movesForever() was implemented within the RabbitAdapter.java class which had a Thread that contained a
while loop which ran forever. This while loop was responsible for constantly populating firstRabbitMove and
secondRabbitMove with integer values that nextMove() would return.

firstRabbitMove was then inserted as the input argument for the switch case statement. In this way, the rabbit would not
cause the GUI thread to sleep, and perform its own moves at four second intervals without disrupting the rest of the
program.

Wolf Solution:
Since four concurrent threads were being spawned as soon as a key was being pressed, each character's thread would try
and access the ticked variable of the Cells on the grid to calculate their path. This gave rise to race conditions, as
one character would try to access the ticked variable of some of the Cells before the previous character was done
calculating its path. This was fixed by using a synchronized feature. A method called getCharacterMoves() was used
to regulate the method calls made to aiMoves(). Since these two methods were now both synchronized, every character
would have to wait for aiMoves() to return before the next call to it could be made. Characters would essentially be
waiting for their paths to be calculated.