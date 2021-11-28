import javax.swing.*;
import java.io.File;

public class Display extends JFrame {
    //штука для подключения open jl (оптимизация графики)
    static {
        System.setProperty("sun.java2d.opengl", "True");
    }
    //файл для определения пути к проекту
    public static File test = new File("test.txt");
    //наш осноовной холст
    public static JFrame frame = new JFrame("Platformer");
    //переменная для изменения размена экрана
    public static boolean isFullScreen = true;
    //дефолтные размеры экрана
    public static int w = 1000;
    public static int h = 800;

    public static void main(String[] args) throws InterruptedException {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //иконка холста (отображается на панели задач)
        ImageIcon img = new ImageIcon("src/Resources/Images/Icon.png");
        frame.setIconImage(img.getImage());
        //разворачиваем сразу на полный экран
        Display.frame.setExtendedState(6);
        frame.setUndecorated(true);
        frame.setVisible(true);
        //создаём новый экземпляр игры и начинаем рисовать
        Main m = new Main();
        m.startDrawing(frame);
    }
}