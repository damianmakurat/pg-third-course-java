package app.swiat.organizm.zwierzeta;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze {
    public Wilk(Swiat swiat, int x, int y) {
        super(swiat, x, y, 9, 5, 'W');
    }

    @Override
    protected Zwierze stworzPotomka(int nowyX, int nowyY) {
        return new Wilk(swiat, nowyX, nowyY);
    }

    @Override
    public boolean czyOdparlAtak(Organizm atakujacy) {
        return false;
    }
    @Override
    public Color getKolor() {
        return Color.GRAY; // domyślny kolor
    }
}