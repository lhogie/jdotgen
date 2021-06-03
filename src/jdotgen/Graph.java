package jdotgen;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import toools.gui.GraphViz;
import toools.gui.GraphViz.COMMAND;
import toools.gui.GraphViz.OUTPUT_FORMAT;

public abstract class Graph {

	public enum Style {
		plain, dotted
	};

	public void toDot(PrintStream o) {
		o.println((isDirected() ? "di" : "") + "graph {");
		freeLines().forEach(l -> o.println(l));
		vertices().forEach(u -> u.toDot(o));
		edges().forEach(e -> e.toDot(o));
		o.println("}");
	}

	public byte[] toPDF() {
		return GraphViz.toBytes(COMMAND.dot, toString(), OUTPUT_FORMAT.pdf);
	}

	protected abstract boolean isDirected();

	protected abstract Set<Edge> edges();

	protected abstract Set<Vertex> vertices();

	public List<String> freeLines() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String toString() {
		var bos = new ByteArrayOutputStream();
		var ps = new PrintStream(bos);
		toDot(ps);
		ps.close();
		return new String(bos.toByteArray());
	}

}
