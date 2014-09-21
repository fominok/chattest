import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileWriter {
	
	private File file;
	
	public FileWriter() {
	
	}
	
	public void createFile(String name) throws IOException {
		
		file = new File("/home/j0hn/Downloads/FromMitya/" + name);
		file.createNewFile();
	}

	public void write(byte[] buffer) throws IOException {
		
		OutputStream writer = new FileOutputStream(file);
		writer.write(buffer);
		writer.close();
	}

}
