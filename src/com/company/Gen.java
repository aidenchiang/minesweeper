package com.company;
import java.util.Random;

public class Gen {
    Main m = new Main();
    Random rand = new Random();

    // This function creates an empty "Map" which is a 2D array filled with 0's. It takes the iMapDim variable to determine
    // the Map size, and uses a nested loop to fill the array with 0's. It then returns the 2D array.
    public int[][] EmptyMap(int iMapDim) {
        // Creates a map with the dimensions from Main.
        int[][] Map = new int[iMapDim][iMapDim];
        // Nested loop fills the whole array with 0's
        for (int x = 0; x < iMapDim; x++) {
            for (int y = 0; y < iMapDim; y++) {
                Map[x][y] = 0;
            }
        }
        // Returns the 2D Array "Map".
        return Map;
    }

    // Function generates random numbers using the rand class and nextInt method in order to choose random coordinates
    // where the bombs will be placed. Returns the Map
    public int[][] CreateBombs(int iBombs, int iMapDim, int[][] Map) {
        // Creates 2 variables that will store the X and Y coordinates for the bombs temporarily
        int iXCor, iYCor;
        // using a loop, it will create iBombs amount of bombs on the map by generating a random X coordinate within the
        // bounds of the array, and a random Y coordinate. It then sets the value on the map to 9.
        for (int i = 0; i < iBombs; i++) {
            iXCor = rand.nextInt(iMapDim);
            iYCor = rand.nextInt(iMapDim);
            Map[iXCor][iYCor] = 9;
        }
        // Returns the finished map with the bombs
        return Map;
    }

    // Developer method that can be called to display the backend Map that shows where the bombs are
    // Is passed in the variables iMapDim and the 2D array Map
    // **NOT PART OF NORMAL PROGRAM FUNCTION**
    public void PrintMap(int iMapDim, int[][] Map) {
        // Using a nested loop it loops through all values of the 2D Array and prints them
        for (int x = 0; x < iMapDim; x++) {
            for (int y = 0; y < iMapDim; y++) {
                System.out.print(Map[x][y]);
            }
            // Prints a new line when one "row" of the 2D array is done being printed
            System.out.println();
        }
    }

    // This function prints out the map that the user will see for the first time. Has variables iMapDim, 2D array Map,
    // and another variable iDifficulty passed in.
    public void PrintUserMap(int iMapDim, int[][] Map, int iDifficulty) {
        // Prints the first row which is a "+" in the corner, and the proper X coordinate labels depending on the size of the
        // "Map" 2D array
        System.out.print("+  ");
        for (int i = 0; i < iMapDim; i++) {
            if ((i + 1) < 10) {
                System.out.print(" " + (i + 1) + " ");
            } else {
                System.out.print(" " + (i + 1));
            }
        }
        // Prints a row of dashes to seperate the rows, once again based on the size of the map determined by iDifficulty
        // This repeats the correct number of times based on iMapDim using a for loop in order to print the whole map
        for (int x = 0; x < iMapDim; x++) {
            System.out.println();
            if (iDifficulty == 1) {
                System.out.print("   ----------------");
            } else if (iDifficulty == 2) {
                System.out.print("   -------------------------");
            } else if (iDifficulty == 3) {
                System.out.print("   -------------------------------------");
            }
            // Prints out the coloums and Y Axis labels correctly based on the size of the map, with spaces in between
            // each coloum seperator, in order to simulate a "blank" element in the 2d array "Map" which represents
            // an unguessed slot by the user.
            System.out.println();
            if ((x + 1) < 10) {
                System.out.print((x+1) + " ");
            } else {
                System.out.print((x+1));
            }
            for (int y = 0; y < (iMapDim + 1); y++) {
                System.out.print(" | ");
            }
        }
        // Prints the final row of dashes based on the iDifficulty variable that determines the map size
        System.out.println();
        if (iDifficulty == 1) {
            System.out.print("   ----------------");
        } else if (iDifficulty == 2) {
            System.out.print("   -------------------------");
        } else if (iDifficulty == 3) {
            System.out.print("   -------------------------------------");
        }
        System.out.println();
    }
    // UpdateMap is similar to PrintUserMap, except it will print the nearby bombs in the slots where the user has already guessed
    // This method passes in iXAxis, iYAxis, the 3D Vector GuessedSlots, iMapDim, and iDifficulty
    public void UpdateMap(int iXAxis, int iYAxis, int[][][] GuessedSlots, int iMapDim, int iDifficulty) {
       // This part prints out the first line and the X Axis label coordinates along with a dashed line below it
       // to properly seperate them from the map the user is interacting with
        System.out.print("+  ");
        for (int i = 0; i < iMapDim; i++) {
            if ((i + 1) < 10) {
                System.out.print(" " + (i + 1) + " ");
            } else {
                System.out.print(" " + (i + 1));
            }
        }
        for (int x = 0; x < iMapDim; x++) {
            System.out.println();
            if (iDifficulty == 1) {
                System.out.print("   ----------------");
            } else if (iDifficulty == 2) {
                System.out.print("   -------------------------");
            } else if (iDifficulty == 3) {
                System.out.print("   -------------------------------------");
            }
            System.out.println();
            // This part prints out the colum seperators, along with the YAxis labels. It then checks in each slot if the user has
            // Placed a flag, or if the user has already guessed that slot, and then prints the "F" for flag, or how many
            // nearby bombs there are using GuessedSlots the 3D vector, and a for loop.
            if ((x + 1) < 10) {
                System.out.print((x+1) + " ");
            } else {
                System.out.print((x+1));
            }
            for (int y = 0; y < iMapDim; y++) {
                System.out.print(" |");
                if (GuessedSlots[y][x][0] == 888) {
                    System.out.print("F");
                } else if (GuessedSlots[y][x][0] >= 0) {
                    System.out.print(GuessedSlots[y][x][0]);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print(" |");
        }
        // This prints the final row of dashes to close off the map the user is interacting with.
        System.out.println();
        if (iDifficulty == 1) {
            System.out.print("   ----------------");
        } else if (iDifficulty == 2) {
            System.out.print("   -------------------------");
        } else if (iDifficulty == 3) {
            System.out.print("   -------------------------------------");
        }
        System.out.println();

    }

}

