package ch.fmi.trackmate.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.features.track.TrackAnalyzer;

@Plugin(type = TrackAnalyzer.class)
public class ManualFeatureTrackAnalyzer implements TrackAnalyzer {

	private static final String INT_FEATURE = "MANUAL_INTEGER_TRACK_FEATURE";
	private static final String DOUBLE_FEATURE = "MANUAL_DOUBLE_TRACK_FEATURE";

	private static final String KEY = "MANUAL_TRACK_FEATURE_ANALYZER";
	private static final String NAME = "Manual Track Feature Analyzer";
	private static final String INFO = "<html>A (dummy) Track Analyzer providing manual track features</html>";

	private static List<String> FEATURES = new ArrayList<>(1);
	private static Map<String, String> FEATURE_SHORT_NAMES = new HashMap<>(1);
	private static Map<String, String> FEATURE_NAMES = new HashMap<>(1);
	private static Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap<>(1);
	private static Map<String, Boolean> IS_INT = new HashMap<>(1);

	static {
		FEATURES.add(INT_FEATURE);
		FEATURES.add(DOUBLE_FEATURE);

		FEATURE_SHORT_NAMES.put(INT_FEATURE, "Integer Track Feature");
		FEATURE_SHORT_NAMES.put(DOUBLE_FEATURE, "Double Track Feature");

		FEATURE_NAMES.put(INT_FEATURE, "Custom Integer Track Feature");
		FEATURE_NAMES.put(DOUBLE_FEATURE, "Custom Double Track Feature");

		FEATURE_DIMENSIONS.put(INT_FEATURE, Dimension.NONE);
		FEATURE_DIMENSIONS.put(DOUBLE_FEATURE, Dimension.NONE);

		IS_INT.put(INT_FEATURE, true);
		IS_INT.put(DOUBLE_FEATURE, false);
	}

	@Override
	public long getProcessingTime() {
		return 0;
	}

	@Override
	public List<String> getFeatures() {
		return FEATURES;
	}

	@Override
	public Map<String, String> getFeatureShortNames() {
		return FEATURE_SHORT_NAMES;
	}

	@Override
	public Map<String, String> getFeatureNames() {
		return FEATURE_NAMES;
	}

	@Override
	public Map<String, Dimension> getFeatureDimensions() {
		return FEATURE_DIMENSIONS;
	}

	@Override
	public Map<String, Boolean> getIsIntFeature() {
		return IS_INT;
	}

	@Override
	public boolean isManualFeature() {
		return true;
	}

	@Override
	public String getInfoText() {
		return INFO;
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
	public void setNumThreads() {
		// Nothing to do
	}

	@Override
	public void setNumThreads(int numThreads) {
		// Nothing to do		
	}

	@Override
	public int getNumThreads() {
		return 0;
	}

	@Override
	public void process(Collection<Integer> trackIDs, Model model) {
		// No automatic computation of features
		// TODO decide whether or not to put default value 0
	}

	@Override
	public boolean isLocal() {
		return true;
	}

}
