package jdotgen;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import toools.gui.GraphViz;
import toools.gui.GraphViz.COMMAND;
import toools.gui.GraphViz.OUTPUT_FORMAT;
import toools.text.TextUtilities;

public abstract class DotWriter {
	@Override
	public String toString() {
		var bos = new ByteArrayOutputStream();
		var ps = new PrintStream(bos);
		toDot(ps);
		ps.close();
		return new String(bos.toByteArray());
	}

	public enum Shape {
		round, box
	}

	public enum Style {
		plain, dotted
	};

	public void toDot(PrintStream o) {
		o.println((isDirected() ? "di" : "") + "graph {");

		findVertices((id, label) -> {
			o.println("\t" + id + "\t" + (label == null ? "" : "label=" + label) + ";");
		});

		findEdges((from, to, directed, style) -> {
			var tos = "{" + TextUtilities.concatene(to, " ") + "}";
			o.println("\t" + from + (directed ? " -> " : " -- ") + tos + ";");
		});

		o.println("}");
	}

	public byte[] toPDF() {
		return GraphViz.toBytes(COMMAND.dot, toString(), OUTPUT_FORMAT.pdf);
	}

	protected abstract boolean isDirected();

	protected abstract void findEdges(EdgeConsumer e);

	protected abstract void findVertices(VertexConsumer e);

}
