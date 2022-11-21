package formatting;


public abstract class Formatter implements ExportingComposition {
	
	protected String directory;
	protected ExportingComposition decoratedComposition; 
	
	public Formatter(ExportingComposition decorated, String s) {
		directory = s; 
		decoratedComposition = decorated;
	}
	
	public void exportFormat() {
		decoratedComposition.exportFormat();
	}
}