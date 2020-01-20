package Model;

import java.util.Random;

public enum NomBatimentEnum {

	A("A"),

	B("B"),

	C("C"),

	D("D"),

	E("E"),

	F("F"),

	G("G");
	
	private final String nomBat;
	private NomBatimentEnum(String n) {
		nomBat = n;
	}
	@Override
	public String toString() {
		return this.nomBat;
	}
	//renvoi un nom de batiment de façon random
	public static NomBatimentEnum getRandomNomBat() {
          Random random = new Random();
          //System.out.println(values()[random.nextInt(values().length)]);
          NomBatimentEnum res = values()[random.nextInt(values().length)];
          return res;
    }

}