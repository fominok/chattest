import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket server;
	private Socket client;
	private FileWriter fileWriter;

	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		fileWriter = new FileWriter();
	}

	public void handleFile() throws IOException {

		byte[] buffer = new byte[4096];
		InputStream socketReader;
		String s;
		int n;
		
		client = server.accept();

		System.out.println("client connected");

		BufferedReader fileNameReader = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
		s = fileNameReader.readLine();
//		fileNameReader.close();

		System.out.println("Transfering file " + s);

		fileWriter.createFile(s);		
		OutputStream writer = new FileOutputStream("/home/j0hn/Downloads/FromMitya/" + s);		
		
		socketReader = client.getInputStream();

		while ((n = socketReader.read(buffer)) >= 0) {
			writer.write(buffer, 0, n);			
		}
		
		socketReader.close();
		writer.close();
	}

	public void close() throws IOException {
		server.close();
	}
}
