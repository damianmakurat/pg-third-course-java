package app.swiat.organizm;

import app.swiat.Swiat;
import app.swiat.organizm.rosliny.Guarana;

import java.util.Random;

public abstract class Zwierze implements Organizm {
    protected Swiat swiat;
    protected int x, y;
    protected int sila;
    protected int inicjatywa;
    protected char symbol;
    protected int wiek;
    protected Random random = new Random();

    public Zwierze(Swiat swiat, int x, int y, int sila, int inicjatywa, char symbol) {
        this.swiat = swiat;
        this.x = x;
        this.y = y;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.symbol = symbol;
        this.wiek = 0;
    }

    @Override
    public void akcja() {
        int[] nowaPozycja = losujNowaPozycje();
        int nowyX = nowaPozycja[0], nowyY = nowaPozycja[1];

        if (swiat.czyPozycjaPoprawna(nowyX, nowyY)) {
            Organizm other = swiat.getOrganizmNaPolu(nowyX, nowyY);
            if (other != null) {
                kolizja(other);
            } else {
                setPozycja(nowyX, nowyY);
            }
        }
    }

    @Override
    public void kolizja(Organizm other) {
        if (czyTenSamGatunek(other)) {
            if (sprobujRozmnozenie(other)) {
                System.out.println("Nowy organizm " + this.symbol + " pojawil sie na swiecie!");
            }
            return;
        }

        if (other instanceof Guarana) {
            zwiekszSile(3);
            System.out.println("Zwierze " + this.symbol + " zwieksza sile o 3. Aktualna sila: " + this.getSila());
            swiat.usunOrganizm(other);
            setPozycja(other.getX(), other.getY());
            return;
        }

        System.out.println("Walka: " + this.symbol + " (sila " + this.getSila() +
                ") vs " + other.getSymbol() + " (sila " + other.getSila() + ")");

        if (this.getSila() >= other.getSila()) {
            System.out.println("Organizm " + other.getSymbol() + " zostal zabity przez " + this.symbol);
            swiat.usunOrganizm(other);
            setPozycja(other.getX(), other.getY());
        } else {
            System.out.println("Organizm " + this.symbol + " zostal zabity przez " + other.getSymbol());
            swiat.usunOrganizm(this);
        }
    }

    protected int[] losujNowaPozycje() {
        int kierunek = random.nextInt(4);
        int nowyX = x, nowyY = y;

        switch (kierunek) {
            case 0: nowyY--; break;
            case 1: nowyY++; break;
            case 2: nowyX--; break;
            case 3: nowyX++; break;
        }

        return new int[]{nowyX, nowyY};
    }

    protected boolean sprobujRozmnozenie(Organizm partner) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int nowyX = x + dx;
                int nowyY = y + dy;

                if (swiat.czyPozycjaPoprawna(nowyX, nowyY) &&
                        swiat.getOrganizmNaPolu(nowyX, nowyY) == null) {
                    swiat.dodajOrganizm(stworzPotomka(nowyX, nowyY));
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract Zwierze stworzPotomka(int nowyX, int nowyY);

    // Implementacja pozostałych metod z interfejsu Organizm
    @Override public void rysowanie() { System.out.print(symbol); }
    @Override public boolean czyTenSamGatunek(Organizm other) { return this.getClass() == other.getClass(); }
    @Override public void setPozycja(int x, int y) { this.x = x; this.y = y; }
    @Override public void zwiekszWiek() { wiek++; }
    @Override public void zwiekszSile(int wartosc) { this.sila += wartosc; }

    // Gettery
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getSila() { return sila; }
    @Override public int getInicjatywa() { return inicjatywa; }
    @Override public int getWiek() { return wiek; }
    @Override public char getSymbol() { return symbol; }
    @Override public Swiat getSwiat() { return swiat; }

    public abstract boolean czyOdparlAtak(Organizm atakujacy);
}