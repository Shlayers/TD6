package main.java.fr.unice.iut.info.grapheX;

/**
 Classe de sommets, toutes les propri�t�s sont h�rit�es de Identifiable.
 
 @author FMorain (morain@lix.polytechnique.fr)
 @author PChassignet (chassign@lix.polytechnique.fr)
 @version 2007.03.21 */

public class Sommet extends Identifiable
{
    
    public Sommet (String nn)
    {
        super(nn);
    }
    
    @Override
    public String toString ()
    {
        return identifiant();
    }
    
    
}
