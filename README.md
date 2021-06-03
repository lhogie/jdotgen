# jdotgen

JDotGen is a tiny Java library aiming at facilitating the generation of GraphViz diagrams from Java programs.

This [demo]{https://github.com/lhogie/jdotgen/blob/main/src/jdotgen/Demo.java} will generate the following output:
```
digraph {
	o;
	r;
	H;
	d;
	o;
	l;
	l;
	e;
	l;
	w;
	e -> l;
	l -> d;
	r -> l;
	l -> l;
	w -> o;
	H -> e;
	o -> w;
	o -> r;
	l -> o;
}
```
