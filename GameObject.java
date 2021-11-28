//класс для всех игровых объектов

import java.awt.*;

public class GameObject {
    //координаты
    int x, y, width, height;
    //хитбокс
    Rectangle hitBox;
    //текстура
    Image texture;
    //тип (spike, block и т.д.)
    String type;
    //конструктор
    public GameObject(int x, int y, int width, int height, Image texture, String type){
        this.type = type;
        if (type.equals("block") || type.equals("flat")) {
            //просто задаём все переменные
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.texture = texture;
            hitBox = new Rectangle(x, y, width, height);
        }else if (type.equals("spike")) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.texture = texture;
            //хитбокс шипа отличается от хитбокса блока
            hitBox = new Rectangle((int) (x + 0.2 * (width)), (int) (y + 0.4 * height), (int) (0.6 * width), (int) (0.4 * height));
        }else{
            //если это какой-то неизвестный тип, то он становиться блоком
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.texture = texture;
            hitBox = new Rectangle(x, y, width, height);
        }
    }
}
