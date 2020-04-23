package ch.fmi.trackmate.tracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import fiji.plugin.trackmate.Spot;

public class Tracks {
	private Tracks() {
		// prevent instantiation of static utility class
	}

	public static SimpleWeightedGraph<Spot, DefaultWeightedEdge> prune(SimpleWeightedGraph<Spot, DefaultWeightedEdge> graph, boolean setWeights) {
		
		SimpleWeightedGraph<Spot, DefaultWeightedEdge> prunedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		ConnectivityInspector<Spot, DefaultWeightedEdge> graphInspector = new ConnectivityInspector<>(graph);
		// Consider changing to FloydWarshallShortestPaths
		DijkstraShortestPath<Spot, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);

		List<Set<Spot>> connectedSets = graphInspector.connectedSets();

		for (Set<Spot> track : connectedSets) {
			// Sorting necessary?
			List<Spot> trackSpots = new ArrayList<>(track);
			Collections.sort(trackSpots, (s1, s2) -> {
				return s1.getFeature(Spot.FRAME).compareTo(s2.getFeature(Spot.FRAME));
			});

			// loop through list
			for (int i = 0; i < trackSpots.size(); i++) {
				Spot source = trackSpots.get(i);
				int sourceSpotFrame = source.getFeature(Spot.FRAME).intValue();
				int firstLinkedFrame = Integer.MAX_VALUE;
				for (int j = i + 1; j < trackSpots.size(); j++) {
					Spot target = trackSpots.get(j);
					int targetSpotFrame = target.getFeature(Spot.FRAME).intValue();
					if (targetSpotFrame > firstLinkedFrame) break;
					if (targetSpotFrame > sourceSpotFrame) {
						firstLinkedFrame = targetSpotFrame;
						prunedGraph.addVertex(source);
						prunedGraph.addVertex(target);
						DefaultWeightedEdge edge = prunedGraph.addEdge(source, target);
						if (setWeights) {
							prunedGraph.setEdgeWeight(edge, shortestPath.getPathWeight(source, target));
						} else {
							prunedGraph.setEdgeWeight(edge, -1);
						}
					}
				}
			}
		}

		return prunedGraph;
	}

}
