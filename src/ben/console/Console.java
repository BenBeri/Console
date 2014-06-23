package ben.console;

import javax.xml.parsers.ParserConfigurationException;

import ben.console.modules.read.ModuleReader;

public class Console {

	public Console() throws Exception {
		ModuleReader reader = new ModuleReader();
		reader.readModules();
	}
	
	public void listen() {
		
	}
	
	public static void main(String[] args) throws Exception {
		Console con = new Console();
		con.listen();
	}

}
