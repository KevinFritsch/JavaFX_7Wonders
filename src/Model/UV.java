package Model;
public class UV {

	private String nomUV;
	private int age;
	

	public UV(String nomUV){
		this.nomUV= nomUV;
		this.age = -1; //si l'age est -1 alors cela veut dire qu'a nimporte quelle age, l'uv peut etre dispo
	}

	public UV(String nomUV, int ageUV){
		this.nomUV= nomUV;
		this.age = ageUV;
	}

	public UV(UV uv) {
		nomUV = uv.nomUV;
		age = uv.age;
	}
	public int getAge() {
		return age;
	}
	public String getNomUV() {
		return nomUV;
	}



	public void setNomUV(String nomUV) {
		this.nomUV = nomUV;
	}
	
	public boolean equals(UV uv) {
		if(uv.toString().equals(this.toString())) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return nomUV;
	}


}
