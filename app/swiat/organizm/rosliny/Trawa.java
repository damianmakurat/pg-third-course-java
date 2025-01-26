package app.swiat.organizm.rosliny;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Roslina;

import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0, 'T');
    }

    @Override
    public void akcja() {
        super.akcja();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        System.out.println("Organizm " + atakujacy.getSymbol() +
                " zjada trawe na polu (" + this.getX() +
                "," + this.getY() + ")");
        swiat.usunOrganizm(this);
    }

    @Override
    protected Roslina stworzPotomka(int nowyX, int nowyY) {
        return new Trawa(swiat, nowyX, nowyY);
    }

    @Override
    public Color getKolor() {
        return Color.GREEN; // domyślny kolor
    }
}