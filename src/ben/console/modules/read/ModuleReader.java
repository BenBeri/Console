package ben.console.modules.read;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ben.console.Configuration;

public class ModuleReader {

	private List<Module> modules = new ArrayList<Module>();
	private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder xmlReader;
	private Element currentElement;
	
	public ModuleReader() throws ParserConfigurationException {
		this.xmlReader = dbFactory.newDocumentBuilder();
	}
	
	public void readModules() throws Exception {
		File directory = new File(Configuration.moduleFolder);
		if (directory.isDirectory()) {
			File[] modules = directory.listFiles();
			for (File module : modules) {
				
				Module b = this.initializeModuleObject(module);
				this.modules.add(b);
				System.out.println(b.getJar());
				File h = new File(b.getJar());

				URLClassLoader loader = new URLClassLoader(new URL[]{
						new File(b.getJar()).toURI().toURL()
				});
				Runnable run = (Runnable) loader.loadClass(b.getMainClass()).newInstance();
				run.run();
				loader.close();
			}
		}
		else {
			throw new Exception("Module directory not found");
		}
		
		this.log("Loaded modules, total modules loaded: " + this.modules.size());
	}
	
	public List<Module> getModules() {
		return this.modules;
	}
	
	private Module initializeModuleObject(File module) throws SAXException, IOException {
		Module mod = new Module();
		
		Document doc = this.xmlReader.parse(module);
		doc.getDocumentElement().normalize();
		
		NodeList list = doc.getElementsByTagName("libraries");
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				this.currentElement = (Element) node;
				
				mod.setName(this.getElementText("name"));
				mod.setMainClass(this.getElementText("mainClass"));
				mod.setJar(this.getElementText("jar"));
				mod.setCommand(this.getElementText("command"));
			}
		}
		
		System.out.print(mod.getName());
		return mod;
	}
	
	private void log(String m) {
		System.err.println("[ModuleReader] " + m);
	}

	private String getElementText(String tag) {
		return this.currentElement.getElementsByTagName(tag).item(0).getTextContent();
	}
}
