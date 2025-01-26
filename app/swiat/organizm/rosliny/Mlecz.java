package app.swiat.organizm.rosliny;

import app.swiat.Swiat;
import app.swiat.organizm.Roslina;

import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0, 'M');
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; ++i) {
            sprobujRozprzestrzenanie();
        }
    }

    @Override
    protected Roslina stworzPotomka(int nowyX, int nowyY) {
        return new Mlecz(swiat, nowyX, nowyY);
    }

    @Override
    public Color getKolor() {
        return Color.YELLOW; // domyślny kolor
    }
}