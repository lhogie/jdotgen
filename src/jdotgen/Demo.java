package jdotgen;

import java.util.List;

import toools.io.file.RegularFile;

public class Demo {
	public static void main(String[] args) {
		final var text = "This is just an example text!!!";

		var w = new DotWriter() {
			@Override
			protected void findEdges(EdgeConsumer e) {
				String t = text.replaceAll("[^a-zA-Z0-9]", "");

				for (int i = 0; i < t.length() - 1; ++i) {
					e.accept("" + t.charAt(i), List.of("" + t.charAt(i+1)), true, "line");
				}
			}

			@Override
			protected void findVertices(VertexConsumer e) {
				String t = text.replaceAll("[^a-zA-Z0-9]", "");

				for (int i = 0; i < t.length(); ++i) {
					e.accept("" + t.charAt(i), null);
				}
			}

			@Override
			protected boolean isDirected() {
				return true;
			}
		};

		System.out.println(w);
		System.out.println(w.toPDF().length + " bytes");
		new RegularFile("example-output.pdf").setContent(w.toPDF());

	}
}
