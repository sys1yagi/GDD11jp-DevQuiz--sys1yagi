package devquiz2011.sys1yagi.challenge.bfs.util;

public class Util {

	public static void close(Object hasCloseMethod) {
		if (hasCloseMethod == null) {
			return;
		}
		try {
			hasCloseMethod.getClass().getMethod("close", (Class<?>[]) null).invoke(hasCloseMethod, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
