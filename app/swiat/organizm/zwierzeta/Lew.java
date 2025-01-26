package app.swiat.organizm.zwierzeta;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Zwierze;

import java.awt.*;

public class Lew extends Zwierze {
    public Lew(Swiat swiat, int x, int y) {
        super(swiat, x, y, 11, 7, 'L');
    }

    @Override
    protected Zwierze stworzPotomka(int nowyX, int nowyY) {
        return new Lew(swiat, nowyX, nowyY);
    }

    @Override
    public boolean czyOdparlAtak(Organizm atakujacy) {
        if (atakujacy.getSila() < 5) {
            System.out.println("Lew na polu (" + this.getX() + "," + this.getY() +
                    ") odstraszyl organizm " + atakujacy.getSymbol() +
                    " (sila " + atakujacy.getSila() +
                    ") z pola (" + atakujacy.getX() + "," + atakujacy.getY() +
                    ") - zbyt slaby by zaatakowac krola zwierzat!");
            return true;
        }
        return false;
    }

    @Override
    public Color getKolor() {
        return Color.ORANGE; // domyślny kolor
    }
}