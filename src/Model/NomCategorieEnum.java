package Model;

import java.util.Random;

public enum NomCategorieEnum {

	EC("EC"),

	CG("CG"),

	OM("OM"),

	TM("TM"),

	CS("CS"),

	ST("ST");
	
	private final String nomCat;
	private NomCategorieEnum(String n) {
		nomCat = n;
	}
	@Override
	public String toString() {
		return this.nomCat;
	}
	//renvoi un nom de categorie de façon random
	public static NomCategorieEnum getRandomNomCat() {
		Random random = new Random();
		//System.out.println(values()[random.nextInt(values().length)]);
		return values()[random.nextInt(values().length)];
	}

}