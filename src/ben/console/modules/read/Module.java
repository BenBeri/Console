package ben.console.modules.read;

public class Module {

	private String name;
	private String jar;
	private String command;
	private String main;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setJar(String path) {
		this.jar = path;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getJar() {
		return this.jar;
	}
	
	public String getUsageCommand() {
		return this.command;
	}

	public void setMainClass(String c) {
		this.main = c;
	}
	
	public String getMainClass() {
		return this.main;
	}

}
