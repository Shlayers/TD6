package main.java.fr.unice.iut.info.reseauSocial.implementation2016;


import main.java.fr.unice.iut.info.facebookGhost.FacebookGhostNetwork;
import main.java.fr.unice.iut.info.facebookGhost.RelationEvent;
import main.java.fr.unice.iut.info.facebookGhost.User;
import main.java.fr.unice.iut.info.facebookGhost.UserEvent;
import main.java.fr.unice.iut.info.grapheSimple.GrapheSimple;
import main.java.fr.unice.iut.info.grapheSimple.ParcoursSimple;
import main.java.fr.unice.iut.info.grapheX.Sommet;
import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;
import main.java.fr.unice.iut.info.reseauSocial.core.SocialNetworkInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by Antoine on 16/12/2016.
 */
public class SocialNetwork implements SocialNetworkInterface{

    private GrapheSimple grapheSimple = new GrapheSimple();
    private String nom;

    public SocialNetwork(String Nom){
        nom = Nom;
    }

    public MemberInterface getMember(String identifier){
        Iterator<Sommet> iterator = grapheSimple.sommets().iterator();
        while (iterator.hasNext()) {
            Sommet postit = iterator.next();
            if(postit.identifiant().equals(identifier)){
                return (Member) postit ;
            }
        }
        return null ;
    }

    public Collection<? extends MemberInterface> getMembers(){
        ArrayList<MemberInterface> listeMembre = new ArrayList<>();

        for (Sommet s : grapheSimple.sommets()){
            listeMembre.add((MemberInterface) s);
        }
        return listeMembre;
    }

    public MemberInterface addMember(String identifier, int age, String description){
        Member m = new Member(identifier, age, description);
        grapheSimple.ajouterSommet(m);
        return m;
    }

    public void relate(int force, MemberInterface member, MemberInterface friend) {
        if (grapheSimple.existeArc((Member)member,(Member) friend))grapheSimple.enleverArc((Member)member,(Member) friend);
        grapheSimple.ajouterArc((Member)member,(Member)friend,force);
        grapheSimple.ajouterArc((Member)friend,(Member)member,force);
    }

    @Override
    public Collection<? extends MemberInterface> relateToRank(MemberInterface member, int rank) {
        ParcoursSimple parcours = new ParcoursSimple(grapheSimple);
        ArrayList<MemberInterface> listeMembre = new ArrayList<>();
        for (Sommet s : parcours.voisinsAuRang((Sommet) member, rank)){
            listeMembre.add((MemberInterface) s);
        }

        return listeMembre;
    }

    public int distance(MemberInterface member1, MemberInterface member2){
        ParcoursSimple parcoursSimple = new ParcoursSimple(grapheSimple);
        Sommet s1 = grapheSimple.getSommet(member1.ident());
        Sommet s2 = grapheSimple.getSommet(member2.ident());

        return (parcoursSimple.cheminLePlusCourt(s1, s2)).distance();
    }

    public void addOtherNetwork(Observable o){
        o.addObserver(this);
        if (o instanceof FacebookGhostNetwork) {
            FacebookGhostNetwork fg = (FacebookGhostNetwork) o;
            for (User user : fg.getAllUsers()) {
                MemberInterface member = addUser(user);
                for (User family : user.getFamily()) {
                    relate(1, member, addUser(family));
                }
                for (User friend : user.getFriends()) {
                    relate(2, member, addUser(friend));
                }
            }
        }
    }

    private MemberInterface addUser(User user) {
        AdaptedMember member = new AdaptedMember(user);
        grapheSimple.ajouterSommet(member);
        return member;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof UserEvent) {
            UserEvent e = (UserEvent) o;
            addUser(e.getAddedUser());
        } else if (o instanceof RelationEvent) {
            RelationEvent r = (RelationEvent) o;
            relate(r.getNature().equals("Family") ? 1 : 2, new AdaptedMember(r.getU1()), new AdaptedMember(r.getU2()));
        }
    }
}
