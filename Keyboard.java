//класс для работы с клавиатурой (обработка нескольких нажатий одновременно)

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private static final boolean[] keys = new boolean[66568];
    private static boolean left;
    private static boolean tab;
    private static boolean right;
    private static boolean up;
    private static boolean space;
    private static boolean shift;
    private static boolean f11;
    private static boolean s;
    private static boolean e;
    private static boolean r;
    private static boolean f;
    private static boolean q;
    private static boolean c;
    private static boolean v;
    private static boolean g;
    private static boolean x;
    private static boolean z;
    private static boolean b;
    private static boolean n;
    private static boolean m;
    private static boolean u;
    private static boolean ctrl;
    private static boolean h;

    public Keyboard() {

    }

    public boolean getB() {
        return b;
    }

    public boolean getN() {
        return n;
    }

    public boolean getM() {
        return m;
    }

    public boolean getU() {
        return u;
    }

    public boolean getH() {
        return h;
    }

    public boolean getCtrl() {
        return ctrl;
    }

    public void update() {
        h = keys[KeyEvent.VK_H];
        tab = keys[KeyEvent.VK_TAB];
        n = keys[KeyEvent.VK_N];
        m = keys[KeyEvent.VK_M];
        u = keys[KeyEvent.VK_U];
        ctrl = keys[KeyEvent.VK_CONTROL];
        b = keys[66];
        z = keys[90];
        left = keys[37] || keys[65];
        right = keys[39] || keys[68];
        up = keys[38] || keys[87] || keys[32];
        space = keys[32];
        shift = keys[16];
        f11 = keys[122];
        s = keys[83];
        e = keys[69];
        r = keys[82];
        f = keys[70];
        q = keys[81];
        c = keys[67];
        v = keys[86];
        g = keys[71];
        x = keys[88] || keys[127];
    }


    public boolean getX() {
        return x;
    }

    public boolean getV() {
        return v;
    }

    public boolean getC() {
        return c;
    }

    public boolean getQ() {
        return q;
    }

    public boolean getF() {
        return f;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        // return right;
        return true;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getF11() {
        return f11;
    }

    public boolean getS() {
        return s;
    }

    public boolean getShift() {
        return shift;
    }

    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent event) {
    }
}
