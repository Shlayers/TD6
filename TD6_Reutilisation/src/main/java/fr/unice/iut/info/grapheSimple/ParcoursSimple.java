package main.java.fr.unice.iut.info.grapheSimple;

import main.java.fr.unice.iut.info.grapheX.Arc;
import main.java.fr.unice.iut.info.grapheX.Sommet;
import main.java.fr.unice.iut.info.grapheX.Graphe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Cette classe d�finit un parcours simple de graphes
 * contenant des arcs inverses et plusieurs arcs entre deux sommets donn�s.
 * @author blay
 *
 */
public class ParcoursSimple {

	Graphe graphe;


	public ParcoursSimple(Graphe graphe) {
		super();
		this.graphe = graphe;
	}

	
	
	/**
	 * @param origine
	 * @return une liste de chemin
	 * Cette m�thode renvoie les chemins les plus longs possibles � partir du point d'orgine
	 */
	public ArrayList<Chemin> chemins(Sommet origine){
		HashMap<Sommet,ArrayList<Arc>> dejaVu = new HashMap<Sommet, ArrayList<Arc>>();
		ArrayList<Chemin> cheminss = chemins(origine,dejaVu);
		return cheminss;
	}
	
	/**
	 * @param origine
	 * @param destination
	 * @return une liste de chemins entre deux Sommets
	 * Cette m�thode renvoie tous les chemins entre deux Sommets donnes
	 */
	//Pbme avec la comparaison des chemins... qui considere comme �gal deux objets diff�rents... cela doit venir de l'h�ritage...

	public ArrayList<Chemin> chemins(Sommet origine, Sommet destination){
		ArrayList<Chemin> chemins = chemins(origine);
		ArrayList<Chemin> cheminsEntreDeuxSommets = new ArrayList<Chemin> ();
		for(Chemin c : chemins) {
			if (c.atteint(destination)){
				Chemin c_raccourcis = c.extraireChemin(origine, destination);
				if (! cheminsEntreDeuxSommets.contains(c_raccourcis)) {
					cheminsEntreDeuxSommets.add(c_raccourcis);
				}
			}
		}
		return cheminsEntreDeuxSommets;
	}
	
	

	private ArrayList<Chemin> chemins(Sommet origine, HashMap<Sommet,ArrayList<Arc>> dejaVu){

		ArrayList<Chemin> chemins = new ArrayList<Chemin>();

		if  (dejaVu.containsKey(origine)){
			chemins.add(new Chemin(dejaVu.get(origine)));
			return chemins;
		}

		
		dejaVu.put(origine, new ArrayList<Arc>());


		Collection<Arc<Sommet>> voisins = graphe.voisins(origine);	
		HashMap<Sommet,ArrayList<Arc>> dejavVuLocal = new HashMap<Sommet, ArrayList<Arc>>();

		for (Arc<Sommet> a : voisins) {
			Sommet destination = a.destination();
			dejavVuLocal= new HashMap<Sommet,ArrayList<Arc>>(dejaVu);

			if (nouvelleDestinationOuNouvelArcSansRetour(origine,dejavVuLocal,destination,a)) { 
				dejavVuLocal.get(origine).add(a);
				ArrayList<Chemin> cheminsLocaux = chemins(destination,dejavVuLocal);
				if (cheminsLocaux.isEmpty()) {
					Chemin chemin = new Chemin();
					chemin.add(a);
					chemins.add(chemin);
					}
				else {
					for (Chemin c : cheminsLocaux) {
						c.add(0,a);
						chemins.add(c);
					}
				}
			}
		}
		return chemins;
	}

	private boolean nouvelleDestinationOuNouvelArcSansRetour(
		Sommet origine, HashMap<Sommet, ArrayList<Arc>> dejaVu, Sommet destination,
		Arc<Sommet> a) {

		if (! dejaVu.containsKey(destination) )
			return true;

		ArrayList<Arc> x = dejaVu.get(destination);

		return ( (! dejaVu.get(destination).contains(a)) && (! dejaVu.containsKey(a.destination()) ) );
	}
	
	
	public Chemin cheminLePlusCourt(Sommet origine, Sommet destination){
		ArrayList<Chemin> chemins = this.chemins(origine, destination);
		Chemin cheminLePlusCourt = null;
		int distanceLaPlusCourte = Integer.MAX_VALUE;
		for(Chemin c : chemins) {
			if (distanceLaPlusCourte > c.distance()) {
				distanceLaPlusCourte = c.distance();
				cheminLePlusCourt = c;
			}
		}
		return cheminLePlusCourt;
	}
	
	public Set<Sommet> voisinsAuRang(Sommet origine, int rang){
		ArrayList<Chemin> chemins = chemins(origine);
		Set<Sommet> sommetsVoisinsDejaVu = new TreeSet();
		Set<Sommet> sommetsDeBonRang = new TreeSet();
		for (Chemin c : chemins) {
			ArrayList<Sommet> sommets = c.sommets();
			int i = 0;
			for (i = 0; (i <sommets.size() && i < rang); i++)
				sommetsVoisinsDejaVu.add(sommets.get(i));
			if ( (i == rang) && (i < sommets.size()) )
				sommetsDeBonRang.add(sommets.get(i));
			}
		sommetsDeBonRang.removeAll(sommetsVoisinsDejaVu);
		return sommetsDeBonRang;
	}
}
	
	
