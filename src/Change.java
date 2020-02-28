public class Change {
	private String pathName;
	private String name;
	public Type type;
	public long time;
	public String date;
	enum Type {
		NEW, EDIT;
		public String toString(){
			if(this == NEW){
				return "New";
			}else{
				return "Edit";
			}
		}
	}
	public Change(String pathName, Type type) {
		this.pathName = pathName;
	}
	public Type type() {
		return type;
	}
	public String getPath() {
		return pathName;
	}
	public String getDate() {
		return date;
	}
	public long getTime() {
		return time;
	}
	public String getName(){
		return name;
	}
}

