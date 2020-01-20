package Model;
import java.util.ArrayList;

public class Categorie {

	private String typeCategorie;

	private String nomCategorieEnum;
	
	private Gain gainValidationCategorie;
	
	private ArrayList<UV> coutCategorie = null;

	public Categorie(String nom, String type, Gain gainValidationCategorie, ArrayList<UV> cout) {
		this.nomCategorieEnum = nom;
		this.typeCategorie = type;
		this.gainValidationCategorie = gainValidationCategorie;
		this.coutCategorie = new ArrayList<UV>(cout);
		
	}

	public Categorie(Categorie c) {
		typeCategorie = c.typeCategorie;
		nomCategorieEnum = c.nomCategorieEnum;
		gainValidationCategorie = c.gainValidationCategorie;
		coutCategorie = new ArrayList<UV>();
		for(UV uv : c.coutCategorie) {
			coutCategorie.add(new UV(uv));
		}
	}
	public ArrayList<UV> getCoutCategorie() {
		return coutCategorie;
	}

	public Gain getGainValidationCategorie() {
		return gainValidationCategorie;
	}

	public void setGainValidationCategorie(Gain gainValidationCategorie) {
		this.gainValidationCategorie = gainValidationCategorie;
	}


	public void setCoutCategorie(ArrayList<UV> coutCategorie) {
		this.coutCategorie = coutCategorie;
	}

	public void setTypeCategorie(String typeCategorie) {
		this.typeCategorie = typeCategorie;
	}

	public String getNomCategorieEnum() {
		return nomCategorieEnum;
	}

	public void setNomCategorieEnum(String nomCategorieEnum) {
		this.nomCategorieEnum = nomCategorieEnum;
	}
	@Override
	public String toString() {
		String cout = " Coût : " +  coutCategorie.toString() + ")";
		if(coutCategorie.size() == 0) {
			cout = "Gratuit ! )";
		}
		return nomCategorieEnum + " " + typeCategorie + " (Gain validation : " + gainValidationCategorie.toString() + " | " + cout;
	}
	
	public boolean equals(Categorie categorie) {// Object obj
	    if(this != null) {
	    	if (this == categorie) {
		        return true;
		    }
		    else if(categorie.nomCategorieEnum.equals(nomCategorieEnum)&& categorie.typeCategorie.equals(typeCategorie)&& categorie.gainValidationCategorie.equals(gainValidationCategorie) && categorie.coutCategorie.equals(coutCategorie) ){
				return true;
			}
	    }
		return false;
  
}
}
