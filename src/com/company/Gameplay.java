package com.company;
import java.util.Scanner;

public class Gameplay {
    Main m = new Main();
    Gen g = new Gen();
    // Declares an XAxis variable and YAxis variable that stores what slot the user chooses
    public int iXAxis, iYAxis, iFlags, iScore;
    // Creates a 3D array that will be used to store the coordinates of a slot and how many bombs are nearby at that place.
    public int[][][] GuessedSlots = new int[0][0][0];
    // Function prints instructions on how to play the game, and sets the dimensions of the 3D array, and fills all
    // the values with -1 which will be used later
    public void GameStart(int iMapDim) {
        // Prints the instructions on how to play the game
        System.out.println("The bombs have been planted!");
        System.out.println("The numbers on the X-Axis and Y-axis of the grid tell you which slot is which.");
        System.out.println("When we ask you to choose a slot, please enter the X-Axis number first, and then the Y-axis number second! I hope you enjoy this game!");
        // Gives the GuessedSlots 3D array a set dimension
        GuessedSlots = new int[iMapDim][iMapDim][1];
        iFlags = 0;
        iScore = 0;
        // Uses a nested loop to fill all the slots of GuessedSlots 3D array with -1 values.
        for (int i = 0; i < iMapDim; i++) {
            for (int x = 0; x < iMapDim; x++) {
                GuessedSlots[i][x][0] = -1;
            }
        }
    }
    // This is the main function that will loop over and over allowing the user to fill in the map and guess slots, place flags,
    // and win/end the game.
    public int PickSlot(int[][] Map, int iMapDim, int iDifficulty, int iBombs) {
        Scanner s = new Scanner(System.in);
        // We declare 2 variables that we will use to store the user's choice, and whether the option they choose is valid
        int iUserChoice, iValid;
        // This while loop will loop over and over again until the user chooses a valid option, which will change iValid to
        //be equal to 1 and execute the option the user choose
        iValid = 0;
        while (iValid == 0) {
            // This prints a menu for the user to choose from
            System.out.println("Please choose an option: ");
            System.out.println("[1] Choose another slot");
            System.out.println("[2] Place a flag");
            System.out.println("[3] Remove a flag");
            System.out.println("[4] End the game (When you have placed all flags or you want to quit)");
            // This captures the users option choice
            iUserChoice = s.nextInt();
            // Using multiple if statements, we check what the users choice is, and if their option meets the criteria we
            // have choosen
            // This first one, is for a user to place a flag, it checks if the user option was indeed to place a flag,
            // then if the amount of flags is less than the amount of bombs, to prevent the user from just placing flags on
            // the whole field and winning the game
            if (iUserChoice == 2 && iFlags < iBombs) {
                // Stops the while loop
                iValid = 1;
                // Asks user for coordinates for where they want to place the flag
                System.out.println("Where do you want to place a flag? Enter the X-Axis number first: ");
                iXAxis = s.nextInt() - 1;
                System.out.println("Please enter the Y-Axis number now: ");
                iYAxis = s.nextInt() - 1;
                // Calls the PlaceFlag method with parameters iMapDim, and iDifficulty
                return (PlaceFlag(iMapDim, iDifficulty));
            // This next option checks if the user wants to remove a flag, then checks if there are any flags to actually be removed
            } else if (iUserChoice == 3 && iFlags != 0) {
                // Stops the while loop
                iValid = 1;
                // Asks user for coordinates of where they want to place the flag
                System.out.println("Where do you want to remove a flag? Enter the X-Axis number first: ");
                iXAxis = s.nextInt() - 1;
                System.out.println("Please enter the Y-Axis number now: ");
                iYAxis = s.nextInt() - 1;
                // Calls the RemoveFlag method with parameters iMapDim, and iDifficulty
                return(RemoveFlag(iMapDim, iDifficulty));
            // This next option is the standard guess a coordinate slot option
            } else if (iUserChoice == 1) {
                // Asks user what slot they want to guess
                System.out.println("Please choose a slot! Enter the X-Axis number first: ");
                iXAxis = s.nextInt() - 1;
                System.out.println("Please enter the Y-Axis number now: ");
                iYAxis = s.nextInt() - 1;
                // Checks if the slot is valid and within the dimensions of the 2D array aka the Map
                // Loops if it isnt valid, or calls the SlotDetection method with parameters Map, iMapDim, and iDifficulty if
                // the option is valid
                while (iValid == 0) {
                    if (iXAxis <= (iMapDim - 1) && iYAxis <= (iMapDim - 1)) {
                        return (SlotDetection(Map, iMapDim, iDifficulty));
                    } else {
                        System.out.println("That is not a valid option! Please try again.");
                        System.out.println("Please choose a slot! Enter the X-Axis number first: ");
                        iXAxis = s.nextInt() - 1;
                        System.out.println("Please enter the Y-Axis number now: ");
                        iYAxis = s.nextInt() - 1;
                    }
                }
            // This final option is for the user to end the game and check if they've won, or lost, or if they just want to quit.
            } else if (iUserChoice == 4) {
                iValid = 1;
                // Calls the EndGame method with parameters iBombs, iMapDim, and Map
                return (EndGame(iBombs, iMapDim, Map));
            } else {
                // If none of the options entered by the user is valid, it will continuously loop and ask for the user to enter a valid option
                System.out.println("That is not a valid option! Try again! You have entered an option not on the list, or you tried to remove flags, when you have none placed, or you are trying to place to many flags.");
            }
        }
        return(0);
    }
    // This method allows the user to place a flag, it has parameters iMapDim and iDifficulty.
    public int PlaceFlag(int iMapDim, int iDifficulty) {
        // it once again uses the iValid variable to loop over and over again until the user enters a valid number
        int iValid = 0;
        Scanner s = new Scanner(System.in);
        while (iValid == 0) {
            // This checks if the coordinates the user entered are actually on the plane, and if they slot they're trying to place
            // A flag in, is empty and they haven't already guessed a number there and it shows the number
            // of nearby bombs
            if (iXAxis <= (iMapDim - 1) && iYAxis <= (iMapDim - 1) && GuessedSlots[iXAxis][iYAxis][0] == -1) {
                // Sets the iValid variable to 1 confirming its a valid choice
                iValid = 1;
                // Increases the amount of flags the user has placed by 1
                iFlags++;
                // Sets the slot in the 3D array GuessedSlots to 888 to signal a flag has been placed there
                GuessedSlots[iXAxis][iYAxis][0] = 888;
                // Calls the UpdateMap method from the Gen class to update the map and display the flag placement.
                g.UpdateMap(iXAxis, iYAxis, GuessedSlots, iMapDim, iDifficulty);
                // Returns 0 which will be set to the m.iWinCondition which allows the user to continue playing the game
                return (0);
            } else {
                // If the option isn't valid, it will loop again and ask the user for his option of where to place a flag
                System.out.println("That is not a valid slot! Please try again");
                System.out.println("Where do you want to place a flag? Enter the X-Axis number first: ");
                iXAxis = s.nextInt() - 1;
                System.out.println("Please enter the Y-Axis number now: ");
                iYAxis = s.nextInt() - 1;
            }
        }
        return (0);
    }
    // This next method allows the user to remove a flag if they placed one by mistake or in a wrong spot.
    public int RemoveFlag(int iMapDim, int iDifficulty) {
        // Uses iValid variable to loop over and over again until the user has choosen a correct option that won't break the program
        int iValid = 0;
        Scanner s = new Scanner(System.in);
        while (iValid == 0) {
            // Checks if the coordinate the user choose is actually in the map, and if there is actually a flag placed there
            if (iXAxis <= (iMapDim - 1) && iYAxis <= (iMapDim - 1) && GuessedSlots[iXAxis][iYAxis][0] == 888) {
                // Confirms the option as valid
                iValid = 1;
                // Subtracts 1 from the number of flags placed
                iFlags--;
                // Resets the slot to -1, which is default for not being guessed / touched
                GuessedSlots[iXAxis][iYAxis][0] = -1;
                // Updates the map to reflect the changes
                g.UpdateMap(iXAxis, iYAxis, GuessedSlots, iMapDim, iDifficulty);
                // Returns 0 and allows user to continue playing the game
                return (0);
            } else {
                // If the option is not valid it will loop back and keep asking the user for a valid option
                System.out.println("That is not a valid slot! Please try again");
                System.out.println("Where do you want to remove a flag? Enter the X-Axis number first: ");
                iXAxis = s.nextInt() - 1;
                System.out.println("Please enter the Y-Axis number now: ");
                iYAxis = s.nextInt() - 1;
            }
        }
        return (0);
    }
    // This function ends the game and declares the user a winner, a loser, or just quit the game.
    public int EndGame(int iBombs, int iMapDim, int[][] Map) {
        // If the user has placed all the flags correctly on the bombs, the number of flags should equal the number of bombs,
        // so that is what this if statement checks for
        if (iFlags == iBombs) {
            // This nested loop cycles through all of GuessedSlots 3D array elements, and finds where the flags have been placed
            // It them checks if the X, Y, coordinates match where the bombs have been placed in the Map 2D Array, and increases the
            // Score by 1 if it has. If the number of bombs, equals the Score, aka the user has correctly guessed where all the bomsb
            // Are and flagged it, then it prints out a congratulations message, otherwise it prints out a "You have Lost" message
            for (int x = 0; x < iMapDim; x++) {
                for (int y = 0; y < iMapDim; y++) {
                    if (GuessedSlots[x][y][0] == 888 && Map[x][y] == 9) {
                        iScore++;
                    }
                }
            }
            if (iScore == iBombs) {
                System.out.println("Congratulations, you have correctly figured out where all the bombs are. You WIN!");
            } else {
                System.out.println("The flags were not in the correct location! You have lost. We hope you enjoyed the game.");
            }
        // If the user has not used all the guesses aka Flags, it prints out a default you have ended the game message
        } else {
            System.out.println("You have ended the game, we hoped you enjoyed!");
        }
        return (1);
    }
    // This next method detects if the user actidentally steped on a bomb.
    public int SlotDetection(int[][] Map, int iMapDim, int iDifficulty) {
        // If the coordinates the user entered match those of where a bomb is placed in the 2D Array Map, it then returns 1, which
        // ends the game and prints out a message saying that the user stepped on a bomb, and that the game has ended
        if (Map[iYAxis][iXAxis] == 9) {
            System.out.println("Oops! You have stepped on bomb! Game Over!");
            return(1);
        } else {
            // If the user did not step on a bomb, it then calls the method CalcNearbyBombs with parameters Map, iMapDim, and iDifficulty
            CalcNearbyBombs(Map, iMapDim, iDifficulty);
            return(0);
        }
    }
    // This final method, calculates the number of bombs nearby.
    public void CalcNearbyBombs(int[][] Map, int iMapDim, int iDifficulty) {
        // It uses iBombsNearby to store the amount of bombs nearby to later pass onto another method to display the number to the user
        int iBombsNearby;
        iBombsNearby = 0;
        // The next 8 nested if statements all do the same thing, they first check if the number entered is valid, and if
        // The slots around it are valid, aka on the map and not out of bounds of the 2D array
        // They then proceed to see if there is a bomb in that slot, and if not, it continues checking the next slots around
        // the slot the user guessed, otherwise if there is a bomb, it increases the iBombsNearby counter by 1
        if ((iYAxis + 1) >= 0 && (iXAxis + 1) >= 0 && (iYAxis + 1) < iMapDim && (iXAxis + 1) < iMapDim) {
            if (Map[iYAxis + 1][iXAxis + 1] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis + 1) >= 0 && (iXAxis) >= 0 && (iYAxis + 1) < iMapDim && (iXAxis) < iMapDim) {
            if (Map[iYAxis + 1][iXAxis] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis) >= 0 && (iXAxis + 1) >= 0 && (iYAxis) < iMapDim && (iXAxis + 1) < iMapDim) {
            if (Map[iYAxis][iXAxis + 1] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis) >= 0 && (iXAxis - 1) >= 0 && (iYAxis) < iMapDim && (iXAxis - 1) < iMapDim) {
            if (Map[iYAxis][iXAxis - 1] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis - 1) >= 0 && (iXAxis) >= 0 && (iYAxis - 1) < iMapDim && (iXAxis) < iMapDim) {
            if (Map[iYAxis - 1][iXAxis] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis - 1) >= 0 && (iXAxis + 1) >= 0 && (iYAxis - 1) < iMapDim && (iXAxis + 1) < iMapDim) {
            if (Map[iYAxis - 1][iXAxis + 1] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis + 1) >= 0 && (iXAxis - 1) >= 0 && (iYAxis + 1) < iMapDim && (iXAxis - 1) < iMapDim) {
            if (Map[iYAxis + 1][iXAxis - 1] == 9) {
                iBombsNearby++;
            }
        }
        if ((iYAxis - 1) >= 0 && (iXAxis - 1) >= 0 && (iYAxis - 1) < iMapDim && (iXAxis - 1) < iMapDim) {
            if (Map[iYAxis - 1][iXAxis - 1] == 9) {
                iBombsNearby++;
            }
        }
        // This prints out how many bombs are nearby
        System.out.println("There are " + iBombsNearby + " bombs nearby.");
        // This stores the coordinate of where the user guessed, and how many bombs are nearby in a 3D array GuessedSlots
        GuessedSlots[iXAxis][iYAxis][0] = iBombsNearby;
        // This calls the UpdateMap method of class Gen, in order to properly display the results.
        g.UpdateMap(iXAxis, iYAxis, GuessedSlots, iMapDim, iDifficulty);
    }
}
