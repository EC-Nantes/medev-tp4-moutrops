/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.centrale.projet.jeudedame;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author vadimdigonnet
 */
public class PionTest {

    private Plateau plateau;

    @BeforeEach
    public void setUp() {
        plateau = new Plateau();
        plateau.vider(); 
    }

    @Test
    public void testDeplaceCaseVideDansLePlateau() {
        Pion pion = new Pion(new Point2D(3, 3), "blanc", plateau);
        plateau.getMaListePionBlanc().add(pion);

        boolean res = pion.deplace(1, 1);

        assertTrue(res, "Le pion devrait se déplacer sur une case vide");
        assertEquals(4, pion.getPosition().getX());
        assertEquals(4, pion.getPosition().getY());
    }

    @Test
    public void testDeplaceCaseHorsPlateau() {
        Pion pion = new Pion(new Point2D(0, 0), "blanc", plateau);
        plateau.getMaListePionBlanc().add(pion);

        boolean res = pion.deplace(-1, -1);

        assertFalse(res, "Déplacement hors plateau impossible");
        assertEquals(0, pion.getPosition().getX());
        assertEquals(0, pion.getPosition().getY());
    }

    @Test
    public void testDeplaceCaseOccupee() {
        Pion pion = new Pion(new Point2D(3, 3), "blanc", plateau);
        Pion autre = new Pion(new Point2D(4, 4), "noir", plateau);

        plateau.getMaListePionBlanc().add(pion);
        plateau.getMaListePionNoir().add(autre);

        boolean res = pion.deplace(1, 1);

        assertFalse(res, "Le pion ne doit pas se déplacer sur une case occupée");
        assertEquals(3, pion.getPosition().getX());
        assertEquals(3, pion.getPosition().getY());
    }


    @Test
    public void testDoitMangerImpossibleSiCaseDerriereOccupee() {
        // Pion blanc
        Pion pion = new Pion(new Point2D(4, 4), "blanc", plateau);
        plateau.getMaListePionBlanc().add(pion);

        // Pion noir adjacent
        Pion pionNoir = new Pion(new Point2D(5, 5), "noir", plateau);
        plateau.getMaListePionNoir().add(pionNoir);

        // Case derrière occupée (6,6)
        Pion blocage = new Pion(new Point2D(6, 6), "blanc", plateau);
        plateau.getMaListePionBlanc().add(blocage);

        List<Pion> result = pion.doitManger();

        assertTrue(result.isEmpty(), "Le pion ne doit pas pouvoir manger si la case derrière est occupée");
    }
}