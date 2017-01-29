package test.java.fr.unice.iut.info;

import static org.junit.Assert.*;
import java.util.Collection;

import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;
import main.java.fr.unice.iut.info.reseauSocial.implementation2016.SocialNetwork;
import main.java.fr.unice.iut.info.reseauSocial.core.SocialNetworkInterface;
import org.junit.Before;
import org.junit.Test;

// This class corresponds to my implementation.
// You must substitute your own class.

public class PartScenario {

    //Reference to modify
    SocialNetworkInterface iutRS;

    @Before
    public void setUp() throws Exception {
        iutRS = new SocialNetwork("IUT");
    }
    @Test
    public void testInit() {
        assertTrue(iutRS != null);
    }

    /*
        Serie of simple tests
     */
    private MemberInterface buildGeek(String name, int age, String description) {
        iutRS.addMember(name, age, description);
        return iutRS.getMember(name);
    }

    @Test
    public void testAddAndGetMemberSimple() {
        MemberInterface m_geek01 = buildGeek("geek01", 18, "S1T, le plus beau");
        assertEquals("geek01", m_geek01.ident());
        assertEquals(18, m_geek01.getAge());
        assertEquals("S1T, le plus beau", m_geek01.getDescription());
    }
    @Test
    public void testAddAndGetTwoMembers() {
        MemberInterface m_geek01 = buildGeek("geek01", 18, "S1T, le plus beau");
        MemberInterface m_geek02 = buildGeek("ivana",Integer.MIN_VALUE, "Princesse Geek");
        assertEquals("geek01", m_geek01.ident());
        assertEquals("ivana", m_geek02.ident());
    }

    @Test
    public void testGetMembers() {
        iutRS.addMember("geek01", 18, "S1T, le plus beau");
        iutRS.addMember("ivana",Integer.MIN_VALUE, "Princesse Geek");
        Collection<? extends MemberInterface> membres = iutRS.getMembers();
        assertEquals("taille du reseau est bien de 2 membres", 2,membres.size());
    }


    //------------ Construction des relations ------- //

    private void buildAsterixNetwork() {
        MemberInterface asterix = iutRS.addMember("Asterix",0,"Asterix, le plus intelligent");
        MemberInterface falbala = iutRS.addMember("Falbala",0,"falbala, la plus jolie");
        MemberInterface obelix = iutRS.addMember("Obelix",0,"Obelix, le plus intelligent");
        MemberInterface panoramix =iutRS.addMember("Panoramix",0,"Panoramix, le plus magique");
        MemberInterface abraracourcix = iutRS.addMember("Abraracourcix",0,"Abraracourcix, chef du village");

        //les relations crées sont bidirectionnelles
        //asterix <-1-> obelix
        iutRS.relate(1, asterix, obelix);
        iutRS.relate(1, asterix, panoramix);
        //ne fais "rien"
        iutRS.relate(1, obelix, asterix);
        iutRS.relate(2, obelix, falbala);
        iutRS.relate(3, obelix, panoramix);
        iutRS.relate(4, falbala, obelix);
        iutRS.relate(4, falbala, asterix);
        iutRS.relate(4, panoramix,abraracourcix);
        //au final vous devez avoir
        //asterix <-1-> panoramix <-4-> abraracourcix
        //asterix <-4-> falbala
        //asterix <-1-> obelix <-4-> falbala
        //obelix <-3-> panoramix
    }

