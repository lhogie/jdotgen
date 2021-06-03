package jdotgen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import toools.text.TextUtilities;

public class Edge implements Dottable {
	String from;
	List<String> to = new ArrayList<>();
	String style;
	boolean directed;

	@Override
	public void toDot(PrintStream o) {
		var tos = "{" + TextUtilities.concatene(to, " ") + "}";
		o.println("\t" + from + (directed ? " -> " : " -- ") + tos + ";");
	}
}
