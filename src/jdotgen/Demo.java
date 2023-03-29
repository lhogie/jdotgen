package jdotgen;

import jdotgen.Props.Style;
import toools.io.file.RegularFile;

public class Demo {
	public static void main(String[] args) {
		final var text = "salut";

		var w = new DotWriter() {
			@Override
			protected void findVertices(VertexProps v, Validator f) {
				for (int i = 0; i < text.length(); ++i) {
					v.id = i;
					v.label = text.charAt(i);
					f.f();
				}
			}

			@Override
			protected void findEdges(EdgeProps v, Validator f) {
				for (int i = 0; i < text.length() - 1; ++i) {
					v.from = i;
					v.to = i + 1;
					v.label = "" + text.charAt(i);
					v.directed = true;
					v.style = Style.dotted;
					f.f();
				}
			}

		};

		System.out.println(w.toDot());

		DotWriter.pathToCommands = "/usr/local/bin/";
		var img = new RegularFile("$HOME/a.pdf");
		img.setContent(w.toPDF());
		img.open();
	}
}
