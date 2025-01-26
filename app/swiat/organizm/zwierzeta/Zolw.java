package app.swiat.organizm.zwierzeta;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Zwierze;

import java.awt.*;

public class Zolw extends Zwierze {
    public Zolw(Swiat swiat, int x, int y) {
        super(swiat, x, y, 2, 1, 'Z');
    }

    @Override
    public void akcja() {
        if (random.nextInt(100) < 75) {
            System.out.println("Zolw pozostaje w miejscu na polu (" +
                    this.getX() + "," + this.getY() + ")");
            return;
        }
        super.akcja();
    }

    @Override
    public boolean czyOdparlAtak(Organizm atakujacy) {
        if (atakujacy.getSila() < 5) {
            System.out.println("Zolw na polu (" + this.getX() + "," + this.getY() +
                    ") odparl atak organizmu " + atakujacy.getSymbol() +
                    " (sila " + atakujacy.getSila() +
                    ") z pola (" + atakujacy.getX() + "," + atakujacy.getY() + ")");
            return true;
        }
        return false;
    }

    @Override
    protected Zwierze stworzPotomka(int nowyX, int nowyY) {
        return new Zolw(swiat, nowyX, nowyY);
    }
    @Override
    public Color getKolor() {
        return Color.CYAN; // domyślny kolor
    }

}