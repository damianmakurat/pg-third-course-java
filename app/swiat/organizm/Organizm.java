package app.swiat.organizm;

import app.swiat.Swiat;

import java.awt.*;

public interface Organizm {
    void akcja();
    void kolizja(Organizm other);
    void rysowanie();
    boolean czyTenSamGatunek(Organizm other);

    void setPozycja(int x, int y);
    void zwiekszWiek();
    void zwiekszSile(int wartosc);

    int getX();
    int getY();
    int getSila();
    int getInicjatywa();
    int getWiek();
    char getSymbol();
    Color getKolor();
    Swiat getSwiat();
}