package music;

public class Pause extends MusicSymbol {
	
	public Pause(Duration d){
		super(d, 0);
	}
	
	@Override
	public String print() {
		String s = "";
		if (d.equalDur(new Duration(1,4))) s = "Pause: 1/4";
		else s = "Pause: 1/8";
		System.out.println(s);
		return s;
	}
	
	@Override
	public String toString() {
		if (d.equalDur(new Duration(1,4))) return "_"; 
		else return "-";
	}
}
