package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Model.Categorie;
import Model.Joueur;
import Model.JoueurComparator;
import Model.MainCartes;
import Model.UV;
import View.Vue;

public class Jeu {

	private int nombreJoueurs;
	private int tourActuel;
	private int ageActuel;
	private static final int ageMax = 3;
	private static final int tourMax = 6;

	/***
	 * Booléen permettant de déterminer le sens de jeu selon l'age du jeu
	 */
	private boolean echangeSensHoraire = true;
	
	private int indexJoueurCourant;
	private Joueur joueurCourant;
	private ArrayList<Joueur> listeJoueurs;
	private static Vue vue = null;
	/***
	 * Instance de la classe Jeu permettant de respecter le Design Pattern Singleton
	 */
	private static Jeu instanceJeu = null;
	
	private Jeu() {
		this.tourActuel = 1;
		this.ageActuel = 1;
		this.listeJoueurs = new ArrayList<Joueur>();
		this.indexJoueurCourant = 0;
	}
	
	/***
	 * Méthode static permettant d'intialiser la vue de l'application
	 * @param v Vue
	 */
	public static void setUpSingleton(Vue v) {
		vue = v;
	}
	/***
	 * Méthode static qui permet de retourner l'unique instance de l'application
	 * @return instance unique de l'application
	 */
	public static Jeu getInstanceJeu(){
		if(instanceJeu == null) {
			instanceJeu = new Jeu();
		}
		return instanceJeu;
	}

	/***
	 * Cette fonction permet de changer l'ordre des joueurs de la liste de joueurs pour permettre d'inverser le sens d'echange de cartes
	 */
	
	public void ageSuivant() {
		if(ageActuel < ageMax) {
			ageActuel++;
			vue.afficherNouvelAge();
			//on redistribue des nouvelles cartes pour un nouvel age
			for(Joueur joueur : listeJoueurs) {
				joueur.nouvelleMainCartes((ageActuel+1)+"");
			}
			tourActuel = 1;
			
			indexJoueurCourant = 0;
			joueurCourant = listeJoueurs.get(indexJoueurCourant);
			
			if(echangeSensHoraire) {
				echangeSensHoraire = false;
			}else {
				echangeSensHoraire = true;
			}
		}else {//fin partie
			finJeu();
		}
	}

	public void tourSuivant() {
		if(tourActuel < tourMax) {
			tourActuel++;
			vue.afficherNouveauTour();
			indexJoueurCourant = 0;
			joueurCourant = listeJoueurs.get(indexJoueurCourant);
			
			//echange des mains de cartes de jeu des joueurs selon le sens 
			MainCartes mainTemp;
			ArrayList<MainCartes> listeMains = new ArrayList<MainCartes>();
			if(echangeSensHoraire) {
				mainTemp = listeJoueurs.get(listeJoueurs.size()-1).getMainCartes();
				for(int i = 0; i < listeJoueurs.size() - 1; i++) {
					listeMains.add(new MainCartes(listeJoueurs.get(i).getMainCartes()));
				}
				for(int i = 1; i < listeJoueurs.size(); i++) {
					listeJoueurs.get(i).setMainCartes(listeMains.get(i-1));
				}
				joueurCourant.setMainCartes(new MainCartes(mainTemp));
			}else {
				mainTemp = joueurCourant.getMainCartes();
				for(int i = 1; i < listeJoueurs.size(); i++) {
					listeMains.add(new MainCartes(listeJoueurs.get(i).getMainCartes()));
				}
				
				for(int i = 0; i < listeJoueurs.size() - 1; i++) {
					listeJoueurs.get(i).setMainCartes(listeJoueurs.get(i+1).getMainCartes());
				}
				listeJoueurs.get(listeJoueurs.size()-1).setMainCartes(new MainCartes(mainTemp));
				
			}

		}else {
			ageSuivant();
		}
	}

	public void finJeu() {
		vue.finPartie();
	}

	public void passageJoueurSuivant() {
		if(indexJoueurCourant < listeJoueurs.size() - 1) {
			indexJoueurCourant++;
			joueurCourant = listeJoueurs.get(indexJoueurCourant);
		}else{
			tourSuivant();
		}
		
	}
	public ArrayList<String> getClassement(){
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>(listeJoueurs);
		//on trie la liste par ordre croissant selon les crédits ects
		//Collections.sort(joueurs, (p1, p2) -> p1.getCreditsECTS() - p2.getCreditsECTS());
		Collections.sort(joueurs, new JoueurComparator());
		
		ArrayList<String> res = new ArrayList<String>();
		for(int i = 0; i < joueurs.size(); i++) {
			res.add((i+1) + "- " + joueurs.get(i).getNomJoueur() + " (ECTS : " + joueurs.get(i).getCreditsECTS() + " | Argent : " + 
		joueurs.get(i).getArgent()+ " | Niveau bâtiment : " + joueurs.get(i).getPossedeBatimentUTBM().getNiveauAmelioration() + ")");
			//System.out.println(res.get(i));
		}	
		
		return res;
	}
	

	
	public void defausserCarte(Categorie carte) {
		joueurCourant.defausserCarte(carte);
	}
	
	public void ameliorerBatiment(Categorie carte) {
		joueurCourant.ameliorerBatiment(carte);
	}
	
	public void validerCategorie(Categorie carte) {
		
		joueurCourant.validerCategorie(carte);
		
	}
	public boolean achatRessourcesVoisins(Joueur voisin, UV uv) {
		return joueurCourant.acheterUvVoisin(voisin, uv);
	}
	public ArrayList<UV> getRessourcesJoueur(Joueur joueur){
		ArrayList<UV> listeRessources = new ArrayList<UV>(joueur.getPossedeBatimentUTBM().getRessources());
		
		return listeRessources;
	}
	public ArrayList<Joueur> getListeVoisins(){
		ArrayList<Joueur> res = new ArrayList<Joueur>(listeJoueurs);
		res.remove(joueurCourant);
		
		return res;
	}
	public void addJoueur(String nomJoueur) {
		Joueur newJoueur = new Joueur(nomJoueur);
		
		if(joueurExisteDeja(nomJoueur)) {
			throw new IllegalArgumentException("Le joueur éxiste déja !");
		}
		this.listeJoueurs.add(newJoueur);

	}


	
	public boolean joueurExisteDeja(String nomJoueur) {
		for(Joueur j : this.listeJoueurs) {
			if(j.getNomJoueur().equals(nomJoueur)) {
				return true;
			}
		}
		return false;
	}
	public int getAgeMax() {
		return	ageMax;
	}

	public int getTourMax() {
		return tourMax;
	}

	public int getNombreJoueurs() {
		return nombreJoueurs;
	}

	public void setNombreJoueurs(int nombreJoueurs) {
		this.nombreJoueurs = nombreJoueurs;
	}

	public int getTourActuel() {
		return tourActuel;
	}

	public void setTourActuel(int tourActuel) {
		this.tourActuel = tourActuel;
	}

	public int getAgeActuel() {
		return ageActuel;
	}

	public void setAgeActuel(int ageActuel) {
		this.ageActuel = ageActuel;
	}

	public boolean isEchangeSensHoraire() {
		return echangeSensHoraire;
	}
	
	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public void setJoueurCourant(Joueur joueurCourant) {
		this.joueurCourant = joueurCourant;
	}

	public ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}
}
