package test.java.fr.unice.iut.info;

import main.java.fr.unice.iut.info.facebookGhost.FacebookGhostNetwork;
import main.java.fr.unice.iut.info.facebookGhost.User;
import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;
import main.java.fr.unice.iut.info.reseauSocial.implementation2016.SocialNetwork;
import main.java.fr.unice.iut.info.reseauSocial.core.SocialNetworkInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;


// This class corresponds to my implementation.
// You must substitute your own class.


public class MainScenario
{

    //Reference to modify
    SocialNetworkInterface iutRS;

    @Before
    public void setUp () throws Exception
    {
        iutRS = new SocialNetwork("IUT");
    }

    @Test
    public void testInit ()
    {
        assertTrue(iutRS != null);
    }

    /*
        Serie of simple tests
     */
    private MemberInterface buildGeek (String name, int age, String description)
    {
        iutRS.addMember(name, age, description);
        return iutRS.getMember(name);
    }

    @Test
    public void testAddAndGetMemberSimple ()
    {
        MemberInterface m_geek01 = buildGeek("geek01", 18, "S1T, le plus beau");
        assertEquals("geek01", m_geek01.ident());
        assertEquals(18, m_geek01.getAge());
        assertEquals("S1T, le plus beau", m_geek01.getDescription());
    }

    @Test
    public void testAddAndGetTwoMembers ()
    {
        MemberInterface m_geek01 = buildGeek("geek01", 18, "S1T, le plus beau");
        MemberInterface m_geek02 = buildGeek("ivana", Integer.MIN_VALUE, "Princesse Geek");
        assertEquals("geek01", m_geek01.ident());
        assertEquals("ivana", m_geek02.ident());
    }

    @Test
    public void testGetMembers ()
    {
        iutRS.addMember("geek01", 18, "S1T, le plus beau");
        iutRS.addMember("ivana", Integer.MIN_VALUE, "Princesse Geek");
        Collection<? extends MemberInterface> membres = iutRS.getMembers();
        assertEquals("taille du reseau est bien de 2 membres", 2, membres.size());
    }


    //------------ Construction des relations ------- //

    private void buildAsterixNetwork ()
    {
        MemberInterface asterix = iutRS.addMember("Asterix", 0, "Asterix, le plus intelligent");
        MemberInterface falbala = iutRS.addMember("Falbala", 0, "falbala, la plus jolie");
        MemberInterface obelix = iutRS.addMember("Obelix", 0, "Obelix, le plus intelligent");
        MemberInterface panoramix = iutRS.addMember("Panoramix", 0, "Panoramix, le plus magique");
        MemberInterface abraracourcix = iutRS.addMember("Abraracourcix", 0, "Abraracourcix, chef du village");

        iutRS.relate(1, asterix, obelix);
        iutRS.relate(1, panoramix, asterix);
        iutRS.relate(2, obelix, falbala);
        iutRS.relate(4, panoramix, abraracourcix);
    }

    @Test
    public void testRelations ()
    {

        buildAsterixNetwork();
        MemberInterface asterix = iutRS.getMember("Asterix");
        MemberInterface obelix = iutRS.getMember("Obelix");
        MemberInterface panoramix = iutRS.getMember("Panoramix");
        MemberInterface falbala = iutRS.getMember("Falbala");


        //tests au rang 1
        Collection<? extends MemberInterface> membresAmis = iutRS.relateToRank(asterix, 1);
        //System.out.println("Amis de Asterix" + membresAmis);
        assertEquals("taille des amis d'Asterix au rang 1 : Obelix et Panoramix", 2, membresAmis.size());
        assertTrue("Asterix est bien ami d'Obelix", membresAmis.contains(obelix));
        assertTrue("Asterix est bien ami de Panoramix", membresAmis.contains(panoramix));
        membresAmis = iutRS.relateToRank(panoramix, 1);
        //System.out.println("Amis de Panoramix" + membresAmis);
        assertEquals("taille des amis de Panoramix au rang 1 : Asterix", 1, membresAmis.size());
        membresAmis = iutRS.relateToRank(panoramix, 4);
        assertEquals("taille des amis de Panoramix au rang 4 : Abracourcix", 1, membresAmis.size());

        membresAmis = iutRS.relateToRank(falbala, 2);
        assertEquals("taille des amis de Falbala au rang 2 : Obelix ", 1, membresAmis.size());
    }

    @Test
    public void testDistances ()
    {
        buildAsterixNetwork();
        MemberInterface asterix = iutRS.getMember("Asterix");
        MemberInterface obelix = iutRS.getMember("Obelix");
        MemberInterface falbala = iutRS.getMember("Falbala");
        MemberInterface abraracourcix = iutRS.getMember("Abraracourcix");

        //Calcul des distances
        int distance = iutRS.distance(asterix, obelix);
        assertEquals("distance entre asterix et obelix", 1, distance);
        distance = iutRS.distance(asterix, falbala);
        assertEquals("distance entre asterix et falbala", 3, distance);
        distance = iutRS.distance(falbala, asterix);
        assertEquals("distance entre falbala et asterix", 3, distance);
        distance = iutRS.distance(falbala, abraracourcix);
        assertEquals("distance entre falbala et Abraracourcix", 8, distance);
        distance = iutRS.distance(abraracourcix, falbala);
        assertEquals("distance entre Abraracourcix et falbala", 8, distance);
    }


    //------------ Connexions e FG ------- //
    private FacebookGhostNetwork getFacebookGhostNetwork ()
    {
        FacebookGhostNetwork fg = new FacebookGhostNetwork();
        buildGreekNetwork(fg);
        //System.out.println(fg);
        iutRS.addOtherNetwork(fg);
        return fg;
    }

