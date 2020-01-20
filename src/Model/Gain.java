package Model;
import java.util.ArrayList;

public class Gain {

	private int gainCreditsECTS = 0;
	
	private ArrayList<UV> gainUVs = null;
	

	public Gain(int nombreCredits ) {
		this.gainCreditsECTS = nombreCredits;
		
	}
	public Gain( ArrayList<UV> listeUVs) {
		if(listeUVs != null) {
			this.gainUVs = new ArrayList<UV>(listeUVs);
		}
	}
	
	public int getGainCreditsECTS() {
		return gainCreditsECTS;
	}

	public ArrayList<UV> getGainUVs() {
		return gainUVs;
	}

	public void addGainUV(UV uv){
		gainUVs.add(uv);
	}

	public void setGainCreditsECTS(int gainCreditsECTS) {
		this.gainCreditsECTS = gainCreditsECTS;
	}
	@Override
	public String toString() {
		if(gainUVs == null) {
			return gainCreditsECTS + " Crédits ECTS";
		}
		return gainUVs.toString();
	}
}
