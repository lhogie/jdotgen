package jdotgen;

import java.util.List;

public interface EdgeConsumer {
	void accept(String from, List<String> to, boolean directed, String style);
}
