package ben.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ben.console.modules.ModuleLoader;
import ben.console.modules.read.Module;
import ben.console.modules.read.ModuleReader;

public class CommandProcessor {

	private ModuleReader reader = new ModuleReader();
	private ModuleLoader loader = new ModuleLoader();
	
	private Scanner scanner;
	
	public CommandProcessor(Scanner s) throws Exception {
		this.reader.readModules();
		this.scanner = s;
	}
	
	public void process(String cmd) throws Exception {
		ArrayList<String> arguements = new ArrayList<String>();
		String[] args = cmd.split(" ");
		
		String command = args[0];
		
		arguements = this.convert(args, 1);
		
		for (Module module : this.reader.getModules()) {
			this.loader.initializeCommandModule(module);
			if (!module.getUsageCommand().equalsIgnoreCase(command)) {
				continue;
			}
			
			int response = this.loader.execute(module, arguements);
			
			while (response != Configuration.COMMAND_COMPLETE) {
				String in = this.scanner.next();
				response = this.loader.respond(this.convert(in.split(" "), 0));
			}
			return;
		}
		
		System.out.println("Command was not found \" " + command + " \"");
	}
	
	private ArrayList<String> convert(String[] args, int startIndex) {
		ArrayList<String> arguements = new ArrayList<String>();
		for (int i = startIndex; i < args.length; i++) {
			arguements.add(args[i]);
		}	
		
		return arguements;
	}
	
}
