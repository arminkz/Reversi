# Reversi

<p>Computer Reversi Game and also an AI Player for AI Competition.</p>
<p>this project consists of a GUI for Playing Othello and also an AI Player.</p>

<br/>
<p align="center">
  <img width="500" height="522" src="/../master/doc/vs_greedy.gif?raw=true"/>
  
  <p align="center">AI Algorithm (Black) vs. Greedy Algorithm (White)</p>
</p>

Play Modes
----------
* Human VS Human
* Human VS AI
* AI VS AI


<br/>
<br/>
<br/>

# AI Algorithm


Minimax Search
--------------
The search algorithm is a minimax search with alpha-beta pruning.


Evaluation Function
-------------------
* Realtime Evaluation : Evaluation function changes as you move from early-game to end-game with each move , using linear interpolation between static values.

* Machine-Learning-Tuned Evaluation : i've used Hill-Climbing algorithm to train weights on each heuristic function based on game progress.


Heuristic Functions
-------------------
One of the most critical components of the search algorithm is the heuristic function, which evaluates the strength and overall desireability of a given board position. It is implemented as a linear combination of the following statistics, with the weights adapting as the game progresses

* Corner Grab (Measures if the current player can take a corner with its next move, Weighted highly at all times.)

* Stability (Measures the number of discs that cannot be flipped for the rest of the game. Weighted highly at all times.)

* Mobility (Measures the number of moves the player is currently able to make. Has significant weight in the opening game, but diminishes to zero weight towards the endgame.)

* Placment (piece placement score of the current player minus the piece placement score of the opponent.)

* Frontier Discs (number of spaces adjacent to opponent pieces minus the the number of spaces adjacent to the current player's pieces.)

* Disc difference (Measures the difference in the number of discs on the board. Has zero weight in the opening, but increases to a moderate weight in the midgame, and to a significant weight in the endgame.)

* Parity (Measures who is expected to make the last move of the game. Has zero weight in the opening, but increases to a very large weight in the midgame and endgame.) (currently unused feature)


Killer Move Detection
---------------------
The AI Player takes some moves without searching within the Minimax tree:

* Corner Grab Move (Move that leads to capturing a corner)

* Blocking Move (Move that blocks the oponent on the next move)


Opening Book
------------
In the opening, the AI may take its moves from a database of commonly
played openings (source [here](http://www.samsoft.org.uk/reversi/openings.htm))


Transposition Table
-------------------
the AI keeps record of some previously searched and Evaluated Boards with use of Special Zorbist Hash Function.(see [here](https://en.wikipedia.org/wiki/Zobrist_hashing))
(This Feature is still under Development)
