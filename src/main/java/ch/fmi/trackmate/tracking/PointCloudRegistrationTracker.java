package ch.fmi.trackmate.tracking;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Vector;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.tracking.SpotTracker;
import mpicbg.imglib.algorithm.scalespace.DifferenceOfGaussianPeak;
import mpicbg.imglib.algorithm.scalespace.DifferenceOfGaussian.SpecialPoint;
import mpicbg.imglib.type.numeric.real.FloatType;
import mpicbg.models.AffineModel3D;
import mpicbg.models.RigidModel3D;
import mpicbg.models.SimilarityModel3D;
import mpicbg.models.TranslationModel3D;
import plugin.DescriptorParameters;
import process.ComparePair;
import process.Matching;
import process.Particle;


public class PointCloudRegistrationTracker implements SpotTracker {

	private SimpleWeightedGraph<Spot, DefaultWeightedEdge> graph;
	private final SpotCollection spots;

	private int minNumInliers;
	private int frameRange;
	private boolean discardLowCoverage;
	private double minCoverage;

	private String errorMessage;

	public PointCloudRegistrationTracker(final SpotCollection spots, int minNumInliers, int frameRange, boolean discardLowCoverage, double minCoverage) {
		this.minNumInliers = minNumInliers;
		this.frameRange = frameRange;
		this.discardLowCoverage = discardLowCoverage;
		this.minCoverage = minCoverage;
		this.spots = spots;
	}

	@Override
	public SimpleWeightedGraph<Spot, DefaultWeightedEdge> getResult() {
		return graph;
	}

	@Override
	public boolean checkInput() {
		if (spots == null) {
			errorMessage = "SpotCollection is null";
			return false;
		}
		return true;
	}

	@Override
	public boolean process() {
		// Create list of peaks
		ArrayList<ArrayList<DifferenceOfGaussianPeak<FloatType>>> peakListList = new ArrayList<>();
		spots.keySet().forEach(f -> {
			peakListList.add(spotsToPeakList(spots.iterable(f, false)));
		});

		// Get parameters for matching
		DescriptorParameters params = createDescriptorParameters(frameRange);

		Vector<ComparePair> comparePairs = Matching.descriptorMatching(peakListList, peakListList.size(), params, 1.0f);

		// Create and populate graph
		// TODO multi-threaded?
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		comparePairs.forEach(pair -> {
			if (pair.inliers.size() < minNumInliers) return;
			ArrayList<Spot> thisFrameSpotList = Lists.newArrayList(spots.iterable(pair.indexA, false));
			ArrayList<Spot> otherFrameSpotList = Lists.newArrayList(spots.iterable(pair.indexB, false));
			pair.inliers.forEach(pointMatch -> {
				long thisIndex = ((Particle) pointMatch.getP1()).getID();
				long otherIndex = ((Particle) pointMatch.getP2()).getID() - peakListList.get(pair.indexA).size();
				Spot thisSpot = thisFrameSpotList.get((int) thisIndex);
				Spot otherSpot = otherFrameSpotList.get((int) otherIndex);
				graph.addVertex(thisSpot);
				graph.addVertex(otherSpot);
				DefaultWeightedEdge edge = graph.addEdge(thisSpot, otherSpot);
				graph.setEdgeWeight(edge, pair.model.getCost());
			});
		});

		return true;
	}

	private DescriptorParameters createDescriptorParameters(int range) {
		DescriptorParameters params = new DescriptorParameters();
		params.model = new RigidModel3D();
		params.dimensionality = 3;
		params.numNeighbors = 3;
		params.significance = 3.0;
		params.similarOrientation = true;
		params.ransacThreshold = 5;
		params.redundancy = 1;
		params.globalOpt = 1; // all-to-all within range
		params.range = range;
		return params;
	}

	private ArrayList<DifferenceOfGaussianPeak<FloatType>> spotsToPeakList(Iterable<Spot> iterable) {
		ArrayList<DifferenceOfGaussianPeak<FloatType>> list = new ArrayList<>();
		double[] realPosition = new double[3];
		int[] position = new int[3];
		iterable.forEach(spot -> {
			list.add(createPeak(spot, realPosition, position));
		});
		return list;
	}

	/**
	 * Creates a new {@code DifferenceOfGaussianPeak} for a TrackMate {@code Spot}
	 * 
	 * TODO move this into a common utility class
	 * 
	 * @param spot {@code Spot} to be converted
	 * @param realPos empty array to hold the real coordinates of the spot
	 * @param pos empty array to hold the integer coordinates of the spot
	 * @return a {@code DifferenceOfGaussianPeak} corresponding to the input {@code Spot}
	 */
	private DifferenceOfGaussianPeak<FloatType> createPeak(Spot spot,
		double[] realPos, int[] pos)
	{
		spot.localize(realPos);
		for (int i = 0; i < realPos.length; i++) {
			pos[i] = (int) realPos[i];
		}
		DifferenceOfGaussianPeak<FloatType> p = new DifferenceOfGaussianPeak<>(pos,
			new FloatType(), SpecialPoint.MAX);
		for (int d = 0; d < realPos.length; d++) {
			p.setSubPixelLocationOffset((float) (realPos[d] - pos[d]), d);
		}
		return p;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setNumThreads() {
		// Ignore for now
	}

	@Override
	public void setNumThreads(int numThreads) {
		// Ignore for now
	}

	@Override
	public int getNumThreads() {
		return 1;
	}

	@Override
	public void setLogger(Logger logger) {
		// Ignore for now
	}

}
