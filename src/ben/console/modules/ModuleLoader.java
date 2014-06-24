package ben.console.modules;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import ben.console.modules.read.Module;

public class ModuleLoader {

	private Class<?> cls;
	
	public void initializeCommandModule(Module m) throws Exception {
		URL url = this.getURL(m.getJar());
		this.cls = this.loadClass(m.getMainClass(), url);
	}
	
	/**
	 * Executes the main method in the module library
	 * @param m		Module	Module Object
	 * @param args	String[] Arguements
	 * @return Integer response number
	 * @throws Exception
	 */
	public int execute(Module m, ArrayList<String> args) throws Exception {
		Method method = this.cls.getDeclaredMethod("execute", ArrayList.class);
		return (int) method.invoke(this.cls.newInstance(), args);
	}
	
	/**
	 * If the main method returns more than '0', means it needs a response.
	 * Use this method to respond to the library.
	 * @param args String[]	Arguements
	 * @return Integer response number
	 * @throws Exception
	 */
	public int respond(ArrayList<String> args) throws Exception {
		Method method = this.cls.getDeclaredMethod("response", ArrayList.class);
		return (int) method.invoke(this.cls.newInstance(), args);
	}
	
	/**
	 * Loads a new class with reflection
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private Class<?> loadClass(String cls, URL url) throws ClassNotFoundException, IOException {
		URLClassLoader loader = new URLClassLoader(new URL[]{url});
		Class<?> toReturn = loader.loadClass(cls);
		loader.close();
		
		return toReturn;
	}
	
	/**
	 * Gets the URL for the jar lib
	 */
	private URL getURL(String jar) throws MalformedURLException {
		return new File(jar).toURI().toURL();
	}

}
