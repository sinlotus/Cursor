package org.example.maze;

public class Maze {
    private final boolean[][] grid;
    private final int width;
    private final int height;
    private Position start;
    private Position end;

    public Maze(boolean[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
        this.start = new Position(1, 1);
        this.end = new Position(width - 2, height - 2);
    }

    public boolean isWall(int x, int y) {
        return grid[y][x];
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public void setStart(Position start) {
        this.start = start;
    }

    public void setEnd(Position end) {
        this.end = end;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
} 