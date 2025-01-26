package app;

import app.swiat.Swiat;
import app.swiat.organizm.*;
import app.swiat.organizm.rosliny.Guarana;
import app.swiat.organizm.rosliny.Mlecz;
import app.swiat.organizm.rosliny.Trawa;
import app.swiat.organizm.zwierzeta.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private JPanel planszaPanel;
    private JTextArea logArea;
    private static final int ROZMIAR_KOMORKI = 30;
    private Swiat swiat;

    public Main() {
        super("Wirtualny Swiat - Damian Makurat");
        swiat = new Swiat(20, 20);
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setContentPane(mainPanel);

        planszaPanel = new JPanel(new GridLayout(20, 20));
        initializePlanszaPanel(mainPanel, gbc);
        initializeLogPanel(mainPanel, gbc);
        initializeButtonPanel(mainPanel, gbc);
        initializeMenuBar();

        pack();
        setLocationRelativeTo(null);
        odswiezPlansze();
    }

    private void initializePlanszaPanel(JPanel mainPanel, GridBagConstraints gbc) {
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                JPanel komorka = new JPanel();
                komorka.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                komorka.setPreferredSize(new Dimension(ROZMIAR_KOMORKI, ROZMIAR_KOMORKI));

                final int finalX = x;
                final int finalY = y;
                komorka.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            pokazMenuKontekstowe(e.getComponent(), e.getX(), e.getY(), finalX, finalY);
                        }
                    }
                });

                planszaPanel.add(komorka);
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(planszaPanel, gbc);
    }

    private void pokazMenuKontekstowe(Component component, int x, int y, int gridX, int gridY) {
        if (component instanceof JPanel) {
            ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        }

        JPopupMenu menu = new JPopupMenu();
        menu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (component instanceof JPanel) {
                    ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                }
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (component instanceof JPanel) {
                    ((JPanel) component).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                }
            }
        });

        JMenuItem killItem = new JMenuItem("Instant Kill");
        killItem.addActionListener(e -> {
            Organizm organizm = swiat.getOrganizmNaPolu(gridX, gridY);
            if (organizm != null) {
                swiat.usunOrganizm(organizm);
                dodajLog("Instant Kill: usunięto organizm " + organizm.getSymbol() + " z pola (" + gridX + "," + gridY + ")");
                odswiezPlansze();
            } else {
                JOptionPane.showMessageDialog(this, "Brak organizmu na tym polu");
            }
        });

        JMenu dodajZwierzeMenu = new JMenu("Dodaj zwierzę");
        for (Zwierzeta zwierze : Zwierzeta.values()) {
            JMenuItem zwierzeItem = new JMenuItem(zwierze.name());
            zwierzeItem.addActionListener(e -> {
                try {
                    if (swiat.getOrganizmNaPolu(gridX, gridY) == null) {
                        Zwierze noweZwierze = null;
                        switch (zwierze) {
                            case WILK -> noweZwierze = new Wilk(swiat, gridX, gridY);
                            case OWCA -> noweZwierze = new Owca(swiat, gridX, gridY);
                            case LEW -> noweZwierze = new Lew(swiat, gridX, gridY);
                            case ZOLW -> noweZwierze = new Zolw(swiat, gridX, gridY);
                            case KOT -> noweZwierze = new Kot(swiat, gridX, gridY);
                        }
                        if (noweZwierze != null) {
                            swiat.dodajOrganizm(noweZwierze);
                            dodajLog("Dodano " + zwierze.name() + " na pole (" + gridX + "," + gridY + ")");
                            odswiezPlansze();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Pole jest już zajęte");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Błąd podczas dodawania: " + ex.getMessage());
                }
            });
            dodajZwierzeMenu.add(zwierzeItem);
        }

        JMenu dodajRoslineMenu = new JMenu("Dodaj roślinę");
        for (Rosliny roslina : Rosliny.values()) {
            JMenuItem roslinaItem = new JMenuItem(roslina.name());
            roslinaItem.addActionListener(e -> {
                try {
                    if (swiat.getOrganizmNaPolu(gridX, gridY) == null) {
                        Roslina nowaRoslina = null;
                        switch (roslina) {
                            case TRAWA -> nowaRoslina = new Trawa(swiat, gridX, gridY);
                            case MLECZ -> nowaRoslina = new Mlecz(swiat, gridX, gridY);
                            case GUARANA -> nowaRoslina = new Guarana(swiat, gridX, gridY);
                        }
                        if (nowaRoslina != null) {
                            swiat.dodajOrganizm(nowaRoslina);
                            dodajLog("Dodano " + roslina.name() + " na pole (" + gridX + "," + gridY + ")");
                            odswiezPlansze();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Pole jest już zajęte");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Błąd podczas dodawania: " + ex.getMessage());
                }
            });
            dodajRoslineMenu.add(roslinaItem);
        }

        JMenuItem zmienSileItem = new JMenuItem("Zmień siłę");
        zmienSileItem.addActionListener(e -> {
            Organizm organizm = swiat.getOrganizmNaPolu(gridX, gridY);
            if (organizm != null) {
                String input = JOptionPane.showInputDialog(
                        this,
                        "Aktualna siła: " + organizm.getSila() + "\nPodaj nową wartość siły:",
                        "Zmiana siły",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (input != null && !input.isEmpty()) {
                    try {
                        int nowaSila = Integer.parseInt(input);
                        if (nowaSila >= 0) {
                            int zmiana = nowaSila - organizm.getSila();
                            organizm.zwiekszSile(zmiana);
                            dodajLog("Zmieniono siłę " + organizm.getSymbol() +
                                    " na polu (" + gridX + "," + gridY + ") z " +
                                    (organizm.getSila() - zmiana) + " na " + nowaSila);
                            odswiezPlansze();
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Siła nie może być ujemna",
                                    "Błąd",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Nieprawidłowa wartość",
                                "Błąd",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Brak organizmu na tym polu");
            }
        });

        menu.add(killItem);
        menu.add(dodajZwierzeMenu);
        menu.add(dodajRoslineMenu);
        menu.add(zmienSileItem);
        menu.show(component, x, y);
    }

    private void initializeLogPanel(JPanel mainPanel, GridBagConstraints gbc) {
        JPanel logPanel = new JPanel(new BorderLayout());
        logArea = new JTextArea(20, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(new JLabel("Logi gry:"), BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(logPanel, gbc);
    }

    private void initializeButtonPanel(JPanel mainPanel, GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel();
        JButton nextTurnButton = new JButton("Następna tura");
        nextTurnButton.addActionListener(e -> {
            dodajLog("Rozpoczęto nową turę");
            swiat.wykonajTure();
            odswiezPlansze();
        });
        buttonPanel.add(nextTurnButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(buttonPanel, gbc);
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Plik");

        JMenuItem saveItem = new JMenuItem("Zapisz");
        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe (*.txt)", "txt"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".txt")) filePath += ".txt";
                try {
                    swiat.zapiszDoPliku(filePath);
                    dodajLog("Zapisano stan gry do pliku: " + filePath);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Błąd podczas zapisu: " + ex.getMessage());
                }
            }
        });

        JMenuItem loadItem = new JMenuItem("Wczytaj");
        loadItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe (*.txt)", "txt"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    swiat.wczytajZPliku(fileChooser.getSelectedFile().getAbsolutePath());
                    odswiezPlansze();
                    dodajLog("Wczytano stan gry z pliku: " + fileChooser.getSelectedFile().getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania: " + ex.getMessage());
                }
            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void odswiezPlansze() {
        Component[] komorki = planszaPanel.getComponents();
        int index = 0;

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                JPanel komorka = (JPanel) komorki[index++];
                komorka.removeAll();
                Organizm organizm = swiat.getOrganizmNaPolu(x, y);
                if (organizm != null) {
                    komorka.setBackground(organizm.getKolor());
                    JLabel label = new JLabel(String.valueOf(organizm.getSymbol()));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    komorka.setLayout(new GridBagLayout());
                    komorka.add(label);
                } else {
                    komorka.setBackground(Color.WHITE);
                }
                komorka.revalidate();
                komorka.repaint();
            }
        }
    }

    public void dodajLog(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}