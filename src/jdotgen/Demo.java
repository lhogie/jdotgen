package jdotgen;

import java.util.function.Consumer;

import jdotgen.GraphvizDriver.DOTCFG;
import jdotgen.GraphvizDriver.OUTPUT_FORMAT;
import jdotgen.Props.Style;
import toools.io.file.RegularFile;

public class Demo {
	public static void main(String[] args) {
		final var text = "salut";

		var w = new GraphvizDriver() {

			@Override
			protected void forEachArc(Consumer<GraphvizArc> c) {
				for (int i = 0; i < text.length() - 1; ++i) {
					var a = new GraphvizArc();
					a.from = i;
					a.to = i + 1;
					a.label = "" + text.charAt(i);
					a.directed = true;
					a.style = Style.dotted;
					c.accept(a);
				}
			}

			@Override
			protected void forEachVertex(Consumer<GraphVizNode> c) {
				var v = new GraphVizNode();

				for (int i = 0; i < text.length(); ++i) {
					v.id = i;
					v.label = "" + text.charAt(i);
					c.accept(v);
				}
			}

		};

		System.out.println(w.toDot());

		GraphvizDriver.path = "/usr/local/bin/";
		var img = new RegularFile("$HOME/a.pdf");
		img.setContent(w.to(DOTCFG.BASIC, OUTPUT_FORMAT.pdf));
		img.open();
	}
}
