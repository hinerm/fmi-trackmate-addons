package ch.fmi.trackmate.tracking;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.tracking.SpotTracker;
import fiji.plugin.trackmate.tracking.SpotTrackerFactory;

@Plugin(type = SpotTrackerFactory.class)
public class PointCloudRegistrationTrackerFactory implements
	SpotTrackerFactory
{

	private static final String INFO_TEXT = "<html>This tracker uses descriptor-based registration to link points between point clouds.</html>";
	private static final String KEY = "POINT_CLOUD_REGISTRATION_TRACKER";
	private static final String NAME = "Point-cloud registration tracker";

	// Minimal number of inlier spots per comparison
	static final String MIN_NUM_INLIERS = "MIN_NUM_INLIERS";
	// Range of frame intervals being compared
	static final String FRAME_RANGE = "FRAME_RANGE";
	// Discard pairs with low field-of-view coverage
	static final String DISCARD_LOW_COVERAGE = "DISCARD_LOW_COVERAGE";
	// Fraction of each image dimension that has to be covered by inliers
	static final String MIN_COVERAGE_FACTOR = "MIN_COVERAGE_FACTOR";

	static final Integer DEFAULT_MIN_NUM_INLIERS = 10;
	static final Integer DEFAULT_FRAME_RANGE = 10;
	static final Boolean DEFAULT_DISCARD_LOW_COVERAGE = true;
	static final Double DEFAULT_MIN_COVERAGE_FACTOR = 0.2;

	private String errorMessage;

	@Override
	public String getInfoText() {
		return INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public SpotTracker create(SpotCollection spots,
		Map<String, Object> settings)
	{
		int minNumInliers = (int) settings.get(MIN_NUM_INLIERS);
		int frameRange = (int) settings.get(FRAME_RANGE);
		boolean discardLowCoverage = (boolean) settings.get(DISCARD_LOW_COVERAGE);
		double minCoverage = (double) settings.get(MIN_COVERAGE_FACTOR);
		return new PointCloudRegistrationTracker(spots, minNumInliers, frameRange, discardLowCoverage, minCoverage);
	}

	@Override
	public ConfigurationPanel getTrackerConfigurationPanel(Model model) {
		return new PointCloudRegistrationTrackerConfigPanel();
	}

	@Override
	public boolean marshall(Map<String, Object> settings, Element element) {
		if (!checkSettingsValidity(settings)) return false;

		int minNumInliers = (int) settings.get(MIN_NUM_INLIERS);
		element.setAttribute(MIN_NUM_INLIERS, "" + minNumInliers);
		int frameRange = (int) settings.get(FRAME_RANGE);
		element.setAttribute(FRAME_RANGE, "" + frameRange);
		boolean discardLowCoverage = (boolean) settings.get(DISCARD_LOW_COVERAGE);
		element.setAttribute(DISCARD_LOW_COVERAGE, "" + discardLowCoverage);
		double minCoverage = (double) settings.get(MIN_COVERAGE_FACTOR);
		element.setAttribute(MIN_COVERAGE_FACTOR, "" + minCoverage);

		return true;
	}

	@Override
	public boolean unmarshall(Element element, Map<String, Object> settings) {
		try {
			int minNumInliers = element.getAttribute(MIN_NUM_INLIERS).getIntValue();
			settings.put(MIN_NUM_INLIERS, minNumInliers);

			int frameRange = element.getAttribute(FRAME_RANGE).getIntValue();
			settings.put(FRAME_RANGE, frameRange);

			boolean discardLowCoverage = element.getAttribute(DISCARD_LOW_COVERAGE).getBooleanValue();
			settings.put(DISCARD_LOW_COVERAGE, discardLowCoverage);

			double minCoverage = element.getAttribute(MIN_COVERAGE_FACTOR).getDoubleValue();
			settings.put(MIN_COVERAGE_FACTOR, minCoverage);
		}
		catch (DataConversionException exc) {
			errorMessage = "Error retrieving settings from XML: " + exc.toString();
			return false;
		}
		return true;
	}

	@Override
	public String toString(Map<String, Object> sm) {
		if (!checkSettingsValidity(sm)) return errorMessage;

		StringBuilder str = new StringBuilder();
		int minNumInliers = (int) sm.get(MIN_NUM_INLIERS);
		str.append("  Minimal number of inlier spots per comparison: " + minNumInliers + ".\n");
		int frameRange = (int) sm.get(FRAME_RANGE);
		str.append("  Range of frame intervals being compared: " + frameRange + ".\n");
		boolean discardLowCoverage = (boolean) sm.get(DISCARD_LOW_COVERAGE);
		str.append("  Discard pairs with low field-of-view coverage: " + discardLowCoverage + ".\n");
		double minCoverage = (double) sm.get(MIN_COVERAGE_FACTOR);
		str.append("  Minimal fraction (of each dimension) covered by inliers within single frame: " + minCoverage + ".\n");
		return str.toString();
	}

	@Override
	public Map<String, Object> getDefaultSettings() {
		Map<String, Object> settings = new HashMap<>(5);
		// Model choice?


		// Minimal number of inliers per compared pair
		settings.put(MIN_NUM_INLIERS, DEFAULT_MIN_NUM_INLIERS);

		// Frame interval range
		settings.put(FRAME_RANGE, DEFAULT_FRAME_RANGE);

		// Discard pairs with low field-of-view coverage
		settings.put(DISCARD_LOW_COVERAGE, DEFAULT_DISCARD_LOW_COVERAGE);

		// Minimal distance(xyz)/area/volume covered by inliers within single frame (um/percentage?)
		settings.put(MIN_COVERAGE_FACTOR, DEFAULT_MIN_COVERAGE_FACTOR);

		return settings;
	}

	@Override
	public boolean checkSettingsValidity(Map<String, Object> settings) {
		if (settings == null) {
			errorMessage = "Settings map is null.\n";
			return false;
		}
		if (settings.size() < 4) {
			errorMessage = "Too few settings entries";
			return false;
		}
		if (!settings.containsKey(MIN_NUM_INLIERS)
				|| !(settings.get(MIN_NUM_INLIERS) instanceof Integer)
				|| (int) settings.get(MIN_NUM_INLIERS) < 0)
		{
			errorMessage = "Wrong parameter for " + MIN_NUM_INLIERS;
			return false;
		}
		if (!settings.containsKey(FRAME_RANGE)
				|| !(settings.get(FRAME_RANGE) instanceof Integer)
				|| (int) settings.get(FRAME_RANGE) < 0)
		{
			errorMessage = "Wrong parameter for " + FRAME_RANGE;
			return false;
		}
		if (!settings.containsKey(DISCARD_LOW_COVERAGE)
				|| !(settings.get(DISCARD_LOW_COVERAGE) instanceof Boolean))
		{
			errorMessage = "Wrong parameter for " + DISCARD_LOW_COVERAGE;
			return false;
		}
		if (!settings.containsKey(MIN_COVERAGE_FACTOR)
				|| !(settings.get(MIN_COVERAGE_FACTOR) instanceof Double)
				|| (double) settings.get(MIN_COVERAGE_FACTOR) < 0
				|| (double) settings.get(MIN_COVERAGE_FACTOR) > 1)
		{
			errorMessage = "Wrong parameter for " + MIN_COVERAGE_FACTOR;
			return false;
		}
		return true;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

}
