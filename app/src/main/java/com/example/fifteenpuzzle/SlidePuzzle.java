package com.example.fifteenpuzzle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlidePuzzle {
    int[][] board = newBoard();
    int boardLength = board.length;
    int moveCount = 0;
    int[][] finishedPuzzle = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

    protected SlidePuzzle() {

    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public List isSolvable(int num) {
        // generate a list of random numbers to make the board with
        int numSquared = num * num;
        Random randomNum = new Random();
        List<Integer> listOfInts = IntStream.range(0, numSquared).boxed().collect(Collectors.toList());
        Collections.shuffle(listOfInts);

        return listOfInts;
    }

    public int[][] newBoard() {
        // The puzzle should always be solvable.
        // If the board length is even and the zero is in in an even row (to determine even row start counting at 1 from the bottom) and the number of inversions is odd then it is solvable.
        // If the board length is even and the zero is in in an odd row (to determine odd row start counting at 1 from the bottom) and the number of inversions is even then it is solvable.
        // If the board length is odd and the number of inversions is even then it is solvable.
        int num = 4;
        List<Integer> solvable = null;
        boolean notSolvable = true;
        while (notSolvable) {
            // while a solvable puzzle has not been generated
            List<Integer> listOfInts = isSolvable(num);
            solvable = new ArrayList(listOfInts);
            // Can the puzzle be solved?
            int zeroY = num - (listOfInts.indexOf(0) / num);
            listOfInts.remove(listOfInts.indexOf(0));

            int inversions = 0;
            for (int i = 0; i < (listOfInts.size() - 1); i++) {
                for (int n = (i + 1); n < listOfInts.size(); n++) {
                    if (listOfInts.get(i) > listOfInts.get(n)) {
                        inversions++;
                    }
                }
            }

            if (zeroY % 2 == 0 && inversions % 2 == 1) {
                System.out.println("Can be solved");
                notSolvable = false;
            } else if (zeroY % 2 == 1 && inversions % 2 == 0) {
                System.out.println("Can be solved");
                notSolvable = false;
            } else {
                System.out.println("Can't be solved.");
            }
        }

        int[][] arrayOfArrays = new int[num][num];
        int count = 0;
        for (int y = 0; y < num; y++) {
            for (int x = 0; x < num; x++) {
                arrayOfArrays[y][x] = solvable.get(count);
                count++;
            }
        }

        return arrayOfArrays;
    }

    public String printBoard() {
        String output = "";
        for (int[] y : board) {
            for (int x : y) {
                output += Integer.toString(x);
                output += (x < 10) ? ",  " : ", ";
            }
            output += "\n";
        }
        return output;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int[][] getFinishedPuzzle() {
        return finishedPuzzle;
    }

    // find the coordinates of a number and return an int array with y, x coordinates
    public int[] find(int num) {
        for (int y = 0; y < boardLength; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == num) {
                    return new int[]{y, x};
                }
            }
        }
        return null;
    }


    public void moveUp(int num) {
        int[] numCoordinates = find(num);
        int tempNum = board[numCoordinates[0] - 1][numCoordinates[1]];
        if (tempNum == 0 || num == 0) {
            board[numCoordinates[0] - 1][numCoordinates[1]] = board[numCoordinates[0]][numCoordinates[1]];
            board[numCoordinates[0]][numCoordinates[1]] = tempNum;
            setMoveCount(getMoveCount() + 1);
        }
        System.out.println(printBoard());
    }

    public void moveLeft(int num) {
        int[] numCoordinates = find(num);
        int tempNum = board[numCoordinates[0]][numCoordinates[1] - 1];
        if (tempNum == 0 || num == 0) {
            board[numCoordinates[0]][numCoordinates[1] - 1] = board[numCoordinates[0]][numCoordinates[1]];
            board[numCoordinates[0]][numCoordinates[1]] = tempNum;
            setMoveCount(getMoveCount() + 1);
        }
        System.out.println(printBoard());
    }

    public void moveDown(int num) {
        int[] numCoordinates = find(num);
        int tempNum = board[numCoordinates[0] + 1][numCoordinates[1]];
        if (tempNum == 0 || num == 0) {
            board[numCoordinates[0] + 1][numCoordinates[1]] = board[numCoordinates[0]][numCoordinates[1]];
            board[numCoordinates[0]][numCoordinates[1]] = tempNum;
            setMoveCount(getMoveCount() + 1);
        }
        System.out.println(printBoard());
    }

    public void moveRight(int num) {
        int[] numCoordinates = find(num);
        int tempNum = board[numCoordinates[0]][numCoordinates[1] + 1];
        if (tempNum == 0 || num == 0) {
            board[numCoordinates[0]][numCoordinates[1] + 1] = board[numCoordinates[0]][numCoordinates[1]];
            board[numCoordinates[0]][numCoordinates[1]] = tempNum;
            setMoveCount(getMoveCount() + 1);
        }
        System.out.println(printBoard());
    }

    public void solveFor(int num) {
        // Determine the best path for zero to take to move the number into its spot.
        int count = 0;
        while (count < 30) {
            count++;
            int[] numCoordinates = find(num);
            int[] zeroCoordinates = find(0);
            int[] numEndCoordinates = {(num - 1) / boardLength, (num % boardLength) - 1};
            boolean leftColumnNumber = false;
            boolean numIsFarthestRight = false;
            boolean lastLeftColumnNumber = false;

            if (numEndCoordinates[1] == -1) {
                // if the number belongs at the far right of the puzzle then change its end coordinates to be at the far right and one below where it should be
                numEndCoordinates[1] = boardLength - 1;
                if (!(Arrays.equals(numCoordinates, numEndCoordinates))) {
                    numIsFarthestRight = true;
                    numEndCoordinates[0] = numEndCoordinates[0] + 1;
                }
            } else if (numEndCoordinates[1] + 3 < boardLength) {
                // number belongs in the farthest left unsolved column
                leftColumnNumber = true;
                System.out.println();
                if (numEndCoordinates[0] == (boardLength - 1)) {
                    // if it is the last number adjust its end coordinates
                    numEndCoordinates[1] = numEndCoordinates[1] + 1;
                    lastLeftColumnNumber = true;
                }
            }

            if (Arrays.equals(numCoordinates, numEndCoordinates) && numIsFarthestRight) {
                // if the number is below where it needs to be and it is the last number in a row start the move sequence
                int[] zeroEndCoordinates = {numCoordinates[0] - 1, numCoordinates[1]};
                // num needs to go up
                if (Arrays.equals(zeroCoordinates, zeroEndCoordinates)) {
                    // num is ready to move up
                    moveUp(num);
                } else {
                    zeroEndCoordinates = new int[] {numCoordinates[0], numCoordinates[1] - 2};
                    System.out.println(Arrays.toString(zeroEndCoordinates));
                    int zeroY = zeroEndCoordinates[0] - zeroCoordinates[0];
                    int zeroX = zeroEndCoordinates[1] - zeroCoordinates[1];
                    while (zeroX < 0) {
                        // if zero is not in the correct position to start the sequence then move it
                        moveLeft(0);
                        zeroCoordinates = find(0);
                        zeroX = zeroEndCoordinates[1] - zeroCoordinates[1];
                    }
                    while (zeroX > 0) {
                        // if zero is not in the correct position to start the sequence then move it
                        moveRight(0);
                        zeroCoordinates = find(0);
                        zeroY = zeroEndCoordinates[0] - zeroCoordinates[0];
                    }
                    while (zeroY < 0) {
                        // if zero is not in the correct position to start the sequence then move it
                        moveUp(0);
                        zeroCoordinates = find(0);
                        zeroY = zeroEndCoordinates[0] - zeroCoordinates[0];
                    }
                    moveUp(0);
                    moveRight(0);
                    moveRight(0);
                    moveDown(0);
                    moveLeft(0);
                    moveUp(0);
                    moveLeft(0);
                    moveDown(0);
                    break;
                }
            } else if (Arrays.equals(numCoordinates, numEndCoordinates) && lastLeftColumnNumber) {
                // number belongs on the bottom left corner and is currently one space to the right
                if (zeroCoordinates[1] == numEndCoordinates[1] - 1) {
                    // if zero is where num needs to be then move num left
                    moveLeft(num);
                } else {
                    int[] zeroEndCoordinates = {numCoordinates[0] - 2, numCoordinates[1]};
                    int zeroY = zeroEndCoordinates[0] - zeroCoordinates[0];
                    int zeroX = zeroEndCoordinates[1] - zeroCoordinates[1];
                    while (zeroY < 0) {
                        // if zero is not in the correct position to start the sequence then move it
                        moveUp(0);
                        zeroCoordinates = find(0);
                        zeroY = zeroEndCoordinates[0] - zeroCoordinates[0];
                    }
                    while (zeroX < 0) {
                        // if zero is not in the correct position to start the sequence then move it
                        moveLeft(0);
                        zeroCoordinates = find(0);
                        zeroX = zeroEndCoordinates[1] - zeroCoordinates[1];
                    }
                    moveLeft(0);
                    moveDown(0);
                    moveDown(0);
                    moveRight(0);
                    moveUp(0);
                    moveLeft(0);
                    moveUp(0);
                    moveRight(0);
                    break;
                }
            } else if (Arrays.equals(numCoordinates, numEndCoordinates)) {
                System.out.println(num + " is now in its place.");
                break;
            }

            int yMoves = numEndCoordinates[0] - numCoordinates[0];
            int xMoves = numEndCoordinates[1] - numCoordinates[1];

            // UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP UP
            if (yMoves < 0) {
                System.out.println("num needs to go up");
                int[] zeroEndCoordinates = {numCoordinates[0] - 1, numCoordinates[1]};
                // num needs to go up
                if (Arrays.equals(zeroCoordinates, zeroEndCoordinates)) {
                    // num is ready to move up
                    moveUp(num);
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] < 0) {
                    // if zero needs to go up
                    if (board[zeroCoordinates[0] - 1][zeroCoordinates[1]] > num || board[zeroCoordinates[0] - 1][zeroCoordinates[1]] > num - boardLength && board[zeroCoordinates[0] - 1][zeroCoordinates[1]] != num && leftColumnNumber) {
                        // Zero can move up
                        moveUp(0);
                        if (xMoves > 0) {
                            // num needs to move right
                        }
                    } else if (zeroCoordinates[1] < boardLength - 1) {
                        // Zero will stay within the boundaries
                        if (xMoves > 0 && board[numCoordinates[0]][numCoordinates[1] + 1] == 0) {
                            // num can move right and needs to move right then move it right
                            moveRight(num);
                        } else if (board[zeroCoordinates[0]][zeroCoordinates[1] + 1] > num || board[zeroCoordinates[0]][zeroCoordinates[1] + 1] > num - boardLength && leftColumnNumber) {
                            // Zero can move right
                            moveRight(0);
                        } else if (zeroCoordinates[0] + 1 < boardLength) {
                            // if num needs to go up and zero is left of it and zero can 't go up...
                            moveDown(0);
                            moveRight(0);
                            moveRight(0);
                            moveUp(0);
                            if (board[numCoordinates[0] -1][numCoordinates[1]] > num) {
                                moveUp(0);
                                moveLeft(0);
                            } else {
                                moveRight(num);
                            }
                        } else {
                            // fix this issue. special thanks to Ben B-C.
                            // 1,  2,  3,  4,
                            // 5,  6,  7,  8,
                            // 9,  10, 14, 15,
                            // 13, 0,  11, 12
                            moveLeft(0);
                            moveUp(0);
                            moveRight(0);
                            moveRight(0);
                            moveDown(0);
                            moveLeft(0);
                            moveUp(0);
                            moveRight(0);
                            moveDown(0);
                            moveRight(0);
                            moveUp(0);
                            moveLeft(0);
                            moveDown(0);
                            moveLeft(0);
                            moveLeft(0);
                            moveUp(0);
                            moveRight(0);
                            moveRight(0);
                            moveRight(0);
                            moveDown(0);
                            moveLeft(0);
                            moveUp(0);
                            moveLeft(0);
                            moveLeft(0);
                            moveDown(0);
                            moveRight(0);
                            moveRight(0);
                            moveRight(0);
                            moveUp(0);
                            moveLeft(0);
                            moveLeft(0);
                            moveLeft(0);
                            moveDown(0);
                            moveRight(0);
                            moveRight(0);
                            moveRight(0);
                        }
                    } else if (zeroCoordinates[1] == boardLength - 1) {
                        moveLeft(0);
                        moveUp(0);
                        if (xMoves < 0) {
                            // if the number also needs to go left move it left
                            moveLeft(num);
                        } else {
                            moveUp(0);
                            moveRight(0);
                        }
                    } else {
                        // if num needs to go up and zero is left of it and zero can't go up...
                        moveDown(0);
                        moveRight(0);
                        moveRight(0);
                        moveUp(0);
                        moveUp(0);
                        moveLeft(0);
                    }
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] < 0) {
                    // zero needs to go left
                    if (board[zeroCoordinates[0]][zeroCoordinates[1] - 1] > num || board[zeroCoordinates[0]][zeroCoordinates[1] - 1] > num - boardLength && leftColumnNumber) {
                        // if zero can follow the rules and go left
                        // move zero left
                        moveLeft(0);
                    } else {
                        // move zero down
                        moveDown(0);
                        moveRight(num);
                    }
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] > 0) {
                    // zero needs to go right
                    moveRight(0);
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] > 0) {
                    // zero needs to go down
                    moveDown(0);
                }
                // LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT LEFT
            } else if (xMoves < 0) {
                System.out.println("Num needs to go left");
                int[] zeroEndCoordinates = {numCoordinates[0], numCoordinates[1] - 1};
                System.out.println(Arrays.equals(zeroCoordinates, zeroEndCoordinates));
                // if num needs to go left
                if (Arrays.equals(zeroCoordinates, zeroEndCoordinates)) {
                    // num is ready to move left
                    moveLeft(num);
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] < 0) {
                    // if zero needs to go left
                    if (board[zeroCoordinates[0]][zeroCoordinates[1] - 1] > num || board[zeroCoordinates[0]][zeroCoordinates[1] - 1] != num - boardLength && board[zeroCoordinates[0]][zeroCoordinates[1] - 1] != num && leftColumnNumber) {
                        // Zero can move left
                        moveLeft(0);
                    } else if (zeroCoordinates[0] < boardLength - 1) {
                        // if zero is not at the bottom
                        if (board[zeroCoordinates[0] + 1][zeroCoordinates[1]] > num && board[zeroCoordinates[0] + 1][zeroCoordinates[1]] != num || board[zeroCoordinates[0] + 1][zeroCoordinates[1]] != num && leftColumnNumber) {
                            // Zero can move down
                            moveDown(0);
                            moveLeft(0);
                            if (yMoves > 0) {
                                moveDown(num);
                            }
                        } else {
                            // move all the way around the number
                            moveRight(0);
                            moveDown(0);
                            moveDown(0);
                            moveLeft(0);
                            moveLeft(0);
                            moveUp(0);
                        }
                    } else if (zeroCoordinates[0] == boardLength - 1) {
                        // Zero is at the bottom therefore it can move up
                        moveUp(0);
                        moveLeft(0);
                    } else {
                        // if num needs to go left and zero is above it and zero can't go left...
                        moveRight(0);
                        moveDown(0);
                        moveDown(0);
                        moveLeft(0);
                        moveLeft(0);
                        moveUp(0);
                    }
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] < 0) {
                    // zero needs to go up
                    if (board[zeroCoordinates[0] - 1][zeroCoordinates[1]] > num || board[zeroCoordinates[0] - 1][zeroCoordinates[1]] > num - boardLength && leftColumnNumber) {
                        // Zero can move up
                        moveUp(0);
                    } else {
                        // move zero right then up
                        moveRight(0);
                        moveUp(0);
                    }
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] > 0) {
                    // zero needs to go down
                    // move zero down
                    moveDown(0);
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] > 0) {
                    // zero needs to go right
                    // move zero right
                    moveRight(0);
                }
                // RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT RIGHT
            } else if (xMoves > 0) {
                System.out.println("num needs to go right");
                // errors here with num going right
                int[] zeroEndCoordinates = {numCoordinates[0], numCoordinates[1] + 1};
                // if num needs to go right
                if (board[numCoordinates[0]][numCoordinates[1] + 1] == 0 && numIsFarthestRight) {
                    // Num can move right
                    moveRight(num);
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] > 0) {
                    // if zero needs to move right
                    if (board[zeroCoordinates[0]][zeroCoordinates[1] + 1] > num || board[zeroCoordinates[0]][zeroCoordinates[1] + 1] > num - boardLength && leftColumnNumber) {
                        // Zero can move right
                        moveRight(0);
                    } else if (zeroCoordinates[0] + 1 == boardLength) {
                        moveUp(0);
                        moveRight(0);
                        moveDown(0);
                        moveRight(0);
                        moveUp(0);
                        moveLeft(0);
                        moveLeft(0);
                        moveDown(0);
                        moveRight(0);
                        moveRight(0);
                    } else {
                        // move zero down then right first
                        moveDown(0);
                        moveRight(0);
                        moveRight(0);
                        moveUp(0);
                        moveRight(num);
                    }
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] > 0) {
                    // if zero needs to go down
                    moveDown(0);
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] < 0) {
                    // if zero needs to go left
                    if (board[zeroCoordinates[0]][zeroCoordinates[1] - 1] > num) {
                        // Zero can move left
                        moveLeft(0);
                    } else {
                        // move zero down then left
                        moveDown(0);
                        moveLeft(0);
                    }
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] < 0) {
                    // if zero needs to move up
                    moveUp(0);
                }
                // DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN DOWN
            } else if (yMoves > 0) {
                System.out.println("num needs to move down");
                // if num needs to move down
                int[] zeroEndCoordinates = {numCoordinates[0] + 1, numCoordinates[1]};
                if (board[numCoordinates[0] + 1][numCoordinates[1]] == 0) {
                    // if num can move down move it down
                    moveDown(num);
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] > 0) {
                    // if zero needs to move down
                    if (board[zeroCoordinates[0] + 1][zeroCoordinates[1]] != num) {
                        // if zero can move down move it down
                        moveDown(0);
                    } else {
                        // move zero right
                        moveRight(0);
                    }
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] < 0) {
                    // if zero needs to move left
                    if (board[zeroCoordinates[0]][zeroCoordinates[1] + 1] != num) {
                        // if zero can move left move it left
                        moveLeft(0);
                    } else {
                        // move zero down
                        moveDown(0);
                    }
                } else if (zeroEndCoordinates[1] - zeroCoordinates[1] > 0) {
                    // if zero needs to move right
                    moveRight(0);
                } else if (zeroEndCoordinates[0] - zeroCoordinates[0] < 0) {
                    // if zero needs to move up
                    moveUp(0);
                }
            }
        }
    }
}