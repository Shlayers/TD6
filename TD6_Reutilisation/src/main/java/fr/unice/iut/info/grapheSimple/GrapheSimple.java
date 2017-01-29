package main.java.fr.unice.iut.info.grapheSimple;

import main.java.fr.unice.iut.info.grapheX.Arc;
import main.java.fr.unice.iut.info.grapheX.Graphe;
import main.java.fr.unice.iut.info.grapheX.Sommet;
import main.java.fr.unice.iut.info.reseauSocial.core.MemberInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 Cette classe a ete necessaire pour gérer les graphes ayant plusieurs arétes entre deux sommets données
 et ayant des arcs dans les deux sens entre deux sommets.
 Ces fonctionnalités n'étaient pas prises en charge par les classes initiales.
 Elle permet egalement de simplifier la comprehension des codes données.
 
 @author blay */
public class GrapheSimple extends Graphe{
    HashMap<String, Sommet> mesSommets = new HashMap<String, Sommet>();
    
    HashMap<Sommet, HashMap<Sommet, ArrayList<Arc<Sommet>>>> aretes;
    
    
    public GrapheSimple ()
    {
        aretes = new HashMap<Sommet, HashMap<Sommet, ArrayList<Arc<Sommet>>>>();
    }
    
    
    public Sommet getSommet (String ident)
    {
        return mesSommets.get(ident);
    }
    
    @Override
    public int taille ()
    {
        return mesSommets.size();
    }
    
    
    @Override
    
    public Graphe copie ()
    {
        //@TODO
        return null;
    }
    
    //n'ajoute le sommet que s'il n'est pas deja dans le graphe.
    @Override
    public void ajouterSommet (Sommet s)
    {
        if(existeSommet(s)) return;
        mesSommets.put(s.identifiant(), s);
        aretes.put(s, new HashMap<Sommet, ArrayList<Arc<Sommet>>>());
    }
    
    @Override
    public boolean existeArc (Sommet s, Sommet t)
    {
        return aretes.get(s).containsKey(t);
    }
    
    
    //A revoir avec la nouvelle version
    //@todo
    private boolean existeSommet (Sommet s)
    {
        return aretes.containsKey(s);
    }
    
    
    public ArrayList<Arc<Sommet>> arcs (Sommet s, Sommet t)
    {
        return aretes.get(s).get(t);
    }
    
    @Override
    public void ajouterArc (Sommet s, Sommet t)
    {
        this.ajouterArc(s, t, 0);
    }
    
    
    @Override
    public void ajouterArc (Arc<Sommet> arc)
    {
        HashMap<Sommet, ArrayList<Arc<Sommet>>> s = aretes.get(arc.origine());
        if(s == null)
        {
            ajouterSommet(arc.origine());
            s = aretes.get(arc.origine());
        }
        if(aretes.get(arc.destination()) == null) ajouterSommet(arc.destination());
        if(s.get(arc.destination()) == null) s.put(arc.destination(), new ArrayList<Arc<Sommet>>());
        s.get(arc.destination()).add(arc);
        
    }
    
    @Override
    public void ajouterArc (Sommet s, Sommet t, int val)
    {
        Arc<Sommet> a = new Arc<Sommet>(s, t, val);
        this.ajouterArc(a);
    }
    
    @Override
    public int valeurArc (Sommet s, Sommet t)
    {
        if(!existeArc(s, t)) throw new Error("Arc inexistant");
        return aretes.get(s).get(t).get(0).valeur();
    }
    
    //RETIRE TOUS LES ARCS VERS T
    @Override
    public void enleverArc (Sommet s, Sommet t)
    {
        if(!existeArc(s, t)) return;
        aretes.get(s).remove(t);
    }
    
    @Override
    public Collection<Sommet> sommets ()
    {
        return mesSommets.values();
    }
    
    @Override
    public Collection<Arc<Sommet>> voisins (Sommet s)
    {
        ArrayList<Arc<Sommet>> voisins = new ArrayList<Arc<Sommet>>();
        HashMap<Sommet, ArrayList<Arc<Sommet>>> arcs = aretes.get(s);
        if(arcs != null)
        {
            for (ArrayList<Arc<Sommet>> av : arcs.values())
            {
                voisins.addAll(av);
            }
        }
        return voisins;
    }
    
    @Override
    public String toString ()
    {
        return "Sommets=" + mesSommets + ";\n arcs=" + toStringArretes(aretes) + "]";
    }
    
    private String toStringArretes (HashMap<Sommet, HashMap<Sommet, ArrayList<Arc<Sommet>>>> aretes2)
    {
        String s = new String();
        for (HashMap<Sommet, ArrayList<Arc<Sommet>>> x : aretes2.values())
        {
            for (ArrayList<Arc<Sommet>> aretes : x.values())
            {
                for (Arc a : aretes)
                {
                    s += "\t" + a.toString() + "\n";
                }
            }
        }
        return s;
    }
    
}
