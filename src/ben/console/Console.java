package ben.console;

import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import ben.console.modules.read.ModuleReader;

/**
 * Console Application
 * This application lets you remotely add libraries for each command you wish.
 * The command's logic can contain as many sub-commands as you want, and can be
 * at any size for your needs.
 * 
 * @author Ben
 *
 */
public class Console {

	private CommandProcessor commands;
	
	private boolean inCommand = false;
	private Scanner scanner = new Scanner(System.in);
	
	public Console() throws Exception {
		this.commands = new CommandProcessor(this.scanner);
	}
	
	/**
	 * Listen for new commands
	 * @throws Exception
	 */
	public void listen() throws Exception {
		System.out.println("Loaded console successfully");
		String in;
		
		// Checks if the newly entered command is not equal to "exit", otherwise it will
		// exit the system.
		while ( !(in = this.scanner.next()).equalsIgnoreCase("exit") ) {
			// Process the command
			this.commands.process(in);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Console con = new Console();
		con.listen();
	}

}
