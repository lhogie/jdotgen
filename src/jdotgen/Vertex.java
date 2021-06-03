package jdotgen;

import java.io.PrintStream;

public class Vertex implements Dottable {
	public enum Shape {
		round, box
	}

	public String id, label;
	public Shape shape;

	public void toDot(PrintStream o) {
		o.println("\t" + id + "\t" + (label == null ? "" : "label=" + label) + ";");
	}

}
