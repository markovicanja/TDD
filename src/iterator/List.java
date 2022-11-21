package iterator;

public interface List {
	public Iterator createIterator();
	public void add(Object o);
	public void clear();
	public void removeLast();
	public Object get(int position);
	public int size();
	public String print();
}
