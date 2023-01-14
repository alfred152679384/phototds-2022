package um.tds.phototds.pruebas;

import java.util.LinkedList;
import java.util.List;

public class DetectarHashtag {
	private static List<String> detectarHashtags(String s) {
		List<String> list = new LinkedList<>();
		String aux = s;
		String hashtag = "";
		int i, f = 0;
		while ((i = aux.indexOf("#")) != -1) {
			aux = aux.substring(i);
			i = 0;
			f = aux.indexOf(" ");
			if (f == -1)
				f = aux.length();
			hashtag = aux.substring(i, f);
			list.add(hashtag);
			aux = aux.substring(f);
		}
		return list;
	}

}
