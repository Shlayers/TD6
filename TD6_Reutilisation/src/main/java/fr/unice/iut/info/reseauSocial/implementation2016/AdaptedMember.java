package main.java.fr.unice.iut.info.reseauSocial.implementation2016;

import main.java.fr.unice.iut.info.facebookGhost.User;
import main.java.fr.unice.iut.info.grapheX.Sommet;
import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;

/**
 * Created by Antoine on 27/01/2017.
 *
 */
public class AdaptedMember extends Sommet implements MemberInterface {

    private User user;

    public AdaptedMember(User user) {
        super(user.getId());
        this.user = user;
    }

    @Override
    public int getAge() {
        return user.getAgeRange().getAge();
    }

    @Override
    public String getDescription() {
        return user.myProfil();
    }

    @Override
    public String ident() {
        return user.getId();
    }
}
