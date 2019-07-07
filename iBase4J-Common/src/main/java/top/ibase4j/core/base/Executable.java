package top.ibase4j.core.base;

import java.io.IOException;

@FunctionalInterface
public interface Executable {
	
	Object exec() throws IOException;
	
}
