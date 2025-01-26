package app.swiat.organizm.zwierzeta;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Zwierze;

import java.awt.*;

public class Kot extends Zwierze {
    public Kot(Swiat swiat, int x, int y) {
        super(swiat, x, y, 5, 9, 'K');
    }

    @Override
    protected Zwierze stworzPotomka(int nowyX, int nowyY) {
        return new Kot(swiat, nowyX, nowyY);
    }

    @Override
    public boolean czyOdparlAtak(Organizm atakujacy) {
        return false;
    }

    @Override
    public Color getKolor() {
        return Color.PINK; // domyślny kolor
    }
}