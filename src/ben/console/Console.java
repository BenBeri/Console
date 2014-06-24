package ben.console;

import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import ben.console.modules.read.ModuleReader;

public class Console {

	private CommandProcessor commands;
	
	private boolean inCommand = false;
	private Scanner scanner = new Scanner(System.in);
	
	public Console() throws Exception {
		this.commands = new CommandProcessor(this.scanner);
	}
	
	public void listen() throws Exception {
		System.out.println("Loaded console successfully");
		String in;
		
		while ( !((in = this.scanner.next()).equalsIgnoreCase("exit") && !inCommand) ) {
			this.commands.process(in);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Console con = new Console();
		con.listen();
	}

}
