package jdotgen;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import toools.extern.Proces;
import toools.text.TextUtilities;

public abstract class GraphvizDriver {

	VertexProps v = new VertexProps();
	EdgeProps e = new EdgeProps();

	@Override
	public String toString() {
		return toDot();
	}

	public String toDot() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		toDot(pw);
		pw.flush();
		return sw.toString();
	}

	public static interface Validator {
		void f();
	}

	public void toDot(PrintStream o) {
		toDot(new PrintWriter(o));
	}

	public void toDot(PrintWriter o) {
		o.println("digraph {");

		findVertices(v, () -> {
			o.print("\t" + v.id);
			var p = new ArrayList<String>();

			if (v.label != null) {
				p.add("label=" + v.label);
			}

			if (v.shape != null) {
				p.add("shape=" + v.shape);
			}

			if (v.style != null) {
				p.add("style=" + v.style);
			}

			if (!p.isEmpty()) {
				o.println("\t" + "[" + TextUtilities.concat(", ", p) + "]");
			}

			o.println(";");
		});

		findEdges(e, () -> {
			o.print("\t" + e.from + " -> " + e.to);
			
			var p = new ArrayList<String>();

			if (!e.directed) {
				p.add("edgetail=none");
			}

			if (e.style != null) {
				p.add("style=" + v.style);
			}
			
			if (!p.isEmpty()) {
				o.println("\t" + "[" + TextUtilities.concat(", ", p) + "]");
			}

			o.println(";");
		});

		o.println("}");
	}

	public byte[] toPDF() {
		return draw(COMMAND.dot, toString(), OUTPUT_FORMAT.pdf);
	}

	public static String pathToCommands = null;

	public enum COMMAND {
		dot, neato, fdp, twopi, circo, osage, patchwork, sfdp
	}

	public enum OUTPUT_FORMAT {
		bmp, canon, dot, gv, xdot, xdot1_2, xdot1_4, cgimage, cmap, eps, exr, fig, gd, gd2, gif, gtk, ico, imap, cmapx,
		imap_np, cmapx_np, ismap, jp2, jpg, jpeg, jpe, pct, pict, pdf, pic, plain_ext, png, pov, ps, ps2, psd, sgi, svg,
		svgz, tga, tif, tiff, tk, vml, vmlz, vrml, wbmp, webp, xlib, x11
	}

	public static byte[] draw(COMMAND cmd, String dotText, OUTPUT_FORMAT of) {
		var c = cmd.name();

		if (pathToCommands != null) {
			c = pathToCommands + "/" + cmd;
		}

		System.out.println("running " + c);
		return Proces.exec(c, dotText.getBytes(), "-T" + of.name());
	}

	protected abstract void findEdges(EdgeProps e, Validator f);

	protected abstract void findVertices(VertexProps v, Validator f);

}
