import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main {

    //способ узнать путь к проекту
    public static String projectPath = Display.test.getAbsolutePath().replaceAll("/test.txt", "");

    //непосредственно наш игрок
    public static Player gamer = new Player(0, 752, 50,50);

    //все игровые объекты в 1 списке
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    //камера
    /* public static double cameraY = 0;
    public static double minCameraY = 700;
    public static double maxCameraY = 520;*/

    //показ всех хитбоксов
    public boolean showHitboxes = true;

    //все основные архитектуры
    JFrame frame;
    Keyboard keyboard = new Keyboard();
    Mouse mouse = new Mouse();

    //все текстуры
    Image Player;
    Image Block;
    Image Spike;

    public Main() {

    }


    //методы для движения объектов, а не игрока
    public static int toScreenX(double x) {
        //меняйте, чтобы приблизить или отдалить камеру от левого края экрана
        double xLayout = Display.frame.getWidth() / 3;
        return (int) ((xLayout) + (x - gamer.x));
        // старый метод из платформера
        //return (int) ((Display.frame.getWidth() / 2) + (x - gamer.x));
    }

    public static int toScreenY(double y) {
        //для постоянного положения игрока
        return (int) ((Display.frame.getHeight() / 2) + (y - gamer.y));

        //для передвижения камеры по y

        //return (int) (y - cameraY);
    }

    //функция перемещения камеры по y
    /*
    public static int playerY(double y) {
        if (y - cameraY >= minCameraY - gamer.height) {
            if (y - cameraY < Display.h) {
                if (gamer.ySpeed >= 2) {
                    cameraY += gamer.ySpeed / 2;
                } else {
                    cameraY += 2;
                }
            } else {
                cameraY += gamer.ySpeed;
            }
        } else if (y - cameraY <= maxCameraY + gamer.height) {
            if (y - cameraY < Display.h) {
                cameraY -= 2;
            } else {
                cameraY -= 4;
            }
        }
        return 0;
    }
    */

    //метод инициализации всех объектов (для готового уровня скопируйте сюда все из block levels.txt)
    public void fillBlocks() {

        //плоскость
        gameObjects.add(new GameObject(-200, 800, 16000, 300, Block, "block"));
        //шип
        gameObjects.add(new GameObject(128, 750, 50, 50, Spike, "spike"));
        //блок
        gameObjects.add(new GameObject(500, 740, 60, 60, Block, "block"));
        }

    //магический код для начала игры.

    public void startDrawing(JFrame frame) throws InterruptedException {
        //создаём холст
        this.frame = frame;
        //привязываем слушатели
        frame.addKeyListener(keyboard);
        frame.addMouseListener(mouse);
        //создаём поток под музыку
        AudioThread music = new AudioThread();
        Thread musicPLayer = new Thread(music);
        musicPLayer.start();
        //подгружаем изображения
        this.loadImages();
        //заполняем уровень объектами
        fillBlocks();
        //графика
        frame.createBufferStrategy(2);
        while (true) {
            long frameLength = 1000 / 60;
            long start = System.currentTimeMillis();
            BufferStrategy bs = frame.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
            this.draw(g);
            bs.show();
            g.dispose();
            long end = System.currentTimeMillis();
            long len = end - start;

            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }

            //код для выхода из игры
            if (keyboard.getQ()) {
                System.out.println("Выход");
                System.exit(20);
            }
            //обновления клавиатуры и физики игрока
            keyboard.update();
            gamer.set();

        }
    }

    public void draw(Graphics g) {
        //рисование
        for (int i = 0; i < gameObjects.size(); i++) {
            //оптимизация по отображению
            if (gameObjects.get(i).x - gamer.x < 1500) {
                //рисуем объект
                g.drawImage(gameObjects.get(i).texture, toScreenX(gameObjects.get(i).x), toScreenY(gameObjects.get(i).y), gameObjects.get(i).width, gameObjects.get(i).height, null);
                //рисуем его хитбокс (актуально для шипов и всего такого)
                if (showHitboxes) {
                    g.setColor(Color.red);
                    g.drawRect(toScreenX(gameObjects.get(i).hitBox.x), toScreenY(gameObjects.get(i).hitBox.y), gameObjects.get(i).hitBox.width, gameObjects.get(i).hitBox.height);
                }
            }
        }
        g.setColor(Color.black);
        //рисуем игрока
        g.drawImage(Player, toScreenX(gamer.x), toScreenY(gamer.y), gamer.width, gamer.height, null);

    }

    //функция загрузки изображений (путь к папке: src/Resources/Images/)
    public void loadImages() {
        Player = new ImageIcon("src/Resources/Images/Player.png").getImage();
        Block = new ImageIcon("src/Resources/Images/Block.png").getImage();
        Spike = new ImageIcon("src/Resources/Images/Spike 2.png").getImage();
    }


    //класс для отдельного аудиопотока

    static class AudioThread implements Runnable {
        @Override
        public void run() {
            //путь к звуковому файлу с расширением .wav
            String path = "src/Resources/Sounds/StereoMadness.wav";

            boolean plays = true;

            //создаём звуковой файл
            Audio track = null;
            track = new Audio(path, 0.2, 85000);
            track.sound();
            track.setVolume();
            track.play();
            //проигрываем
            while (true) {
                if (!gamer.respawning) {
                    if (!plays) {
                        track.play();
                    }
                    plays = true;
                    track.repeat();
                } else {
                    plays = false;
                    track.stop();
                }
            }
        }
    }

}
