package jdotgen;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import jdotgen.GraphVizNode.Shape;
import jdotgen.Props.Style;
import toools.extern.Proces;
import toools.text.TextUtilities;

public abstract class GraphvizDriver {
	public List<String> outputFormats = List.of("pdf");
	public DOTCFG cfg = DOTCFG.DEFAULT;

	@Override
	public String toString() {
		return toDot();
	}

	public String toDot() {
		var sw = new StringWriter();
		var pw = new PrintWriter(sw);
		toDot(pw);
		pw.flush();
		return sw.toString();
	}

	public void toDot(PrintStream o) {
		toDot(new PrintWriter(o));
	}

	public void toDot(PrintWriter o) {
		o.println("digraph {");

		forEachVertex(v -> {
			o.print("\t" + quote(v.id));

			var p = new ArrayList<String>();
			p.add("penwidth=" + v.penwidth);
			p.add("width=" + v.width);
			p.add("label=" + quote(v.label != null ? v.label : ""));
			p.add("shape=" + quote(v.shape != null ? v.shape : Shape.circle));
			p.add("style=" + quote(v.style != null ? v.style : Style.solid));

			if (v.fillColor != null) {
				if (v.style != Style.filled)
					throw new IllegalStateException();

				p.add("fillcolor=" + quote("#" + Integer.toHexString(v.fillColor.getRGB()).substring(2)));
			}

			if (v.position != null) {
				if (cfg.cmd != DOTCFG.POS.cmd)
					System.err.println("positions won't be used for rendering");

				p.add("pos=" + quote(v.position.x + "," + v.position.y + "!"));
			}

			o.println("\t" + "[" + TextUtilities.concat(", ", p) + "];");
		});

		o.println();

		forEachArc(l -> {
			o.print("\t\"" + l.from + "\" -> \"" + l.to + '"');

			var p = new ArrayList<String>();
			p.add("arrowhead=" + (l.directed ? "open" : "none"));
			p.add("penwidth=" + l.penwidth);
			p.add("taillabel=" + quote(l.labels.tail != null ? l.labels.tail : ""));
			p.add("headlabel=" + quote(l.labels.head != null ? l.labels.head : ""));
			p.add("label=" + quote(l.label != null ? l.label : ""));
			p.add("style=" + quote(l.style != null ? l.style : Style.solid));
			o.println("\t" + "[" + TextUtilities.concat(", ", p) + "];");
		});

		o.println("}");
	}

	private String quote(Object id) {
		return '"' + id.toString() + '"';
	}

	public byte[] to(DOTCFG cmd, OUTPUT_FORMAT outputFormat) {
		if (outputFormat == OUTPUT_FORMAT.dot) {
			return toDot().getBytes();
		} else {
			return to(cmd, toDot(), outputFormat);
		}
	}

	public static class DOTCFG {
		DOTCMD cmd;
		List<String> parms = new ArrayList<>();

		public DOTCFG(DOTCMD c, List<String> p) {
			this.cmd = c;
			this.parms = p;
		}

		public static DOTCFG BASIC = new DOTCFG(DOTCMD.dot, List.of());
		public static DOTCFG DEFAULT = new DOTCFG(DOTCMD.fdp, List.of("-Gmaxiter=10000", "-GK=1"));
		public static DOTCFG POS = new DOTCFG(DOTCMD.neato, List.of("-n"));
	}

	public static byte[] to(DOTCFG cmd, String dot, OUTPUT_FORMAT outputFormat) {
		if (outputFormat == OUTPUT_FORMAT.dot) {
			return dot.getBytes();
		} else {
			return draw(cmd, dot, outputFormat);
		}
	}

	public static String path = null;

	public enum DOTCMD {
		dot, neato, fdp, twopi, circo, osage, patchwork, sfdp
	}

	public enum OUTPUT_FORMAT {
		bmp, canon, dot, gv, xdot, xdot1_2, xdot1_4, cgimage, cmap, eps, exr, fig, gd, gd2, gif, gtk, ico, imap, cmapx,
		imap_np, cmapx_np, ismap, jp2, jpg, jpeg, jpe, pct, pict, pdf, pic, plain_ext, png, pov, ps, ps2, psd, sgi, svg,
		svgz, tga, tif, tiff, tk, vml, vmlz, vrml, wbmp, webp, xlib, x11
	}

	public static byte[] draw(DOTCFG cmd, String dotText, OUTPUT_FORMAT of) {
		var c = cmd.cmd.name();

		if (path != null) {
			c = path + "/" + c;
		}

//		System.out.println("running " + c);
		var parms = new ArrayList<>(cmd.parms);
		parms.add(0, "-T" + of.name());
		return Proces.exec(c, dotText.getBytes(), parms.toArray(new String[0]));
	}

	protected abstract void forEachArc(Consumer<GraphvizArc> e);

	protected abstract void forEachVertex(Consumer<GraphVizNode> v);

}
