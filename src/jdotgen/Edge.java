package jdotgen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import toools.text.TextUtilities;

public class Edge implements Dottable {
	public enum Style {
		plain, dotted
	};

	public String from;
	public final List<String> to = new ArrayList<>();
	public String style;
	public boolean directed;

	@Override
	public void toDot(PrintStream o) {
		var tos = to.size() == 1 ? to.get(0) : "{" + TextUtilities.concatene(to, " ") + "}";
		o.println("\t" + from + (directed ? " -> " : " -- ") + tos + ";");
	}
}
