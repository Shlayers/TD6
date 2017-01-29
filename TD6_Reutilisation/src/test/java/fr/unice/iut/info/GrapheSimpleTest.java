package test.java.fr.unice.iut.info;

import main.java.fr.unice.iut.info.grapheSimple.Chemin;
import main.java.fr.unice.iut.info.grapheSimple.GrapheSimple;
import main.java.fr.unice.iut.info.grapheSimple.ParcoursSimple;
import main.java.fr.unice.iut.info.grapheX.Arc;
import main.java.fr.unice.iut.info.grapheX.Sommet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

//@todo to be improved!
public class GrapheSimpleTest extends GrapheSimple
{
    
    
    GrapheSimple graphe = new GrapheSimple();
    Sommet s1 = new Sommet("S1");
    Sommet s2 = new Sommet("S2");
    Sommet s3 = new Sommet("S3");
    Sommet s4 = new Sommet("S4");
    int distance_s1_s2 = 1;
    int distance_s1_s3 = 2;
    int distance_s2_s1 = 2;
    int distance_s2_s3 = 1;
    int distance_s3_s4 = 2;
    int distance_s4_s1 = 10;
    
    
    ParcoursSimple parcours;
    
    /*
     * s1 -1-> s2 -1-> s3
     * s2-2-> s1
     * s3 -2-> s4
     * s4 -10-> s1
     *
     */
    @Before
    public void setUp () throws Exception
    {
        graphe = new GrapheSimple();
        
    }
    
    public void initGlobalGraph ()
    {
        graphe = new GrapheSimple();
        graphe.ajouterSommet(s1);
        graphe.ajouterSommet(s2);
        graphe.ajouterSommet(s3);
        graphe.ajouterSommet(s4);
        graphe.ajouterArc(s1, s2, distance_s1_s2);
        graphe.ajouterArc(s1, s2, distance_s1_s2 * 2);
        graphe.ajouterArc(s1, s3, distance_s1_s3);
        graphe.ajouterArc(s2, s1, distance_s2_s1);
        graphe.ajouterArc(s2, s3, distance_s2_s3);
        graphe.ajouterArc(s3, s4, distance_s3_s4);
        graphe.ajouterArc(s4, s1, distance_s4_s1);
        parcours = new ParcoursSimple(graphe);
    }
    
    @After
    public void tearDown () throws Exception
    {
    }
    
    
    @Test
    public void testTailleInitiale ()
    {
        assertEquals(0, graphe.taille());
    }
    
    @Test
    public void testAjouterSommetInitial ()
    {
        graphe.ajouterSommet(s1);
        assertEquals(1, graphe.taille());
        assertEquals(s1, graphe.getSommet("S1"));
    }
    
    @Test
    public void testAjouterArcInitial ()
    {
        graphe.ajouterSommet(s1);
        graphe.ajouterSommet(s2);
        graphe.ajouterSommet(s3);
        graphe.ajouterArc(s1, s2, distance_s1_s2);
        assertEquals(3, graphe.taille());
        assertTrue(graphe.existeArc(s1, s2));
        assertFalse(graphe.existeArc(s1, s3));
        ArrayList<Arc<Sommet>> arcs = graphe.arcs(s1, s2);
        assertEquals(1, arcs.size());
    }
    
    
    @Test
    public void testVoisinsSommet ()
    {
        initGlobalGraph();
        Collection<Arc<Sommet>> voisins = graphe.voisins(s1);
        assertEquals(3, voisins.size());
        //System.out.println("voisins de s1 :" + voisins);
        voisins = graphe.voisins(s2);
        assertEquals("voisins de s2 : " + voisins, 2, voisins.size());
        //System.out.println("voisins de s2 : " + voisins);
    }
    
    @Test
    public void testArcs ()
    {
        initGlobalGraph();
        ArrayList<Arc<Sommet>> arcs = graphe.arcs(s1, s2);
        //System.out.println("de s1 a s2 : " + arcs);
        assertEquals(2, arcs.size());
        arcs = graphe.arcs(s1, s3);
        //System.out.println("de s1 a s3 : " + arcs);
        assertEquals(1, arcs.size());
        arcs = graphe.arcs(s1, s4);
        //System.out.println("de s1 a s4 : " + arcs);
        assertTrue(arcs == null);
        arcs = graphe.arcs(s4, s1);
        //System.out.println("de s4 a s1 : " + arcs);
        assertEquals(1, arcs.size());
        
    }
    
    @Test
    public void testParcoursCheminsDe ()
    {
        initGlobalGraph();
        ArrayList<Chemin> chemins = parcours.chemins(s1);
        assertEquals(3, chemins.size());
        System.out.println("Chemins a partir de s1: " + chemins);
        Chemin chemin = chemins.get(0);
        chemins = parcours.chemins(s2);
        System.out.println("Chemins a partir de s2: " + chemins);
        assertEquals(2, chemins.size());
        chemins = parcours.chemins(s3);
        System.out.println("Chemins a partir de s3: " + chemins);
        assertEquals(2, chemins.size());
        chemins = parcours.chemins(s4);
        assertEquals(3, chemins.size());
        System.out.println("Chemins a partir de s4: " + chemins);
    }
    
