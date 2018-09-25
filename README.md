A solitaire solver that currently solves a variant of FreeCell. In this variation, cards being placed on others must match suits, kings cannot be placed on empty cascades, and the initial setup is 10 cascades with 5 cards each with 2 cards starting in the free cells. This project was inspired by the thought "Is this game still possible?" while playing my family's version of FreeCell.

The code is organized into four packages: game, heuristics, solvers, and structures.
-game: 			contains code for playing a solitaire game, including game state, moves, and generating games
-heuristics:	contains search heuristics for solitaire game states. The heuristics generally give a minimum 				number of moves necessary to win a particular game.
-solvers:		contains search algorithms for solving games
-structures:	contains the base elements of a solitaire game, and auxiliary structures.