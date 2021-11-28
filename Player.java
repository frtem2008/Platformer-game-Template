import java.awt.*;
import java.util.TimerTask;

public class Player {
    //стандартный набор координат + 2 для респауна(начальные)
    int x, y, width, height, fX, fY;
    //штука для обозначения нажатой мышки (меняется в классе Mouse)
    public boolean mouseClicked = false;
    //ограничения по скорости (пикселей / секунду)
    int maxXSpeed = 6;
    int maxYSpeed = 10;
    //переменные для скорости
    double xSpeed, ySpeed;
    //высота прыжка
    double jumpHeight = 6;
    //гравитация
    double gravitation = 0.3;
    //время после респауна, в течение которого игрок не двигается
    long respawnTime = 1000;
    //делаем хитбокс
    Rectangle hitBox;
    //создаём клавиатуру
    Keyboard keyboard = new Keyboard();
    //переменная, которая показывает, респауниться игрок или нет
    boolean respawning = false;

    //создаём игрока
    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.fX = x;
        this.fY = y;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);

    }

    //физика игрока (обновляеться в каждом кадре)
    public void set() {
        //изменение скорости
        if (!respawning) {
            if (keyboard.getLeft() && keyboard.getRight() || !keyboard.getLeft() && !keyboard.getRight()) {
                xSpeed *= 0.8;
            } else if (keyboard.getLeft() && !keyboard.getRight()) {
                xSpeed--;
            } else if (!keyboard.getLeft() && keyboard.getRight()) {
                xSpeed++;
            }
            //прыжок
            if (keyboard.getUp() || mouseClicked) {
                hitBox.y++;
                for (int i = 0; i < Main.gameObjects.size(); i++) {
                    if (Main.gameObjects.get(i).hitBox.intersects(hitBox)) {
                        ySpeed = -jumpHeight;
                    }
                }
                hitBox.y--;
            }

        }

        //гравитация
        ySpeed += gravitation;

        //ограничение скорости

        if (xSpeed > 0 && xSpeed < 0.75) {
            xSpeed = 0;
        }
        if (xSpeed < 0 && xSpeed > -0.75) {
            xSpeed = 0;
        }
        if (xSpeed > maxXSpeed) {
            xSpeed = maxXSpeed;
        }
        if (xSpeed < -maxXSpeed) {
            xSpeed = -maxXSpeed;
        }
        if (ySpeed > maxYSpeed) {
            ySpeed = maxYSpeed;
        }

        //горизонтальные столкновения
        hitBox.x += xSpeed;
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            if (Main.gameObjects.get(i).hitBox.intersects(hitBox)) {
                hitBox.x -= xSpeed;
                while (!Main.gameObjects.get(i).hitBox.intersects(hitBox)) {
                    hitBox.x += Math.signum(xSpeed);
                }
                hitBox.x -= Math.signum(xSpeed);
                xSpeed = 0;
                x = hitBox.x;
                //методы после касания
                //оставить пустым, чтобы просто не проходить через блок
                /* respawn();
                if (!Main.gameObjects.get(i).type.equals("block"))
                    respawn();*/
            }
        }
        //вертикальные столкновения
        hitBox.y += ySpeed;
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            if (Main.gameObjects.get(i).hitBox.intersects(hitBox)) {
                hitBox.y -= ySpeed;
                while (!Main.gameObjects.get(i).hitBox.intersects(hitBox)) {
                    hitBox.y += Math.signum(ySpeed);
                }
                hitBox.y -= Math.signum(ySpeed);
                ySpeed = 0;
                y = hitBox.y;
                //методы после касания
                //оставить пустым, чтобы просто не проходить через блок
                /*if (!Main.gameObjects.get(i).type.equals("block"))
                    respawn();
                 if (ySpeed < -2) {
                    respawn();
                }
                */

            }
        }

        //изменение координат относительно скорости
        x += xSpeed;
        y += ySpeed;
        hitBox.x = x;
        hitBox.y = y;
    }

    //метод для респауна
    public void respawn() {
        //начало респауна
        respawning = true;

        //сброс координат до начальных
        Player.this.x = fX;
        Player.this.y = fY;

        //метод обнуления камеры игрока
        //Main.cameraY = 0;

        //задержка при респауне
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                respawning = false;
            }
        }, respawnTime);
    }

}