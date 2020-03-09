import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Change {
	private String pathName;
	private String name;
	public Type type;
	public String date;
	public String init;

	public Change(String pathname, String Name, String Init) {
		pathName = pathname;
		init = Init.toUpperCase();
		name = Name;
		init();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		date = dtf.format(now);
	}

	enum Type {
		NEW, EDIT;
		public String toString() {
			if (this == NEW) {
				return "New";
			} else {
				return "Edit";
			}
		}
	}

	public void init() {
		if (this.init.equals("NEW")) {
			this.type = Type.NEW;
		} else {
			this.type = Type.EDIT;
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

	public String getName() {
		return name;
	}
}