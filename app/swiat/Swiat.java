package app.swiat;

import app.swiat.organizm.Organizm;
import app.swiat.organizm.rosliny.Guarana;
import app.swiat.organizm.rosliny.Mlecz;
import app.swiat.organizm.rosliny.Trawa;
import app.swiat.organizm.zwierzeta.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Swiat {
    private int width;
    private int height;
    private List<Organizm> organizmy;
    private Random random;

    public Swiat(int width, int height) {
        this.width = width;
        this.height = height;
        this.organizmy = new ArrayList<>();
        this.random = new Random();
        inicjalizujSwiat();
    }

    public void wykonajTure() {
        sortujOrganizmy();
        List<Organizm> aktywneOrganizmy = new ArrayList<>(organizmy);

        for (Organizm organizm : aktywneOrganizmy) {
            if (organizm != null && organizmy.contains(organizm)) {
                organizm.zwiekszWiek();
                organizm.akcja();
            }
        }

        czyscMartweOrganizmy();
    }

    public void dodajOrganizm(Organizm organizm) {
        if (organizm == null) return;

        try {
            if (!czyPozycjaPoprawna(organizm.getX(), organizm.getY())) {
                throw new IllegalArgumentException("Pozycja organizmu poza granicami świata");
            }

            if (getOrganizmNaPolu(organizm.getX(), organizm.getY()) != null) {
                throw new IllegalStateException("Pole jest już zajęte przez inny organizm");
            }

            organizmy.add(organizm);
        } catch (Exception e) {
            throw e;
        }
    }

    public void usunOrganizm(Organizm organizm) {
        organizmy.remove(organizm);
    }

    public Organizm getOrganizmNaPolu(int x, int y) {
        return organizmy.stream()
                .filter(org -> org != null && org.getX() == x && org.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public boolean czyPozycjaPoprawna(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public List<Punkt> getWolnePolaSasiednie(int x, int y) {
        List<Punkt> wolnePola = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int newX = x + dx;
                int newY = y + dy;

                if (czyPozycjaPoprawna(newX, newY) && getOrganizmNaPolu(newX, newY) == null) {
                    wolnePola.add(new Punkt(newX, newY));
                }
            }
        }

        return wolnePola;
    }

    private void sortujOrganizmy() {
        organizmy.sort((a, b) -> {
            if (a == null) return 1;
            if (b == null) return -1;

            int compareInicjatywa = Integer.compare(b.getInicjatywa(), a.getInicjatywa());
            if (compareInicjatywa != 0) return compareInicjatywa;

            return Integer.compare(b.getWiek(), a.getWiek());
        });
    }

    private void czyscMartweOrganizmy() {
        organizmy.removeIf(Objects::isNull);
    }

    private void inicjalizujSwiat() {
        organizmy.clear();

        int liczbaKazdegoPoczatkowego = 5;

        // Dodawanie zwierząt
        for (int i = 0; i < liczbaKazdegoPoczatkowego; i++) {
            Punkt pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Wilk(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Owca(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Lew(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Zolw(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Kot(this, pos.x, pos.y));
        }

        // Dodawanie roślin
        for (int i = 0; i < liczbaKazdegoPoczatkowego; i++) {
            Punkt pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Trawa(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Mlecz(this, pos.x, pos.y));

            pos = getLosowaWolnaPozycja();
            dodajOrganizm(new Guarana(this, pos.x, pos.y));
        }
    }

    private Punkt getLosowaWolnaPozycja() {
        while (true) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (getOrganizmNaPolu(x, y) == null) {
                return new Punkt(x, y);
            }
        }
    }

    public void zapiszDoPliku(String nazwaPliku) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nazwaPliku))) {
            writer.write(width + " " + height + "\n");
            writer.write(organizmy.size() + "\n");

            for (Organizm org : organizmy) {
                writer.write(String.format("%s %d %d %d %d %c\n",
                        org.getClass().getSimpleName(),
                        org.getX(),
                        org.getY(),
                        org.getSila(),
                        org.getWiek(),
                        org.getSymbol()));
            }
        }
    }

    public void wczytajZPliku(String nazwaPliku) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nazwaPliku))) {
            organizmy.clear();

            String[] wymiary = reader.readLine().split(" ");
            width = Integer.parseInt(wymiary[0]);
            height = Integer.parseInt(wymiary[1]);

            int liczbaOrganizmow = Integer.parseInt(reader.readLine());

            for (int i = 0; i < liczbaOrganizmow; i++) {
                String[] dane = reader.readLine().split(" ");
                String typ = dane[0];
                int x = Integer.parseInt(dane[1]);
                int y = Integer.parseInt(dane[2]);
                int sila = Integer.parseInt(dane[3]);
                int wiek = Integer.parseInt(dane[4]);

                Organizm org = switch (typ) {
                    case "Wilk" -> new Wilk(this, x, y);
                    case "Owca" -> new Owca(this, x, y);
                    case "Lew" -> new Lew(this, x, y);
                    case "Zolw" -> new Zolw(this, x, y);
                    case "Kot" -> new Kot(this, x, y);
                    case "Trawa" -> new Trawa(this, x, y);
                    case "Mlecz" -> new Mlecz(this, x, y);
                    case "Guarana" -> new Guarana(this, x, y);
                    default -> throw new IllegalStateException("Nieznany typ organizmu: " + typ);
                };

                org.zwiekszSile(sila - org.getSila());
                for(int w = 0; w < wiek; w++) org.zwiekszWiek();
                organizmy.add(org);
            }
        }
    }

    // Gettery
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public List<Organizm> getOrganizmy() { return organizmy; }
}