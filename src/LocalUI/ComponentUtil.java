package LocalUI;

import java.util.HashMap;
import java.util.Map;

public class ComponentUtil {
	private static Map<String, Object> componentMap = null;
	static {
		if (componentMap == null) {
			componentMap = new HashMap<String, Object>();
		}
	}

	public static void setComponent(String name, Object component) {
		componentMap.put(name, component);
	}

	public static Object getComponent(String name) {
		return componentMap.get(name);
	}
}
