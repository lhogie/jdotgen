package jdotgen;

public class GraphvizArc extends Props {
	public Object from, to;
	public boolean directed = true;
	public final LinkEndsLabels labels = new LinkEndsLabels();

	public boolean isLoop() {
		return from.equals(to);
	}
}
