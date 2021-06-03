package jdotgen;

import java.util.ArrayList;

import toools.io.file.RegularFile;

public class Demo {
	public static void main(String[] args) {
		final var text = "This is just an example text!!!";

		var w = new DotWriter() {
			@Override
			protected void findEdges(EdgeConsumer e) {
				String t = text.replaceAll("[^a-zA-Z0-9]", "");
				
				for (int i = 0; i < t.length() - 1; ++i) {
					var l = new ArrayList<String>();

					for (int j = i + 1; j < t.length(); ++j) {
						l.add( ""+t.charAt(j));
					}

					e.accept("" + t.charAt(i), l, true, "line");
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
System.out.println(w.toPDF().length +  " bytes");
		new RegularFile("$HOME/a.pdf").setContent(w.toPDF());

	}
}
