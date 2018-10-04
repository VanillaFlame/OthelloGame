package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

public final class BoardUtils {

    public static Hashtable<Vector2, LinkedList<Vector2>> getPossibleRowTurns(
            Board board, DiskType diskType, int row, int startCol) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);

        int currentCol = startCol;
        LinkedList<Vector2> reverses = new LinkedList<Vector2>();
        if (currentCol != 0 && data[row][currentCol - 1] == oppositeType) {
            while (--currentCol != 0 && data[row][currentCol] == oppositeType) {
                reverses.add(new Vector2(row, currentCol));
            }
        }
        if (data[row][currentCol] == null) {
            possibleTurns.put(new Vector2(row, currentCol), reverses);
            //possibleTurns.add(new Vector2(row, currentCol));
        }

        currentCol = startCol;
        reverses = new LinkedList<Vector2>();
        if (currentCol != data.length - 1 && data[row][currentCol + 1] == oppositeType) {
            while (++currentCol != data.length - 1 && data[row][currentCol] == oppositeType) {
                reverses.add(new Vector2(row, currentCol));
            }
        }
        if (data[row][currentCol] == null) {
            possibleTurns.put(new Vector2(row, currentCol), reverses);
        }
        return possibleTurns;
    }

    public static Hashtable<Vector2, LinkedList<Vector2>> getPossibleColumnTurns(
            Board board, DiskType diskType, int startRow, int col) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);

        int currentRow = startRow;
        LinkedList<Vector2> reverses = new LinkedList<Vector2>();
        if (currentRow != 0 && data[currentRow - 1][col] == oppositeType) {
            while (--currentRow != 0 && data[currentRow][col] == oppositeType) {
                reverses.add(new Vector2(currentRow, col));
            }
        }
        if (data[currentRow][col] == null) {
            possibleTurns.put(new Vector2(currentRow, col), reverses);
        }

        currentRow = startRow;
        reverses = new LinkedList<Vector2>();
        if (currentRow != data.length - 1 && data[currentRow + 1][col] == oppositeType) {
            while (++currentRow != data.length - 1 && data[currentRow][col] == oppositeType) {
                reverses.add(new Vector2(currentRow, col));
            }
        }
        if (data[currentRow][col] == null) {
            possibleTurns.put(new Vector2(currentRow, col), reverses);
        }
        return possibleTurns;
    }

    public static Hashtable<Vector2, LinkedList<Vector2>> getPossibleDiagTurns(
            Board board, DiskType diskType, int startRow, int startCol) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);

        int currentCol = startCol;
        int currentRow = startRow;
        LinkedList<Vector2> reverses = new LinkedList<Vector2>();
        if (    currentCol != 0 &&
                currentRow != 0 &&
                data[currentRow - 1][currentCol - 1] == oppositeType) {
            --currentCol;
            --currentRow;
            while (currentCol != 0 && currentRow != 0 && data[currentRow][currentCol] == oppositeType) {
                reverses.add(new Vector2(currentRow, currentCol));
                --currentCol;
                --currentRow;
            }
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.put(new Vector2(currentRow, currentCol), reverses);
        }

        currentCol = startCol;
        currentRow = startRow;
        reverses = new LinkedList<Vector2>();
        if (    currentCol != data.length - 1 &&
                currentRow != data.length - 1 &&
                data[currentRow + 1][currentCol + 1] == oppositeType) {
            ++currentCol;
            ++currentRow;
            while ( currentCol != data.length - 1 &&
                    currentRow != data.length - 1 &&
                    data[currentRow][currentCol] == oppositeType) {
                reverses.add(new Vector2(currentRow, currentCol));
                ++currentCol;
                ++currentRow;
            }
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.put(new Vector2(currentRow, currentCol), reverses);
        }
        return possibleTurns;
    }

    public static Hashtable<Vector2, LinkedList<Vector2>> getPossibleAntidiagTurns(
            Board board, DiskType diskType, int startRow, int startCol) {
        Hashtable<Vector2, LinkedList<Vector2>> possibleTurns = new Hashtable<Vector2, LinkedList<Vector2>>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);

        int currentCol = startCol;
        int currentRow = startRow;
        LinkedList<Vector2> reverses = new LinkedList<Vector2>();
        if (    currentCol != 0 &&
                currentRow != data.length - 1 &&
                data[currentRow + 1][currentCol - 1] == oppositeType) {
            --currentCol;
            ++currentRow;
            while ( currentCol != 0 &&
                    currentRow != data.length - 1 &&
                    data[currentRow][currentCol] == oppositeType) {
                reverses.add(new Vector2(currentRow, currentCol));
                --currentCol;
                ++currentRow;
            }
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.put(new Vector2(currentRow, currentCol), reverses);
        }

        currentCol = startCol;
        currentRow = startRow;
        reverses = new LinkedList<Vector2>();
        if (    currentCol != data.length - 1 &&
                currentRow != 0 &&
                data[currentRow - 1][currentCol + 1] == oppositeType) {
            ++currentCol;
            --currentRow;
            while ( currentCol != data.length - 1 &&
                    currentRow != 0 &&
                    data[currentRow][currentCol] == oppositeType) {
                reverses.add(new Vector2(currentRow, currentCol));
                ++currentCol;
                --currentRow;
            }
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.put(new Vector2(currentRow, currentCol), reverses);
        }
        return possibleTurns;
    }

    public static Vector2 stringPosToVector(String position) {
        return new Vector2(Float.parseFloat(position.substring(1, 4)), Float.parseFloat(position.substring(5, 8)));
    }
}
