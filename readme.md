### April 20th Jiacheng Cui update
Add some unit tests and Made some improvement to the MCTS in merge game.

By running **`MergeSimulator.java`** , you can get results under different iterations. 

### April 19th Ziyuan Yin update

Add GUI to the 2048 game, can run proporly right now (run MergeGUI). 
Added more test.


### April 18th Jiacheng Cui update

Implemented our own game - a merge game named 2048. I also created some unit tests.

You can start the game by running **`MergeSimulator.java`** .

However, the MCTS I used didn't get any improvement, and no UI was created.




### April 15th Jiacheng Cui update

Implemented a graphical user interface (GUI) for the TicTacToe game using Java Swing.

The interface enables a human player (X) to play against an AI player (O) that uses Monte Carlo Tree Search (MCTS) for move decision-making.



### April 9th WenboJin update

Implemented MCTS in MCTS.java (and main() method)

Now the program can simulate tictactoe game between 'AI' and the user

About MCTS: Select, Expand, Simulate, Backpropagate

About MCTS_Simulate:
* First Try: random genarate next move (basically meaningless)
* Second Try (now): Check if opponent will win, then if AI will win, then random.




### April 3th WenboJin update

Implemented Position.java

Changed project structure

Using Maven // pom.xml

Now you can run tictactoe.java and get the result of one game
