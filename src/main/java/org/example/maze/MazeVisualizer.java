package org.example.maze;

import java.util.List;

public class MazeVisualizer {
    private static final char WALL = '█';
    private static final char PATH = '·';
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char EMPTY = ' ';

    public void visualize(Maze maze, List<Maze.Position> path) {
        char[][] display = new char[maze.getHeight()][maze.getWidth()];

        // 初始化显示数组
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                display[y][x] = maze.isWall(x, y) ? WALL : EMPTY;
            }
        }

        // 标记路径
        for (Maze.Position pos : path) {
            if (!pos.equals(maze.getStart()) && !pos.equals(maze.getEnd())) {
                display[pos.getY()][pos.getX()] = PATH;
            }
        }

        // 标记起点和终点
        display[maze.getStart().getY()][maze.getStart().getX()] = START;
        display[maze.getEnd().getY()][maze.getEnd().getX()] = END;

        // 打印迷宫
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                System.out.print(display[y][x]);
            }
            System.out.println();
        }
    }
} 