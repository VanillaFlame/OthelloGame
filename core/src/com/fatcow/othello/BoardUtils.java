package com.fatcow.othello;

import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;

public final class BoardUtils {

    public static HashSet<Vector2> getPossibleRowTurns(Board board, DiskType diskType, int row, int startCol) {
        HashSet<Vector2> possibleTurns = new HashSet<Vector2>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);
        int currentCol = startCol;

        if (currentCol != 0 && data[row][currentCol - 1] == oppositeType) {
            while (--currentCol != 0 && data[row][currentCol] == oppositeType); //HACK
        }
        if (data[row][currentCol] == null) {
            possibleTurns.add(new Vector2(row, currentCol));
        }

        currentCol = startCol;
        if (currentCol != data.length - 1 && data[row][currentCol + 1] == oppositeType) {
            while (++currentCol != data.length - 1 && data[row][currentCol] == oppositeType);
        }
        if (data[row][currentCol] == null) {
            possibleTurns.add(new Vector2(row, currentCol));
        }
        return possibleTurns;
    }

    public static HashSet<Vector2> getPossibleColumnTurns(Board board, DiskType diskType, int startRow, int col) {
        HashSet<Vector2> possibleTurns = new HashSet<Vector2>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);
        int currentRow = startRow;

        if (currentRow != 0 && data[currentRow - 1][col] == oppositeType) {
            while (--currentRow != 0 && data[currentRow][col] == oppositeType); //HACK
        }
        if (data[currentRow][col] == null) {
            possibleTurns.add(new Vector2(currentRow, col));
        }
        currentRow = startRow;
        if (currentRow != data.length - 1 && data[currentRow + 1][col] == oppositeType) {
            while (++currentRow != data.length - 1 && data[currentRow][col] == oppositeType);
        }
        if (data[currentRow][col] == null) {
            possibleTurns.add(new Vector2(currentRow, col));
        }
        return possibleTurns;
    }

    public static HashSet<Vector2> getPossibleDiagTurns(Board board, DiskType diskType, int startRow, int startCol) {
        HashSet<Vector2> possibleTurns = new HashSet<Vector2>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);
        int currentCol = startCol;
        int currentRow = startRow;

        if (    currentCol != 0 &&
                currentRow != 0 &&
                data[currentRow - 1][currentCol - 1] == oppositeType) {
            while (--currentCol != 0 && --currentRow != 0 && data[currentRow][currentCol] == oppositeType); //HACK
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.add(new Vector2(currentRow, currentCol));
        }

        currentCol = startCol;
        currentRow = startRow;
        if (    currentCol != data.length - 1 &&
                currentRow != data.length - 1 &&
                data[currentRow + 1][currentCol + 1] == oppositeType) {
            while ( ++currentCol != data.length - 1 &&
                    ++currentRow != data.length - 1 &&
                    data[currentRow][currentCol] == oppositeType);
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.add(new Vector2(currentRow, currentCol));
        }
        return possibleTurns;
    }

    public static HashSet<Vector2> getPossibleAntidiagTurns(Board board, DiskType diskType, int startRow, int startCol) {
        HashSet<Vector2> possibleTurns = new HashSet<Vector2>();
        DiskType[][] data = board.getData();
        DiskType oppositeType = DiskType.getOpposite(diskType);
        int currentCol = startCol;
        int currentRow = startRow;

        if (    currentCol != 0 &&
                currentRow != data.length - 1 &&
                data[currentRow + 1][currentCol - 1] == oppositeType) {
            while ( --currentCol != 0 &&
                    ++currentRow != data.length - 1 &&
                    data[currentRow][currentCol] == oppositeType); //HACK
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.add(new Vector2(currentRow, currentCol));
        }

        currentCol = startCol;
        currentRow = startRow;
        if (    currentCol != data.length - 1 &&
                currentRow != 0 &&
                data[currentRow - 1][currentCol + 1] == oppositeType) {
            while ( ++currentCol != data.length - 1 &&
                    --currentRow != 0 &&
                    data[currentRow][currentCol] == oppositeType);
        }
        if (data[currentRow][currentCol] == null) {
            possibleTurns.add(new Vector2(currentRow, currentCol));
        }
        return possibleTurns;
    }

}
