package jdotgen;

import java.io.PrintStream;

public class Vertex implements Dottable {
	public enum Shape {
		round, box
	}

	public String id, label;
	public Shape shape;
	public String subgraph;

	public void toDot(PrintStream o) {
		o.print("\t" + id);

		if (label != null) {
			o.print("\t" + label);
		}

		o.println(";");
	}

}
