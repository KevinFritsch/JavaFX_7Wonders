package Model;

public class Joueur {

	private int argent = 3;

	private int creditsECTS = 0;

	private String nomJoueur;

	private MainCartes mainCartes;
	
	private BatimentUTBM possedeBatimentUTBM;

	public Joueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
		this.mainCartes = new MainCartes("1");//on modifiera l'age
		this.possedeBatimentUTBM = new BatimentUTBM();		
	}
	
	public void nouvelleMainCartes(String age) {
		this.mainCartes = new MainCartes(age + "");
	}
	public void defausserCarte(Categorie carte) {
		mainCartes.getListeCartes().remove(carte);
		argent += 3;
	}

	public void choisirCarte(Categorie carte) {
		Gain gain = carte.getGainValidationCategorie();
		if(gain.getGainUVs() == null) {
			creditsECTS += gain.getGainCreditsECTS();
		}else {
			for(UV uv : gain.getGainUVs()) {
				possedeBatimentUTBM.addUV(uv);
			}
		}
		mainCartes.getListeCartes().remove(carte);
	}

	public boolean acheterUvVoisin(Joueur joueur, UV uv) {
		if(argent >= 3) {
			//joueur.getPossedeBatimentUTBM().getRessources().remove(uv);
			possedeBatimentUTBM.getRessources().add(uv);
			joueur.argent += 3;
			argent -= 3;
			return true;
		}
		return false;	
	}

	public boolean validerCategorie(Categorie categorie) {
		if(categorie.getCoutCategorie().size() == 0) {
			mainCartes.getListeCartes().remove(categorie);
			return true;
		}
		boolean verif = false;
		for(UV cout : categorie.getCoutCategorie()) {
			verif = false;
			for(UV ressource : possedeBatimentUTBM.getRessources()) {
				if(ressource.equals(cout)) {
					verif = true;
				}
			}
			if(!verif) {
				return verif;
			}
		}
		mainCartes.getListeCartes().remove(categorie);
		Gain gainValide = categorie.getGainValidationCategorie();
		if(gainValide.getGainUVs() == null) {
			creditsECTS += gainValide.getGainCreditsECTS();
		}else {
			possedeBatimentUTBM.getRessources().addAll(gainValide.getGainUVs());
		}
		
		return verif;
		
	}

	public boolean ameliorerBatiment(Categorie carte) {
		Gain res = possedeBatimentUTBM.ameliorerBatiment();
		
		if(res != null) {
			mainCartes.getListeCartes().remove(carte);
			if(res.getGainUVs() != null) {
				possedeBatimentUTBM.getRessources().addAll(res.getGainUVs());
			}else {
				creditsECTS += res.getGainCreditsECTS();
			}
			return true;
		}
		return false;
		
	}

	public int calculTotalCredit() {
		return creditsECTS;
	}

	public BatimentUTBM getPossedeBatimentUTBM() {
		return possedeBatimentUTBM;
	}

	public void setPossedeBatimentUTBM(BatimentUTBM possedeBatimentUTBM) {
		this.possedeBatimentUTBM = possedeBatimentUTBM;
	}

	public int getArgent() {
		return argent;
	}

	public void setArgent(int argent) {
		this.argent = argent;
	}

	public int getCreditsECTS() {
		return creditsECTS;
	}

	public void setCreditsECTS(int creditsECTS) {
		this.creditsECTS = creditsECTS;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

	public MainCartes getMainCartes() {
		return mainCartes;
	}

	public void setMainCartes(MainCartes mainCartes) {
		this.mainCartes = mainCartes;
	}
	@Override
	public String toString() {
		return nomJoueur;
	}
}