    @Test
    public void testRelationsAtRankOne() {

        buildAsterixNetwork();
        MemberInterface asterix = iutRS.getMember("Asterix");
        MemberInterface obelix = iutRS.getMember("Obelix");
        MemberInterface panoramix = iutRS.getMember("Panoramix");
        MemberInterface falbala = iutRS.getMember("Falbala");


        //tests au rang 1
        Collection<? extends MemberInterface> membresAmis = iutRS.relateToRank(asterix, 1);

        assertEquals("taille des amis d'Asterix au rang 1 : Obelix, Panoramix et Falbala", 3, membresAmis.size());
        assertTrue("Asterix est bien ami d'Obelix", membresAmis.contains(obelix));
        assertTrue("Asterix est bien ami de Panoramix", membresAmis.contains(panoramix));
        
        membresAmis = iutRS.relateToRank(panoramix, 1);
        assertEquals("taille des amis de Panoramix au rang 1 : Asterix, Abraracourcix et Obelix", 3, membresAmis.size());
        assertTrue("Panoramix est bien ami Asterix", membresAmis.contains(obelix));
        assertFalse("Panoramix n'est pas ami avec Falbala", membresAmis.contains(falbala));
        
        membresAmis = iutRS.relateToRank(obelix, 1);
        assertEquals("taille des amis d'Obelix au rang 1 : Asterix, Falbala et Panoramix", 3, membresAmis.size());
        assertTrue("Obelix est bien ami avec Panoramix", membresAmis.contains(panoramix));
        assertFalse("Obelix n'est pas ami avec Abraracourcix", membresAmis.contains(iutRS.getMember("Abraracourcix")));
    }

    @Test
    public void testRelationsAtRankTwo() {

        buildAsterixNetwork();
        MemberInterface asterix = iutRS.getMember("Asterix");
        MemberInterface obelix = iutRS.getMember("Obelix");
        MemberInterface panoramix = iutRS.getMember("Panoramix");
        MemberInterface falbala = iutRS.getMember("Falbala");
        MemberInterface abraracourcix = iutRS.getMember("Abraracourcix");

        //tests au rang 2
        Collection<? extends MemberInterface> membresAmis = iutRS.relateToRank(asterix, 2);

        System.out.println("Amis de Asterix au rang 2" + membresAmis);
        assertEquals("taille des amis d'Asterix au rang 2 : Abraracourcix, les autres le sont deja au rang 1", 1, membresAmis.size());
        assertTrue("Asterix est bien ami de Abraracourcix au rang 2", membresAmis.contains(abraracourcix));
        
        membresAmis = iutRS.relateToRank(panoramix, 2);
        iutRS.relateToRank(asterix,3);
        assertEquals("taille des amis de Panoramix au rang 2 : Falbala", 1, membresAmis.size());
        assertTrue("Panoramix est bien ami de Falbala au rang 2", membresAmis.contains(falbala));
        
        membresAmis = iutRS.relateToRank(obelix, 2);
        assertEquals("taille des amis Obelix au rang 2 : Abraracourcix", 1, membresAmis.size());
        assertTrue("Obelix est bien ami de Abraracourcix au rang 2", membresAmis.contains(abraracourcix));
        
        membresAmis = iutRS.relateToRank(falbala, 3);
        assertEquals("taille des amis Falbala au rang 3 : Abraracourcix", 1, membresAmis.size());
        assertTrue("Abraracourcix est bien ami de falbala au rang 3", membresAmis.contains(abraracourcix));

    }

   @Test
    public void testDistances() {
        buildAsterixNetwork();
        MemberInterface asterix = iutRS.getMember("Asterix");
        MemberInterface obelix = iutRS.getMember("Obelix");
        MemberInterface falbala = iutRS.getMember("Falbala");
        MemberInterface abraracourcix = iutRS.getMember("Abraracourcix");
        MemberInterface panoramix = iutRS.getMember("Panoramix");

        //Calcul des distances
        assertEquals("distance entre asterix et obelix", 1, iutRS.distance(asterix, obelix));
        assertEquals("distance entre asterix et falbala", 4, iutRS.distance(asterix, falbala));
        assertEquals("distance entre falbala et asterix", 4, iutRS.distance(falbala, asterix));
        assertEquals("distance entre falbala et Abraracourcix", 9, iutRS.distance(falbala, abraracourcix));
        assertEquals("distance entre falbala et Panoramix", 5, iutRS.distance(falbala, panoramix));      
        assertEquals("distance entre asterix et Abraracourcix", 5, iutRS.distance(asterix, abraracourcix));
        assertEquals("distance entre obelix et Abraracourcix", 6, iutRS.distance(obelix, abraracourcix));
        assertEquals("distance entre asterix et Panoramix", 1, iutRS.distance(asterix, panoramix));
        assertEquals("distance entre obelix et Panoramix", 2, iutRS.distance(obelix, panoramix));
    }

}
