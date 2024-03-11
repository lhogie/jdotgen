package jdotgen;

import java.awt.Color;

public class GraphVizNode extends Props {
	public enum Shape {
		circle, box
	}

	public static class Position {
		public double x;
		public double y;
	}

	public Shape shape = Shape.circle;
	public Object id;
	public double width = 1;
	public Position position;
	public Color fillColor;
}
