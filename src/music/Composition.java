package music;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.io.*;
import java.util.regex.*;
import exceptions.*;
import formatting.ExportingComposition;
import iterator.Iterator;
import iterator.List;
import iterator.SymbolList;
import user.User;

public class Composition implements ExportingComposition {
	public Map<Character, String> charMap = new HashMap<>();
	public Map<String, Character> reversedMap = new HashMap<>();
	public Map<String, Integer> midiMap = new HashMap<>();
	public Map<Integer, String> reversedMidiMap = new HashMap<>();
	private boolean isLoaded=false, isPlaying=false;
	
	// ITERATOR
	private List symbolList = new SymbolList();
	private Iterator iterator = symbolList.createIterator();
	
	public void setLoaded(boolean b) { isLoaded=b; }
	public void setPlaying(boolean b) { isPlaying=b; }
	public boolean isLoaded() { return isLoaded; }
	public boolean isPlaying() { return isPlaying; }
	
	public Composition() {
		mapParse();
	}
	
	// STRATEGIJA + ITERATOR
	public String printComposition() {
		return symbolList.print();
	}
	
	private void mapParse() {
		File file=new File("map.csv");
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				Stream<String> s = br.lines();
				
				s.forEach(l->{
					Pattern p = Pattern.compile("([^,]*),([^,]*),([^\n]*)");
					Matcher m = p.matcher(l);
					
					if (m.matches()) {
						String chr = m.group(1);
						String midi = m.group(2);
						String num = m.group(3);
						
						charMap.put(chr.charAt(0), midi);
						reversedMap.put(midi, chr.charAt(0));
						midiMap.put(midi, Integer.parseInt(num));
						reversedMidiMap.put(Integer.parseInt(num), midi);
					}
				});
				br.close();
			} 
			catch (FileNotFoundException e) {
				System.err.println("Fajl nije pronadjen...");
			}
			catch (IOException e) {}
	}
	
	public void putSymbol(String s, boolean group) {
		boolean chord=false;
		Chord chr=null;
		if (group && s.charAt(1) == ' ') //izbacuje blanko znake iz stringa ako su u grupi
			s = s.replaceAll("\\s+","");
		else if (group) { 
			chord=true; 
			chr=new Chord(); 
		}
		for (int i=0; i<s.length(); i++) {
			char c=s.charAt(i);
			if (!group) {
				if (c != ' ' && c != '|') { //ako nije pauza, note po 1/4
					String str = charMap.get(c);
					Note n = new Note(str, new Duration(1, 4));
					symbolList.add(n);
				}
				else if (c == ' ') { 
					symbolList.add(new Pause(new Duration(1, 8))); 
				} //osminska pauza
				else { 
					symbolList.add(new Pause(new Duration(1, 4))); 
				} //cetvrtinska pauza
			}
			else if (!chord) { //note po 1/8 - nije akord
				String str = charMap.get(c);
				Note n= new Note(str, new Duration(1,8));
				symbolList.add(n);
			}
			else { //akord 1/4
				String str = charMap.get(c);
				Note n = new Note(str, new Duration(1,4));
				chr.add(n);
			}
		}
		if (chr!=null && chr.size()>0) symbolList.add(chr);
	}
	
	// FASADA
	// Metoda za parsiranje je ubacena u klasu Kompozicija, pa samim tim
	// glavni program vise ne mora da zna za postojanje muzickih simbola
	// jer se sva komunikacija vrsi unutar fasade
	public void parse(String directory) {
		symbolList.clear();
		File file=new File(directory);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			Stream<String> s = br.lines();
			
			s.forEach(l->{
				Pattern p = Pattern.compile("([^\\[]*)([^\\]]*)(.*)");
				Matcher m = p.matcher(l);
				
				if (m.matches()) {
					String s1 = m.group(1);
					String s2 = m.group(2);
					String s3 = m.group(3);
					putSymbol(s1, false);
					
					if (s2.length()>0) { //skida [] iz grupa
						s2 = s2.substring(1);
						s3 = s3.substring(1);
						putSymbol(s2, true);
					}
					
					while (s3.length()>0) {
						String str=s3;
						Pattern p1=Pattern.compile("([^\\[]*)([^\\]]*)(.*)");
						Matcher m1=p1.matcher(str);
						if (m1.matches()) {
							String first=m1.group(1);
							if (first.length()>0) putSymbol(first, false);
							
							String second=m1.group(2);
							String third=m1.group(3);
							if (second.length()>0) {
								second = second.substring(1);
								third = third.substring(1);
							}
							if (second.length()>0) putSymbol(second, true);
							s3=third;
						}
					}
				}
			});
			
			br.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println("Fajl nije pronadjen...");
		}
		catch (IOException e) {}
	}
	
	public void add(MusicSymbol m) {
		symbolList.add(m);
	}
	
	public void removeLast() {
		symbolList.removeLast();
	}
	
	public MusicSymbol getLast() {
		return (MusicSymbol) symbolList.get(symbolList.size() - 1);
	}
	
	// ITERATOR
	public String toString() {
		StringBuilder sb=new StringBuilder();
		
		for(iterator.firstElement(); iterator.hasNext(); iterator.nextElement()) {
			MusicSymbol s = (MusicSymbol) iterator.getElement();
			sb.append(""+s+" ");
		}
		return sb.toString();
	}
	
	public int getSize() {
		return symbolList.size();
	}
	
	public MusicSymbol getSymbol(int i) throws OutOfBounds {
		if (i<0 || i >= symbolList.size()) throw new OutOfBounds();
		return (MusicSymbol) symbolList.get(i);
	}
	
	// DEKORATER
	@Override
	public void exportFormat() {
		System.out.println("Uspesno je izvrseno eksportovanje kompozicije.");
		
	}
	public boolean canExport() {
		User user = User.getInstance();
		if (!user.hasData()) return false;
		return true;
	}
}
