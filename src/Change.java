
public class Change {
	private String pathname;
	public Type type;
	enum Type{
		NEW,EDIT
	}
	public Change(String pathName, Type type) {
		pathname = pathName;
	}
	public Type getType() {
		return this.type;
	}
	public String getPath() {
		return this.pathname;
	}
}
