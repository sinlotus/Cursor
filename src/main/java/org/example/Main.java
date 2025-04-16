package org.example;

import org.example.maze.Maze;
import org.example.maze.MazeFrame;
import org.example.maze.MazeGenerator;
import org.example.maze.PathFinder;
import java.util.List;
import javax.swing.SwingUtilities;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.printf("Hello and welcome!");

        // 创建迷宫生成器
        MazeGenerator generator = new MazeGenerator(21, 21); // 21x21的迷宫
        boolean[][] mazeGrid = generator.generate();

        // 创建迷宫对象
        Maze maze = new Maze(mazeGrid);

        // 创建路径查找器
        PathFinder pathFinder = new PathFinder(maze);
        List<Maze.Position> path = pathFinder.findPath();

        // 创建并显示图形界面
        SwingUtilities.invokeLater(() -> {
            MazeFrame frame = new MazeFrame(maze, path);
            frame.setVisible(true);
            // 使用SwingUtilities.invokeLater确保在窗口显示后再启动动画
            SwingUtilities.invokeLater(frame::startAnimation);
        });

        // 打印路径信息
        System.out.println("Path Information:");
        System.out.println("Start: (" + maze.getStart().getX() + ", " + maze.getStart().getY() + ")");
        System.out.println("End: (" + maze.getEnd().getX() + ", " + maze.getEnd().getY() + ")");
        System.out.println("Path Length: " + path.size() + " steps");

        for (int i = 1; i <= 5; i++) {
            //TIP 按 <shortcut actionId="Debug"/> 开始调试代码。我们已经设置了一个 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 断点
            // 但您始终可以通过按 <shortcut actionId="ToggleLineBreakpoint"/> 添加更多断点。
            System.out.println("i = " + i);
        }
    }
}