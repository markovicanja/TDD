package iterator;

import java.util.ArrayList;

import music.MusicSymbol;

public class SymbolList implements List {
	
	private ArrayList<MusicSymbol> symbols = new ArrayList<MusicSymbol>();
	private int index = 0;

	@Override
	public Iterator createIterator() {
		return new SymbolIterator();
	}
	
	private class SymbolIterator implements Iterator {

		@Override
		public void firstElement() {
			index = 0;
		}

		@Override
		public void nextElement() {
			index++;
		}

		@Override
		public boolean hasNext() {
			if (index < symbols.size()) return true;
			return false;
		}

		@Override
		public MusicSymbol getElement() {
			return symbols.get(index);
		}

	}
	
	@Override
	public void add(Object m) {
		symbols.add((MusicSymbol) m);
	}

	@Override
	public void clear() {
		symbols.clear();
	}

	@Override
	public void removeLast() {
		if (symbols.size() == 0) return;
		symbols.remove(symbols.size() - 1);
	}

	@Override
	public MusicSymbol get(int position) {
		if (symbols.size() == 0) return null;
		return symbols.get(position);
	}

	@Override
	public int size() {
		return symbols.size();
	}

	@Override
	public String print() {
		StringBuilder sb = new StringBuilder("");
		for (MusicSymbol s : symbols) sb.append(s.print());
		return sb.toString();
	}

}
