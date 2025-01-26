package app.swiat.organizm;

import app.swiat.Punkt;
import app.swiat.Swiat;

import app.*;

import java.util.List;
import java.util.Random;

public abstract class Roslina implements Organizm {
    protected static final int SZANSA_ROZPRZESTRZENIENIA = 15;
    protected Random random = new Random();
    protected Swiat swiat;
    protected int x, y;
    protected int sila;
    protected char symbol;
    protected int wiek;
    protected int inicjatywa;

    public Roslina(Swiat swiat, int x, int y, int sila, char symbol) {
        this.swiat = swiat;
        this.x = x;
        this.y = y;
        this.sila = sila;
        this.symbol = symbol;
        this.inicjatywa = 0;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        sprobujRozprzestrzenanie();
    }

    @Override
    public void kolizja(Organizm atakujacy) {
        System.out.println("Organizm " + atakujacy.getSymbol() + " zjada rosline " + this.symbol);
        var message = "Organizm " + atakujacy.getSymbol() + " zjada rosline " + this.symbol;
        swiat.usunOrganizm(this);
    }

    protected boolean sprobujRozprzestrzenanie() {
        if (random.nextInt(100) < SZANSA_ROZPRZESTRZENIENIA) {
            Punkt nowaPozycja = znajdzWolnePoleSasiednie();

            if (nowaPozycja != null) {
                Roslina nowaRoslina = stworzPotomka(nowaPozycja.x, nowaPozycja.y);
                try {
                    swiat.dodajOrganizm(nowaRoslina);
                    System.out.println("Nowa roslina " + this.symbol + " wyrosla na polu ("
                            + nowaPozycja.x + "," + nowaPozycja.y + ")");
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    protected Punkt znajdzWolnePoleSasiednie() {
        int[][] kierunki = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        List<Punkt> wolnePola = swiat.getWolnePolaSasiednie(x, y);

        if (!wolnePola.isEmpty()) {
            return wolnePola.get(random.nextInt(wolnePola.size()));
        }

        return null;
    }

    @Override
    public void rysowanie() {
        System.out.print(symbol);
    }

    @Override
    public boolean czyTenSamGatunek(Organizm other) {
        return this.getClass() == other.getClass();
    }

    protected abstract Roslina stworzPotomka(int x, int y);

    // Gettery i settery
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getSila() { return sila; }
    @Override public int getInicjatywa() { return inicjatywa; }
    @Override public int getWiek() { return wiek; }
    @Override public char getSymbol() { return symbol; }
    @Override public Swiat getSwiat() { return swiat; }

    @Override
    public void setPozycja(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void zwiekszWiek() {
        wiek++;
    }

    @Override
    public void zwiekszSile(int wartosc) {
        sila += wartosc;
    }
}