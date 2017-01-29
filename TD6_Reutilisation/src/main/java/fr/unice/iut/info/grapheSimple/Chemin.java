package main.java.fr.unice.iut.info.grapheSimple;

import main.java.fr.unice.iut.info.grapheX.Arc;
import main.java.fr.unice.iut.info.grapheX.Sommet;

import java.util.ArrayList;
import java.util.Collection;

/**
 La classe Chemin définit un chemin comme un ensemble d'arcs et maintient pour chaque chemin
 la somme des valeurs des arcs
 
 @author blay */
public class Chemin implements Comparable<Chemin>
{
    
    //On pourrait éviter cet attribut en calculant la distance à la demande.
    int distance = 0;
    
    ArrayList<Arc> paths = new ArrayList<Arc>();
    
    
    public Chemin ()
    {
        paths = new ArrayList<Arc>();
    }
    
    public Chemin (ArrayList<Arc> arrayList)
    {
        paths = arrayList;
    }
    
    
    /**
     @return le Sommet terminant le chemin si le chemin est vide il vaut null
     */
    public Sommet arrivee ()
    {
        if(paths.size() == 0) return null;
        Arc arc = paths.get(paths.size() - 1);
        return (Sommet) arc.destination();
    }
    
    
    /**
     @return la somme des valeurs des arcs
     */
    public int distance ()
    {
        return distance;
    }
    
    
    /**
     @return la liste des arcs qui composent le chemin
     */
    public ArrayList<Arc> getArcs ()
    {
        return paths;
    }
    
    /**
     Ajoute un arc � la fin du chemin
     TODO : verifier que le dernier noeud est bien le premier noeud de l'arc ajout�
     */
    public boolean add (Arc e)
    {
        distance += e.valeur();
        return paths.add(e);
    }
    
    public void add (int x, Arc e)
    {
        distance += e.valeur();
        paths.add(x, e);
    }
    
    public boolean addAll (Collection<Arc> c)
    {
        for (Arc a : c)
        {
            distance += a.valeur();
        }
        return paths.addAll(c);
    }
    
    public void clear ()
    {
        distance = 0;
        paths.clear();
        
    }
    
    /**
     verifie l'appartenance d'un arc au chemin
     
     @param arc
     @return vrai si l'arc appartient au chemin
     */
    public boolean contains (Arc arc)
    {
        return paths.contains(arc);
    }
    
    
    public boolean isEmpty ()
    {
        return paths.isEmpty();
    }
    
    public boolean remove (Arc o)
    {
        return paths.remove(o);
    }
    
    public boolean removeAll (Collection<Arc> c)
    {
        return paths.removeAll(c);
    }
    
    
    public int size ()
    {
        return paths.size();
    }
    
    
    @Override
    public String toString ()
    {
        return "Chemin [dist.=" + distance + ", paths=" + paths + "]";
    }
    
    
    /**
     d�termine si le sommet appartient au chemin
     
     @param sommet
     @return vrai si le sommet appartient au chemin
     */
    public boolean atteint (Sommet sommet)
    {
        for (Arc a : paths)
        {
            if(a.destination().equals(sommet)) return true;
        }
        return false;
    }
    
    
    /**
     @param depart
     @param arrivee
     @return le sous-chemin reliant depart et  arrivee si les deux noeuds appartiennent au chemin.
     */
    public Chemin extraireChemin (Sommet depart, Sommet arrivee)
    {
        boolean debutee = false;
        Chemin c = new Chemin();
        for (Arc a : paths)
        {
            if(debutee)
            {
                c.add(a);
            }
            if(a.origine().equals(depart))
            {
                c.add(a);
                debutee = true;
            }
            if(debutee && a.destination().equals(arrivee)) return c;
        }
        return c;
        
    }
    
    
    public int compareTo (Chemin c)
    {
        if(this.distance() < c.distance())
        {
            return -1;
        }
        else if(this.distance() == c.distance())
        {
            return 0;
        }
        else
        {
            return 1;
        }
        
    }
    
    public boolean equals (Object o)
    {
        if(!(o instanceof Chemin)) return false;
        Chemin c = (Chemin) o;
        if(distance == c.distance)
        {
            if(c.paths.size() == this.paths.size())
            {
                for (Arc a : c.paths)
                {
                    if(!paths.contains(a))
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    
    public ArrayList<Sommet> sommets ()
    {
        ArrayList<Sommet> sommets = new ArrayList<Sommet>();
        if(paths.size() != 0)
        {
            for (Arc arc : paths)
            {
                sommets.add((Sommet) arc.origine());
            }
            sommets.add((Sommet) paths.get(paths.size() - 1).destination());
        }
        return sommets;
    }
    
}



