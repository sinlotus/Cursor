package org.example.maze;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazeFrame extends JFrame {
    private static final int CELL_SIZE = 30;
    private static final Color WALL_COLOR = Color.BLACK;
    private static final Color PATH_COLOR = Color.GREEN;
    private static final Color START_COLOR = Color.BLUE;
    private static final Color END_COLOR = Color.RED;
    private static final Color EMPTY_COLOR = Color.WHITE;
    private static final Color CURRENT_COLOR = Color.YELLOW;

    private final Maze maze;
    private final List<Maze.Position> path;
    private int currentPathIndex = 0;
    private Timer animationTimer;

    public MazeFrame(Maze maze, List<Maze.Position> path) {
        this.maze = maze;
        this.path = path;

        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
            }
        };

        mazePanel.setPreferredSize(new Dimension(
                maze.getWidth() * CELL_SIZE,
                maze.getHeight() * CELL_SIZE
        ));

        add(mazePanel);
        pack();
        setLocationRelativeTo(null);

        // 设置动画定时器
        animationTimer = new Timer(100, e -> {
            if (currentPathIndex < path.size()) {
                currentPathIndex++;
                mazePanel.repaint();
            } else {
                animationTimer.stop();
            }
        });
    }

    public void startAnimation() {
        if (animationTimer != null && !animationTimer.isRunning()) {
            currentPathIndex = 0;
            animationTimer.start();
        }
    }

    private void drawMaze(Graphics g) {
        // 绘制迷宫背景
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                if (maze.isWall(x, y)) {
                    g.setColor(WALL_COLOR);
                } else {
                    g.setColor(EMPTY_COLOR);
                }
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 绘制路径
        g.setColor(PATH_COLOR);
        for (int i = 0; i < currentPathIndex; i++) {
            Maze.Position pos = path.get(i);
            if (!pos.equals(maze.getStart()) && !pos.equals(maze.getEnd())) {
                g.fillRect(pos.getX() * CELL_SIZE, pos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // 绘制当前路径点
        if (currentPathIndex < path.size()) {
            g.setColor(CURRENT_COLOR);
            Maze.Position currentPos = path.get(currentPathIndex);
            g.fillRect(currentPos.getX() * CELL_SIZE, currentPos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // 绘制起点和终点
        g.setColor(START_COLOR);
        g.fillRect(maze.getStart().getX() * CELL_SIZE, maze.getStart().getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.setColor(END_COLOR);
        g.fillRect(maze.getEnd().getX() * CELL_SIZE, maze.getEnd().getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // 绘制网格线
        g.setColor(Color.GRAY);
        for (int i = 0; i <= maze.getWidth(); i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, maze.getHeight() * CELL_SIZE);
        }
        for (int i = 0; i <= maze.getHeight(); i++) {
            g.drawLine(0, i * CELL_SIZE, maze.getWidth() * CELL_SIZE, i * CELL_SIZE);
        }
    }
} 