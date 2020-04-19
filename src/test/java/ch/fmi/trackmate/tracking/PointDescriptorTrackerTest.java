package ch.fmi.trackmate.tracking;

import static org.junit.Assert.assertEquals;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Test;

import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;

public class PointDescriptorTrackerTest {

	@Test
	public void testPruned() {
		boolean doPrune = true;
		// setup SpotCollection and Model
		SpotCollection spotCollection = createTestSpots();
		// Run tracker
		PointDescriptorTracker tracker = new PointDescriptorTracker(spotCollection, 3, 3, 3, 10.0, 10.0, doPrune);
		tracker.setLogger(Logger.DEFAULT_LOGGER);
		tracker.process();
		SimpleWeightedGraph<Spot, DefaultWeightedEdge> graph = tracker.getResult();
		// Verify tracks (connected sets)
		assertEquals(16, graph.edgeSet().size()); // this is in the pruned graph
	}

	@Test
	public void testRaw() {
		boolean doPrune = false;
		// setup SpotCollection and Model
		SpotCollection spotCollection = createTestSpots();
		// Run tracker
		PointDescriptorTracker tracker = new PointDescriptorTracker(spotCollection, 3, 3, 3, 10.0, 10.0, doPrune);
		tracker.setLogger(Logger.DEFAULT_LOGGER);
		tracker.process();
		SimpleWeightedGraph<Spot, DefaultWeightedEdge> graph = tracker.getResult();
		// Verify tracks (connected sets)
		assertEquals(36, graph.edgeSet().size()); // this is in the pruned graph
	}

	private SpotCollection createTestSpots() {
		SpotCollection spots = new SpotCollection();
		double r = 1;
		double quality = 1;
		int frame = 0;
		spots.add(new Spot(0.0, 0.0, 0.0, r, quality), frame);
		spots.add(new Spot(1.0, 4.0, 0.0, r, quality), frame);
		spots.add(new Spot(4.0, 0.0, 0.0, r, quality), frame);
		spots.add(new Spot(6.0, 3.0, 0.0, r, quality), frame);
		frame++; // 1
		spots.add(new Spot(0.1, 0.0, 0.0, r, quality), frame);
		spots.add(new Spot(1.0, 3.9, 0.0, r, quality), frame);
		spots.add(new Spot(4.1, 0.0, 0.0, r, quality), frame);
		spots.add(new Spot(6.0, 3.1, 0.0, r, quality), frame);
		frame++; // 2
		spots.add(new Spot(0.1, -0.1, 0.0, r, quality), frame);
		spots.add(new Spot(1.1, 3.9, 0.0, r, quality), frame);
		spots.add(new Spot(4.1, 0.1, 0.0, r, quality), frame);
		spots.add(new Spot(5.9, 3.1, 0.0, r, quality), frame);
		frame++; // 3
		spots.add(new Spot(0.0, -0.1, 0.0, r, quality), frame);
		spots.add(new Spot(1.1, 4.0, 0.0, r, quality), frame);
		spots.add(new Spot(4.0, 0.1, 0.0, r, quality), frame);
		spots.add(new Spot(5.9, 3.0, 0.0, r, quality), frame);
		frame++; // 4
		spots.add(new Spot(-0.1, -0.1, 0.0, r, quality), frame);
		spots.add(new Spot(1.1, 4.1, 0.0, r, quality), frame);
		spots.add(new Spot(3.9, 0.1, 0.0, r, quality), frame);
		spots.add(new Spot(5.9, 2.9, 0.0, r, quality), frame);
		// TODO frames 5-8

		return spots;
	}

}
