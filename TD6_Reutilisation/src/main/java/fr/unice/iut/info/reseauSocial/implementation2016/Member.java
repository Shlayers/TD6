package main.java.fr.unice.iut.info.reseauSocial.implementation2016;

import main.java.fr.unice.iut.info.grapheX.Sommet;
import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;


/**
 * Created by LERAT Antoine on 16/12/2016.
 */

public class Member extends Sommet implements MemberInterface {

    private String nom;
    private String description;
    private int age;

    public Member(String nn,int age,String description) {
        super(nn);
        this.nom=nn;
        this.description=description;
        this.age= age;
    }

    public int getAge() {
        return this.age;
    }

    public String getDescription() {
        return this.description;
    }

    public String ident() {
        return this.nom;
    }

    @Override
    public String toString() {
        return "Member{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", age=" + age +
                '}';
    }
}

