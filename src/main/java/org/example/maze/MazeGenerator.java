package org.example.maze;

import java.util.*;

public class MazeGenerator {
    private final int width;
    private final int height;
    private final boolean[][] maze;
    private final Random random;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new boolean[height][width];
        this.random = new Random();
    }

    public boolean[][] generate() {
        // 初始化迷宫，所有墙都存在
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = true;
            }
        }

        // 从随机起点开始生成
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        generateMaze(startX, startY);

        return maze;
    }

    private void generateMaze(int x, int y) {
        maze[y][x] = false; // 标记为通路

        // 定义四个方向
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        // 随机打乱方向
        shuffleDirections(directions);

        for (int[] dir : directions) {
            int newX = x + dir[0] * 2;
            int newY = y + dir[1] * 2;

            if (isValid(newX, newY) && maze[newY][newX]) {
                // 打通墙壁
                maze[y + dir[1]][x + dir[0]] = false;
                generateMaze(newX, newY);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void shuffleDirections(int[][] directions) {
        for (int i = directions.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
    }
} 