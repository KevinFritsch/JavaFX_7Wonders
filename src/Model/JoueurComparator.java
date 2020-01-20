package Model;

import java.util.Comparator;

/***
 * Cette classe permet de trier la liste de classement des joueurs selon leur nombre de crédits ECTS
 * 
 *
 */
public class JoueurComparator implements Comparator<Joueur> {

	@Override
	public int compare(Joueur o1, Joueur o2) {
		int res = Integer.compare(o2.getCreditsECTS()*50+ 
				o2.getArgent()*25 + 
				o2.getPossedeBatimentUTBM().getNiveauAmelioration() * 25, o1.getCreditsECTS()*50 + 
				o1.getArgent()*25 + 
				o1.getPossedeBatimentUTBM().getNiveauAmelioration() * 25);
		
		return res;
	}

}
