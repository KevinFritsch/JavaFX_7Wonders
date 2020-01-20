package Model;
import java.util.ArrayList;
import java.util.Random;

public class MainCartes {
	
	ArrayList<Categorie> listeCartes;
	ArrayList<Categorie> listeCartesUtilisees;
	
	public MainCartes(String ageCourrant) {
		//la liste de cartes doit etre faite de facon aléatoire
		//il faut créer une fonction pour cela
		listeCartes = new ArrayList<Categorie>();
		listeCartesUtilisees = new ArrayList<Categorie>();
		
		/*Categorie randomCategorie ;
		randomCategorie =randomCarte(ageCourrant);
		listeCartesUtilisees.add(randomCategorie);
		listeCartes.add(randomCategorie);*/
		
		for(int i = 0; i < 7; i++) {
			setUpCartesRandom(ageCourrant);
		}	
	}
	
	public MainCartes(MainCartes m) {
		listeCartes = new ArrayList<Categorie>();
		for(Categorie c : m.listeCartes) {
			listeCartes.add(new Categorie(c));
		}
		listeCartesUtilisees = new ArrayList<Categorie>();
		for(Categorie c : m.listeCartesUtilisees) {
			listeCartesUtilisees.add(new Categorie(c));
		}
	}

	public void setUpCartesRandom(String ageCourrant) {
		Categorie randomCategorie =randomCarte(ageCourrant);
		
		for (int j=0; j<listeCartesUtilisees.size();j++){
			while(randomCategorie.equals(listeCartesUtilisees.get(j))){
				randomCategorie =randomCarte(ageCourrant);// pas la meilleur m"thode car risque de tomber sur une carte précédente

			}
		}
		listeCartesUtilisees.add(randomCategorie);
		listeCartes.add(randomCategorie);
	}
	public Categorie randomCarte(String ageCourrant) {
		Random rand = new Random(); 
		String nomType = "";
		int nbCreditGain =randInt(1,3);
		int nbGainUV =randInt(1,4);
		//le coût d'une carte peut aller de 0 à 2 UV, si le nombre est 3, cela revient à avoir 2 UV en cout. Si le nombre est 1 ou 2 il faut une
		//UV en guise de cout
		int nbCoutUV =randInt(0,3);
		if(nbCoutUV == 2) {
			nbCoutUV = 1;
		}else if(nbCoutUV == 3) {
			nbCoutUV = 2;
		}
		
		//Entier compris entre 0 et 1 permettant de connaitre la nature du gain (crédits ou UVs)
		int randomChoixGain =randInt(0,1);
		ArrayList<UV> gainUVs = new ArrayList<UV>() ;
		ArrayList<UV> coutUVs = new ArrayList<UV>() ;
		ArrayList<String> listeNomUV = new ArrayList<String>();
		
		listeNomUV.add("LO");
		listeNomUV.add("GE");
		listeNomUV.add("LP");
		listeNomUV.add("AG");
		listeNomUV.add("MI");
		listeNomUV.add("MT");

		String nomCat = new String(NomCategorieEnum.getRandomNomCat().toString());
		
		//on prend un entier au hasard entre 0 et 2, si celui-ci vaut 2 alors le gain d'UV sera une UV pour l'age suivant
		//il y a donc 1 chance sur 3 de tomber sur une carte d'age suivant si il est différent de 3
		int randomAgeCarte = randInt(0,2);
		
		int ageGainCout = Integer.parseInt(ageCourrant);
		
		if(randomAgeCarte == 2) {
			if(ageGainCout == 2) {
				ageGainCout = 1;
			}
			else if(ageGainCout == 3) {
				ageGainCout = 2;
			}
		}
		
		//num uv peut aller de 1 à 5 pour l'instant
		for (int i=0;i<nbGainUV;i++){
			gainUVs.add(new UV(listeNomUV.get(rand.nextInt(listeNomUV.size()))+ageCourrant+randInt(1,2)));
		}
		for (int i=0;i<nbCoutUV;i++){
			coutUVs.add(new UV(listeNomUV.get(rand.nextInt(listeNomUV.size()))+(ageGainCout+"")+randInt(1,2)));
		}
        
		if (nomCat.equals("TM")) {
			nomType = "Tecnique et Manager";
		}
		else if(nomCat.equals("EC")){
				nomType = "Expression Communication";
		}
		else if(nomCat.equals("CS")){
			nomType = "Culture Scientifique";
		}
		else if(nomCat.equals("CG")){
			nomType = "Culture Générale";
		}
		else if(nomCat.equals("OM")){
			nomType = "Organiser et Manager";
		}
		else if(nomCat.equals("ST")){
			nomType = "Stages et Projets";
		}
		
		Gain gainUV = null;
		if (randomChoixGain == 0){
			gainUV = new Gain(nbCreditGain);
		} else{
			gainUV = new Gain(gainUVs);
		}
		
		return new Categorie(nomCat,nomType,gainUV,coutUVs);
	}
		
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
	}
	
	public ArrayList<Categorie> getListeCartes() {
		return listeCartes;
	}

	public void setListeCartes(ArrayList<Categorie> listeCartes) {
		this.listeCartes = listeCartes;
	}
	

	
}