    private void buildGreekNetwork (FacebookGhostNetwork fg)
    {
        User zeus = fg.addUser("Zeus", "le dieu ...");
        User alcmene = fg.addUser("Alcmene", "la mere d'hercule");
        User hercule = fg.addUser("Hercule", "le hero");
        User admete = fg.addUser("Admete", "l'ami");
        User hera = fg.addUser("Hera", "la femme de zeus");
        fg.addFamilyRelation(hercule, zeus);
        fg.addFamilyRelation(hercule, alcmene);
        fg.addFamilyRelation(hera, zeus);
        fg.addFriendRelation(hercule, admete);
    }

    private MemberInterface buildGreekmythologieNetwork (FacebookGhostNetwork fg)
    {
        User admete = fg.addUser("Admete", "l'ami");
        assertEquals("description de Ademete",
                     iutRS.getMember("Admete").getDescription(),
                     fg.getUser("Admete").myProfil());
        return iutRS.getMember("Admete");
    }


    @Test
    public void testConnexionToFGInitialisation ()
    {
        FacebookGhostNetwork fg = getFacebookGhostNetwork();
        MemberInterface admete = buildGreekmythologieNetwork(fg);
        Collection<? extends MemberInterface> membres = iutRS.relateToRank(admete, 1);
        assertEquals("taille de la famille d'ADMETE : personne", 0, membres.size());

        MemberInterface hercule = iutRS.getMember("Hercule");
        membres = iutRS.relateToRank(hercule, 1);
        assertEquals("taille de la famille Hercule: Zeus et Alcmene", 2, membres.size());

        MemberInterface hera = iutRS.getMember("Hera");
        membres = iutRS.relateToRank(hera, 1);
        assertEquals("taille de la famille Hera: Zeus", 1, membres.size());

        MemberInterface alcmene = iutRS.getMember("Alcmene");
        membres = iutRS.relateToRank(alcmene, 1);
        assertEquals("taille de la famille Alcmene: Hercule", 1, membres.size());

    }


    @Test
    public void testConnexionToFGWithlinks ()
    {
        FacebookGhostNetwork fg = getFacebookGhostNetwork();

        MemberInterface admete = buildGreekmythologieNetwork(fg);
        MemberInterface zeus = iutRS.getMember("Zeus");
        MemberInterface hera = iutRS.getMember("Hera");
        MemberInterface hercule = iutRS.getMember("Hercule");


        //Test relations
        Collection<? extends MemberInterface> membresAmis = iutRS.relateToRank(admete, 2);
        assertEquals("taille des amis de Admete: Hercule", 1, membresAmis.size());

        membresAmis = iutRS.relateToRank(hera, 1);
        assertEquals("taille de la famille Hera : Zeus", 1, membresAmis.size());

        membresAmis = iutRS.relateToRank(hercule, 1);
        assertEquals("taille de la famille d'Hercule : Zeus et Alcmene: ", 2, membresAmis.size());
        assertTrue("Zeus est bien de la famille de Hercule", membresAmis.contains(zeus));


        int distance = iutRS.distance(hercule, zeus);
        assertEquals("distance entre hercule et zeus", 1, distance);
        distance = iutRS.distance(hercule, admete);
        assertEquals("distance entre hercule et admete", 2, distance);

        membresAmis = iutRS.relateToRank(hercule, 2);
        assertEquals("taille des amis d'Hercule: Admete", 1, membresAmis.size());
        assertTrue("Admete est bien ami de Hercule", membresAmis.contains(admete));

        distance = iutRS.distance(admete, hercule);
        assertEquals("distance entre hercule et admete", 2, distance);

        distance = iutRS.distance(admete, zeus);
        assertEquals("distance entre Zeus et admete", 3, distance);
    }


    //------------ Observations  : Connexions e FG ------- //
    @Test
    public void testConnexionToFGAndObserver ()
    {
        FacebookGhostNetwork fg = new FacebookGhostNetwork();
        buildGreekNetwork(fg);

        MemberInterface zeus = iutRS.getMember("Zeus");
        MemberInterface hera = iutRS.getMember("Hera");
        MemberInterface hercule = iutRS.getMember("Hercule");

        assertNull(zeus);
        assertNull(hera);
        assertNull(hercule);

        iutRS.addOtherNetwork(fg);

        zeus = iutRS.getMember("Zeus");
        hera = iutRS.getMember("Hera");
        hercule = iutRS.getMember("Hercule");

        assertNotNull(zeus);
        assertNotNull(hera);
        assertNotNull(hercule);

        fg.addFamilyRelation("Zeus", "Hera");

        Collection<? extends MemberInterface> membresAmis = iutRS.relateToRank(hera, 1);
        assertEquals("taille de la famille d'Hera : Zeus", 1, membresAmis.size());
        membresAmis = iutRS.relateToRank(hercule, 1);
        assertEquals("taille de la famille d'Hercule : Zeus et Alcmene", 2, membresAmis.size());
        membresAmis = iutRS.relateToRank(hercule, 2);
        assertEquals("taille des amis d'Hercule : Adamete", 1, membresAmis.size());

        //Non demande
     /*   MemberInterface asterix = iutRS.addMember("Asterix",0,"Asterix, le plus intelligent");
        fg.addUser("Asterix", "Albert Uderzo, Asterix est le seul anti-heros qui ait jamais autant collectionne les succes et les exploits.");
        assertEquals("description de Asterix", asterix.getDescription(), fg.getUser("Asterix").myProfil());
     */

    }
}
