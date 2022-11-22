package formatting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.OutOfBounds;
import music.*;
import user.User;

public class TxtFormatter extends Formatter {
	
	private Composition composition;

	public TxtFormatter(ExportingComposition d, String s) {
		super(d, s);
		composition = (Composition)d;
	}

	@Override
	public void exportFormat() {
		if (!canExport()) System.err.println("Zabranjeno eksportovanje jer nema unetog korisnika");
		writeToFile();
		decoratedComposition.exportFormat();
	}
	
	private void writeToFile() {
		try {
			Files.createDirectories(Paths.get(User.getInstance().getUsername()));
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(directory));
			writer.write(toString());
			writer.close();
		} catch (IOException e) {
			System.err.println("Direktorijum nije pronadjen");
		}
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (int i=0; i<composition.getSize(); i++) {
			MusicSymbol m=null;
			try {
				m = composition.getSymbol(i);
			} catch (OutOfBounds e) {continue;}
			Duration d=m.symDuration();
			if (m instanceof Pause) {
				if (d.equalDur(new Duration(1,4))) sb.append("|");
				else sb.append(" ");
			}
			else if (m instanceof Note) {
				if (d.equalDur(new Duration(1,4))) sb.append(""+composition.reversedMap.get(m.toString()));
				else {
					sb.append("["+composition.reversedMap.get((m.toString().toUpperCase()))+" ");
					while (++i<composition.getSize()) {
						MusicSymbol ms=null;
						try {
							ms = composition.getSymbol(i);
						} catch (OutOfBounds e) {break;}
						Duration dur=ms.symDuration();
						if (dur.equalDur(new Duration(1,8)) && ms instanceof Note) sb.append(composition.reversedMap.get(ms.toString().toUpperCase())+" ");
						else {
							i--;
							break;
						}
					}
					sb.append("]");
				}
			}
			else if (m instanceof Chord) {
				sb.append("[");
				Chord c=(Chord)m;
				Character chr;
				for (int j=0; j<c.size(); j++) {
					try {
						chr = composition.reversedMap.get(c.getNote(j).toString());
					} catch (OutOfBounds e) {continue;}
					sb.append(""+chr);
				}
				sb.append("]");
			}
		}
		return sb.toString();
	}

}
