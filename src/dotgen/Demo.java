package dotgen;

import java.util.ArrayList;

import toools.io.file.RegularFile;

public class Demo {
	public static void main(String[] args) {
		final var text = "salut";

		var w = new DotWriter() {
			@Override
			protected void findEdges(EdgeConsumer e) {
				for (int i = 0; i < text.length() - 1; ++i) {
					var l = new ArrayList<String>();

					for (int j = i + 1; j < text.length(); ++j) {
						l.add("" + text.charAt(j));
					}

					e.accept("" + text.charAt(i), l, true, "line");
				}

			}

			@Override
			protected void findVertices(VertexConsumer e) {
				for (int i = 0; i < text.length(); ++i) {
					e.accept("" + text.charAt(i), null);
				}
			}

			@Override
			protected boolean isDirected() {
				return true;
			}
		};

		w.toDot(System.out);

		new RegularFile("$HOME/a.pdf").setContent(w.toPDF());

	}
}
