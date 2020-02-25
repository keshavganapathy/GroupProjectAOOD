
public class Change {
	private String pathName;
	public Type type;
	enum Type {
		NEW, EDIT
	}
	public Change(String pathName, Type type) {
		this.pathName = pathName;
	}
	public Type getType() {
		return type;
	}
	public String getPath() {
		return pathName;
	}
}
