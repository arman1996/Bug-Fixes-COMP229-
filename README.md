# Bug-Fixes-COMP229-
There are two bugs that have risen from adding concurrency to the game. Fix them and make the game scalable.


## Assignment Three

The solution for assignment two has a couple of problems:<br/>
<br/>
When you hover over the Rabbit, the game freezes.<br/>
The Wolf is not behaving as we would like.  If we ask the wolf to calculate it's path twice it works, but not if we ask it to do so only once.<br/>
<br/>
Your job is to:<br/>
<br/>
fix these problems<br/>
describe your solution to each problem<br/>
Once that is done, you are asked to check if your solution works as the game scales.  A solution works as the game scales if the game remains responsive as the grid size grows and character locations move further apart, no character may have to wait for another to finish calculating their path before calculating theirs.  If not, you must come up with a new solution that does.  Your solution should not rely on faster path-finding - it must be purely based on safe multi-threading.  If your solution does already work for a larger game, then you must still come up with another solution - try some other multi-threading feature.<br/>
<br/>
Your solution to assignment two might never have had these problems.  You must still work from the starting point we have given you.  You might find you can get some insight into the problem by comparing our solution to yours, but you still have to build an answer from our code.<br/>
<br/>
Note:  Fixing the problem is very easy if you know where to look, thus we have allocated a significant proportion of the marks to your explanations.  Note also that you will need very clear and correct explanations for full marks.<br/>
<br/>
## Submission

You should submit a zip file containing:<br/>
<br/>
(18 marks) BUGS.md which contains your explanations of the rabbit and wolf bugs. <br/>
(15 marks) SOLUTION_ONE.md which contains a description of your first solution to the wolf bug. <br/>
(18 marks) solution_one folder which contains a full IntelliJ project that includes your implementation of your first solution. <br/>
(18 marks) SOLUTION_TWO.md which contains a description of your second solution to the wolf bug - the solution that will scale.<br/>
(18 marks) solution_two folder which contains a full IntelliJ project that includes your implementation of your second solution. <br/>
(13 marks) will be awarded if either IntelliJ project includes a solution to the rabbit bug.<br/>
