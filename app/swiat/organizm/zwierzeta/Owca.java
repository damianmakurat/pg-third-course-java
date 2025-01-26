package app.swiat.organizm.zwierzeta;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Zwierze;

import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Swiat swiat, int x, int y) {
        super(swiat, x, y, 4, 4, 'O');
    }

    @Override
    protected Zwierze stworzPotomka(int nowyX, int nowyY) {
        return new Owca(swiat, nowyX, nowyY);
    }

    @Override
    public boolean czyOdparlAtak(Organizm atakujacy) {
        return false;
    }

    @Override
    public Color getKolor() {
        return Color.LIGHT_GRAY; // domyślny kolor
    }
}