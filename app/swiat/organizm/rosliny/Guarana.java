package app.swiat.organizm.rosliny;

import app.swiat.Swiat;
import app.swiat.organizm.Organizm;
import app.swiat.organizm.Roslina;

import java.awt.*;

public class Guarana extends Roslina {
    public Guarana(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0, 'G');
    }

    @Override
    public void akcja() {
        super.akcja();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        atakujacy.zwiekszSile(3);
        System.out.println("Organizm " + atakujacy.getSymbol() +
                " zjadl guarane, jego sila aktualnie wynosi: " +
                atakujacy.getSila());
        swiat.usunOrganizm(this);
    }

    @Override
    protected Roslina stworzPotomka(int nowyX, int nowyY) {
        return new Guarana(swiat, nowyX, nowyY);
    }

    @Override
    public Color getKolor() {
        return Color.MAGENTA; // domyślny kolor
    }
}