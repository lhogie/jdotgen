package jdotgen.demo;

import java.util.HashSet;
import java.util.Set;

import jdotgen.Edge;
import jdotgen.Graph;
import jdotgen.Vertex;
import toools.io.file.RegularFile;

public class GraphOfLetters {
	public static void main(String[] args) {
		final var text = "Hello world!";

		var w = new Graph() {
			@Override
			protected boolean isDirected() {
				return true;
			}

			@Override
			protected Set<Edge> edges() {
				var r = new HashSet<Edge>();
				String t = text.replaceAll("[^a-zA-Z0-9]", "");

				for (int i = 0; i < t.length() - 1; ++i) {
					var e = new Edge();
					e.directed = isDirected();
					e.from = "" + t.charAt(i);
					e.to.add("" + t.charAt(i + 1));
					r.add(e);
				}

				return r;
			}

			@Override
			protected Set<Vertex> vertices() {
				var r = new HashSet<Vertex>();
				String t = text.replaceAll("[^a-zA-Z0-9]", "");

				for (int i = 0; i < t.length(); ++i) {
					var v = new Vertex();
					v.id = "" + t.charAt(i);
					r.add(v);
				}

				return r;
			}
		};

		System.out.println(w);
		new RegularFile("example-output.pdf").setContent(w.toPDF());

	}
}
