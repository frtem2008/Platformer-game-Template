//работа с мышью
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
    public int newX, newY, oldX, oldY, scroll;
    public boolean clicked;

    @Override
    public void mouseClicked(MouseEvent e) {
        //метод, который вызывается, когда мышка кликнута(нажата и отпущена)

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //код, меняющий переменную, когда мышка зажата
        Main.gamer.mouseClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //метод, который вызывается, когда мышка отпущена
        Main.gamer.mouseClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
