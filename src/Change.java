import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Change {
	private String pathName;
	private String name;
	public Type type;
	public String date;
	public String init;

	public Change(String pathname, String Name, String Init) {
		this.pathName = pathname;
		this.init = Init.toUpperCase();
		this.name = Name;
		File path = new File(pathName);
		BasicFileAttributes attrs;
		try {
			attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
			FileTime time = attrs.lastModifiedTime();

			String pattern = "MM/dd/yyyy - HH:mm";
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			this.date = format.format(new Date(time.toMillis()));

		} catch (IOException e) {
			e.printStackTrace();
		}
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
		int nameIndex = pathName.lastIndexOf("\\");
		this.name = pathName.substring(nameIndex + 1);
		this.type = type;
		File path = new File(pathName);
		BasicFileAttributes attrs;
		try {
			attrs = Files.readAttributes(path.toPath(), BasicFileAttributes.class);
			FileTime time = attrs.lastModifiedTime();

			String pattern = "MM/dd/yyyy - HH:mm";
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			this.date = format.format(new Date(time.toMillis()));

		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public String display() {
		String display = getName() + " - " + getDate() + " - " + type();
		System.out.println(display);
		return display;
	}
}