package ch.fmi;

import java.util.Iterator;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;

public class MaxQualitySpotAnalyzer<T> implements SpotAnalyzer<T> {

	private final Model model;
	private final int frame;
	private String errorMessage;
	private long processingTime;

	public MaxQualitySpotAnalyzer(final Model model, final int frame) {
		this.model = model;
		this.frame = frame;
	}

	@Override
	public boolean checkInput() {
		return true;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public boolean process() {
		final SpotCollection sc = model.getSpots();

		/*
		 * First iteration to get max quality
		 */
		Iterator<Spot> spotIt = sc.iterator(frame, false);
		double max = Double.NEGATIVE_INFINITY;
		while (spotIt.hasNext()) {
			final double quality = spotIt.next().getFeature(Spot.QUALITY);
			if (quality > max)
				max = quality;
		}

		/*
		 * Second iteration to label spot with max quality
		 */
		spotIt = sc.iterator(frame, false);
		while (spotIt.hasNext()) {
			Spot spot = spotIt.next();
			spot.putFeature(MaxQualitySpotAnalyzerFactory.HAS_MAX_QUALITY_IN_FRAME,
					spot.getFeature(Spot.QUALITY) < max ? Double.valueOf(0) : Double.valueOf(1));
		}

		return true;
	}

	@Override
	public long getProcessingTime() {
		return processingTime;
	}

}
