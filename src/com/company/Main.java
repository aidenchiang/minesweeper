/*
This is a fully functional MineSweeper game created on March 24, 2021.
It allows you to choose difficulties, does not allow for invalid inputs that would cause the program to fail, and
has all the normal features of MineSweeper such as placing a flag, guessing a slot and the program telling you the # of nearby bombs
and checking to see if you placed all the flags correctly and telling you if you won or lost.
 */
package com.company;
import java.util.Scanner;

public class Main {
	// Declaring variables and creating the array, and
	public int iDifficulty, iMapDim, iBombs, iWinCondition;
	public int[][] Map = new int[0][0];
	Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
		Main m = new Main();
		Gen g = new Gen();
		Gameplay game = new Gameplay();
		m.iWinCondition = 0;
		// Calls the Difficulty method in order to determine the type of map to generate.
    	m.Difficulty();
    	// Creates a empty map stored in the 2D "Map" array by passing the iMapDim
		// variable which determines the size of the 2D Array, into the EmptyMap method in Gen class
    	m.Map = g.EmptyMap(m.iMapDim);
    	// Calls the GameStar method in the Gameplay class passing in a variable iMapDim
    	game.GameStart(m.iMapDim);
    	// Calls the CreateBombs method in the Gen class, passing the variables iBombs, iMapDim, and the array Map into the method
		// Stores the returned values in the Map 2D array
		m.Map = g.CreateBombs(m.iBombs, m.iMapDim, m.Map);
		//
		// This is for developer use only to verify the "Hidden Map" which controls the backend mechanics such as where the bombs are located
		// g.PrintMap(m.iMapDim, m.Map);
		//
		// Calls the PrintUserMap method of the Gen class, passing in variables iMapDim, the 2D Array Map, and the variable iDifficulty
		g.PrintUserMap(m.iMapDim, m.Map, m.iDifficulty);
		// While loop keeps asking the user to guess slots until the iWinCondition is not equal to 0.
		while (m.iWinCondition == 0) {
			// Calls the PickSlots variable passing in the 2D array Map, and the variables iMapDim, and iDifficulty, setting the returned
			// value to iWinCondition
			m.iWinCondition = game.PickSlot(m.Map, m.iMapDim, m.iDifficulty, m.iBombs);
		}
    }
	// This method asks the user to choose a difficulty from a menu, and determines the size of the map, the number of bombs, and repeats until the user gives a valid answer.
    public void Difficulty() {
    	// Used to force the user to choose a correct difficulty, using the while loop, it will repeatedly ask the user for a correct option.
    	int i = 0;
    	while (i == 0) {
    		// Prints the menu for the user to see.
    		System.out.println("Welcome to MineSweeper!");
			System.out.println("Please choose a difficulty from the menu: ");
			System.out.println("[1] Easy");
			System.out.println("[2] Medium");
			System.out.println("[3] Hard");
			// Stores the users input in the iDifficulty variable
			iDifficulty = s.nextInt();
			// Determines the map size, and the number of bombs, and stops the while loop when the user chooses a correct option.
			if (iDifficulty == 1) {
				iMapDim = 5;
				iBombs = 3;
				i = 1;
			} else if (iDifficulty == 2) {
				iMapDim = 8;
				iBombs = 10;
				i = 1;
			} else if (iDifficulty == 3) {
				iMapDim = 12;
				iBombs = 20;
				i = 1;
			} else {
				// Tells the user they must choose a valid option and loops back and prints the menu again.
				System.out.println("That is not a valid option, please try again.");
			}
		}
	}
}