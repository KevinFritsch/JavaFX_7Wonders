package Model;
import java.util.ArrayList;
import java.util.Random;
public class BatimentUTBM {

	private int niveauAmelioration = 0;
	private static final int niveauAmeliorationMax = 2;
	private NomBatimentEnum nomBatimentEnum;
	private UV uvDeBase;
	/**
	 * Liste représentant les gains que le joueur peut reçevoir s'il améliore son bâtiment
	 */
	private ArrayList<Gain> listeGainPossibleBatiment;
	/**
	 * Liste des UV possédées par un joueur, elle correspond aux ressources du joueur
	 */
	private ArrayList<UV> listeUV;
	
	/***
	 * Liste static permettant d'asssocier les batiments à chaque joueur de façon unique
	 */
	public static ArrayList<NomBatimentEnum> BatimentDejaUtilise = new ArrayList<NomBatimentEnum>();

	public BatimentUTBM() {
		listeUV = new ArrayList<>();
		listeGainPossibleBatiment = new ArrayList<Gain>();
		this.nomBatimentEnum = randomNomBatiment();
        randomGains();
    }

	public void setUvDeBase(UV uv) {
		uvDeBase = uv;
		listeUV.add(getUvDeBase());
	}
	public NomBatimentEnum randomNomBatiment() {		
		NomBatimentEnum randomBatiment = NomBatimentEnum.getRandomNomBat();
		while(BatimentDejaUtilise.contains(randomBatiment)) {
			randomBatiment = NomBatimentEnum.getRandomNomBat();
		}
		BatimentDejaUtilise.add(randomBatiment);
		
		return randomBatiment;
		}
	
	public void randomGains() {
		Random rand  = new Random();
		
		int nbCreditGain;
		int nbGainUV;
		int randomChoixGain;
		int randomAge;
		
		ArrayList<String> listeNomUV = new ArrayList<String>();
		listeNomUV.add("LO");
		listeNomUV.add("GE");
		listeNomUV.add("LP");
		listeNomUV.add("AG");
		listeNomUV.add("MI");
		listeNomUV.add("MT");
		
		for(int i = 0; i < 3; i++) {
			nbCreditGain = randInt(1,3);
			nbGainUV =randInt(1,3);
			randomChoixGain =randInt(0,1);
			randomAge = randInt(1,3);

			Gain gainUV = null;
			if (randomChoixGain == 0){
				gainUV = new Gain(nbCreditGain);
			} else{
				ArrayList<UV> gainUVs = new ArrayList<UV>() ;
				for (int y=0;y<nbGainUV;y++){
					gainUVs.add(new UV(listeNomUV.get(rand.nextInt(listeNomUV.size()))+randomAge+randInt(1,2)));
				}
				
				gainUV = new Gain(gainUVs);
			}			
		
			this.listeGainPossibleBatiment.add(gainUV);
		}
		
		//l'uv de base a un age de 1
		setUvDeBase(new UV(listeNomUV.get(rand.nextInt(listeNomUV.size()))+1+randInt(1,2)));
		
		
	}
	public static int randInt(int min, int max) {


	    Random rand = new Random();

	   
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
    

	public void addUV(UV uv){  // On avait pas de fonction pour ajouter les UV que l'on gagnera en jouant
	    listeUV.add(uv);
    }

	public Gain ameliorerBatiment() {
	    //On veut donc améliorer le batiment
        // D'abord une vérification
        if(getNiveauAmelioration() < 0 || getNiveauAmelioration() > getNiveauAmeliorationMax()){
            System.out.println("Impossible d'améliorer le batiment");
            return null;
        }
        // On améliore donc le batiment
        Gain res = listeGainPossibleBatiment.get(getNiveauAmelioration());
       
        // on augment le niveau d'amélioration
        setNiveauAmelioration(getNiveauAmelioration()+1);
        
        // on redonne le gain qui a été gagné.
		return res;
	}

	public UV getUvDeBase() {
		return uvDeBase;
	}


	//cette fonction doit uniquement renvoyer la recompense suivante
	public Gain getGainAmeliorationBatiment() { // on obtient la prochaine recompense
		return listeGainPossibleBatiment.get(getNiveauAmelioration());
	}

	public ArrayList<UV> getRessources(){
		return this.listeUV;
	}

	public static int getNiveauAmeliorationMax() {
		return niveauAmeliorationMax;
	}

	public int getNiveauAmelioration() {
		return niveauAmelioration;
	}

	public void setNiveauAmelioration(int niveauAmelioration) {
		this.niveauAmelioration = niveauAmelioration;
	}

	public NomBatimentEnum getNomBatimentEnum() {
		return nomBatimentEnum;
	}

}