    @Test
    public void testParcoursCheminsDeA ()
    {
        initGlobalGraph();
        ArrayList<Chemin> chemins = parcours.chemins(s1, s2);
        System.out.println("Chemins de s1 a s2: " + chemins);
        assertEquals(2, chemins.size());
        
        chemins = parcours.chemins(s1, s3);
        System.out.println("Chemins de s1 a s3: " + chemins);
        assertEquals("Chemins de s1 a s3:", 3, chemins.size());
        chemins = parcours.chemins(s1, s4);
        System.out.println("Chemins  de s1 a s4: " + chemins);
        assertEquals(3, chemins.size());
        chemins = parcours.chemins(s2, s3);
        System.out.println("Chemins de s2 a s3: " + chemins);
        assertEquals(2, chemins.size());
        chemins = parcours.chemins(s4, s1);
        System.out.println("Chemins  de s4 a s1: " + chemins);
        assertEquals("Chemins  de s4 a s1: ", 1, chemins.size());
        
    }
    
    @Test
    public void testCheminLePlusCourt ()
    {
        initGlobalGraph();
        Chemin chemin = parcours.cheminLePlusCourt(s1, s2);
        assertEquals(distance_s1_s2, chemin.distance());
        chemin = parcours.cheminLePlusCourt(s1, s3);
        assertEquals(distance_s1_s3, chemin.distance());
        chemin = parcours.cheminLePlusCourt(s1, s4);
        assertEquals(4, chemin.distance());
        chemin = parcours.cheminLePlusCourt(s2, s3);
        assertEquals(distance_s2_s3, chemin.distance());
        chemin = parcours.cheminLePlusCourt(s4, s1);
        assertEquals(distance_s4_s1, chemin.distance());
    }
    
    @Test
    public void testVoisinsDeRangs ()
    {
        initGlobalGraph();
        Set<Sommet> voisins = parcours.voisinsAuRang(s1, 1);
        System.out.println("voisins s1 au rang 1: " + voisins);
        assertEquals(2, voisins.size());
        assertTrue(voisins.contains(s2));
        assertTrue(voisins.contains(s3));
        voisins = parcours.voisinsAuRang(s1, 2);
        System.out.println("voisins s1 au rang 2: " + voisins);
        assertEquals(1, voisins.size());
        assertTrue(voisins.contains(s4));
        voisins = parcours.voisinsAuRang(s2, 1);
        System.out.println("voisins s2 au rang 1: " + voisins);
        assertEquals(2, voisins.size());
        assertTrue(voisins.contains(s1));
        assertTrue(voisins.contains(s3));
        voisins = parcours.voisinsAuRang(s2, 2);
        System.out.println("voisins s2 au rang 2: " + voisins);
        assertEquals(1, voisins.size());
        assertTrue(voisins.contains(s4));
        voisins = parcours.voisinsAuRang(s4, 2);
        System.out.println("voisins s4 au rang 2: " + voisins);
        assertEquals(2, voisins.size());
        assertTrue(voisins.contains(s2));
        assertTrue(voisins.contains(s3));
    }
    
    
    @Test
    public void testExtraireChemin ()
    {
        Chemin cheminComplet = new Chemin();
        cheminComplet.add(new Arc(s1, s2));
        cheminComplet.add(new Arc(s2, s3));
        cheminComplet.add(new Arc(s3, s4));
        System.out.println("chemin de s1 a s4 " + cheminComplet);
        Chemin c = cheminComplet.extraireChemin(s1, s4);
        System.out.println("chemin de s1 a s4 apres extraction " + c);
        assertEquals(3, c.getArcs().size());
        assertEquals(s1, c.getArcs().get(0).origine());
        assertEquals(s4, c.getArcs().get(2).destination());
        
        c = cheminComplet.extraireChemin(s1, s3);
        System.out.println("chemin de s1 a s3 apres extraction " + c);
        assertEquals(2, c.getArcs().size());
        assertEquals(s1, c.getArcs().get(0).origine());
        assertEquals(s3, c.getArcs().get(1).destination());
        
        c = cheminComplet.extraireChemin(s2, s3);
        System.out.println("chemin de s2 a s3 apres extraction " + c);
        assertEquals(1, c.getArcs().size());
        assertEquals(s2, c.getArcs().get(0).origine());
        assertEquals(s3, c.getArcs().get(0).destination());
        
        
        c = cheminComplet.extraireChemin(s2, s4);
        System.out.println("chemin de s2 a s4 apres extraction " + c);
        assertEquals(2, c.getArcs().size());
        assertEquals(s2, c.getArcs().get(0).origine());
        assertEquals(s4, c.getArcs().get(1).destination());
        
    }
    
    
}
