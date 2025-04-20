## Setup

Clone the repository:

```bash
git clone https://github.com/JiachengCui-01/INFO6205_Final-Project.git
```

## Run the Games

### Run TicTacToe with MCTS

```bash
\projects\src\main\mcts\tictactoe\TicTacToe.java
```

This will run a full self-play simulation using MCTS on TicTacToe.

### Run Merge (2048) Game with MCTS

```bash
\projects\src\main\mcts\merge\MergeSimulator.java
```

This will run the Merge game using MCTS strategy and output statistics to the TERMINAL.(It needs a lot of time because it will run five different iterations at a same time and other Settings can increase running time)

## Run Games with UI

You can also manually play each game using the included graphical interfaces.

### TicTacToe UI

```bash
\projects\src\main\mcts\tictactoe\TicTacToeGUI.java
```


### Merge (2048) UI

```bash
\projects\src\main\mcts\merge\MergeGUI.java
```
Players use directional keys to move blocks as a whole and combine blocks of equal value for fusion. The goal is to achieve the highest possible score by merging larger and larger tiles. Each merge can increase scores and the board complexity. The game ends when no more moves are available which means there is no empty space available and you can’t merge any tiles. 

## Output File

After running MergeSimulator, a file called `timing_merge.csv` will be generated. It includes:

- Number of iterations used
- Time spent in milliseconds
- Final game score

Use this file for visualization and analysis.

---

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
