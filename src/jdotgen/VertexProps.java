package jdotgen;

public class VertexProps extends Props {
	public enum Shape {
		circle, box
	}

	Shape shape = Shape.circle;
	int size = 10;
	Object id;
}
