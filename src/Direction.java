
public class Direction {
	String dir;
	char char_dir;
	
	public Direction(String dir, char char_dir) {
	super();
		this.dir = dir;
		this.char_dir = char_dir;
	}
	
	

	public String getDir() {
		return dir;
	}



	public void setDir(String dir) {
		this.dir = dir;
	}



	public char getChar_dir() {
		return char_dir;
	}



	public void setChar_dir(char char_dir) {
		this.char_dir = char_dir;
	}



	@Override
	public String toString() {
		return dir;
	}
	
}
