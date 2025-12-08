package org.centrale.projet.jeudedame;

import java.util.List;
import java.util.Scanner;

public class MainJeuDeDame {

    public static void main(String[] args) {
        JeuDeDame jeu = new JeuDeDame();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Jeu de Dames ===");
        System.out.println("Plateau 10x10, coordonnées de 0 à 9.");
        System.out.println("Saisis les coups sous la forme : xOrig yOrig xDest yDest");
        System.out.println("Tape 'q' pour quitter.\n");

        while (!jeu.estFinDePartie()) {
            Plateau plateau = jeu.getPlateau();

            // Afficher le plateau
            afficherPlateau(plateau);

            // Infos joueur courant
            String joueur = jeu.getJoueurCourant();
            System.out.println("C'est au tour des " + joueur + "s.");

            // Pions obligés de manger ?
            List<Pion> obliges = jeu.getPionsQuiDoiventManger();
            if (!obliges.isEmpty()) {
                System.out.println("Attention : au moins un pion doit manger !");
            }

            System.out.print("Entrez un coup (xOrig yOrig xDest yDest) ou 'q' pour quitter : ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("q")) {
                System.out.println("Partie interrompue.");
                break;
            }

            String[] parts = line.split("\\s+");
            if (parts.length != 4) {
                System.out.println("Format invalide. Exemple: 2 3 3 4\n");
                continue;
            }

            try {
                int xOrig = Integer.parseInt(parts[0]);
                int yOrig = Integer.parseInt(parts[1]);
                int xDest = Integer.parseInt(parts[2]);
                int yDest = Integer.parseInt(parts[3]);

                Point2D origine = new Point2D(xOrig, yOrig);
                Point2D destination = new Point2D(xDest, yDest);
                
                boolean ok = jeu.jouerCoup(origine, destination);
                if (!ok) {
                    System.out.println("Coup illégal, réessaie.\n");
                }

            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer des nombres entiers.\n");
            }
        }

        if (jeu.estFinDePartie()) {
            System.out.println("=== Fin de partie ===");
            System.out.println("Les " 
                + (jeu.getPlateau().getMaListePionBlanc().isEmpty() ? "noirs" : "blancs")
                + " ont gagné !");
        }

        sc.close();
    }

    /**
     * Affiche le plateau en console.
     * .  = case vide
     * b  = pion blanc
     * n  = pion noir
     */
    private static void afficherPlateau(Plateau plateau) {
        // y du haut (9) vers le bas (0) pour que ça ressemble à un vrai plateau
        System.out.println();
        System.out.println("   0 1 2 3 4 5 6 7 8 9");
        System.out.println("  ---------------------");

        for (int y = 9; y >= 0; y--) {
            System.out.print(y + " | ");
            for (int x = 0; x < 10; x++) {
                char c = '.';

                // chercher un pion blanc ou noir à (x,y)
                boolean found = false;
                for (Pion p : plateau.getMaListePionBlanc()) {
                    if (p.getPosition().getX() == x && p.getPosition().getY() == y) {
                        c = 'b';
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (Pion p : plateau.getMaListePionNoir()) {
                        if (p.getPosition().getX() == x && p.getPosition().getY() == y) {
                            c = 'n';
                            break;
                        }
                    }
                }

                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
