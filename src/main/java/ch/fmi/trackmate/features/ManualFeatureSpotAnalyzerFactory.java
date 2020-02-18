package ch.fmi.trackmate.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import net.imagej.ImgPlus;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.features.spot.SpotAnalyzer;
import fiji.plugin.trackmate.features.spot.SpotAnalyzerFactory;

@Plugin(type = SpotAnalyzerFactory.class)
public class ManualFeatureSpotAnalyzerFactory<T extends RealType<T> & NativeType<T>> implements SpotAnalyzerFactory<T> {

	private static final String INT_FEATURE = "MANUAL_INTEGER_SPOT_FEATURE";
	private static final String DOUBLE_FEATURE = "MANUAL_DOUBLE_SPOT_FEATURE";

	private static final String KEY = "MANUAL_SPOT_FEATURE_ANALYZER";
	private static final String NAME = "Manual Spot Feature Analyzer";
	private static final String INFO = "<html>A (dummy) Spot Analyzer providing manual spot features</html>";

	private static List<String> FEATURES = new ArrayList<>(1);
	private static Map<String, String> FEATURE_SHORT_NAMES = new HashMap<>(1);
	private static Map<String, String> FEATURE_NAMES = new HashMap<>(1);
	private static Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap<>(1);
	private static Map<String, Boolean> IS_INT = new HashMap<>(1);

	static {
		FEATURES.add(INT_FEATURE);
		FEATURES.add(DOUBLE_FEATURE);

		FEATURE_SHORT_NAMES.put(INT_FEATURE, "Integer Spot Feature");
		FEATURE_SHORT_NAMES.put(DOUBLE_FEATURE, "Double Spot Feature");

		FEATURE_NAMES.put(INT_FEATURE, "Custom Integer Spot Feature");
		FEATURE_NAMES.put(DOUBLE_FEATURE, "Custom Double Spot Feature");

		FEATURE_DIMENSIONS.put(INT_FEATURE, Dimension.NONE);
		FEATURE_DIMENSIONS.put(DOUBLE_FEATURE, Dimension.NONE);

		IS_INT.put(INT_FEATURE, true);
		IS_INT.put(DOUBLE_FEATURE, false);
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
	public SpotAnalyzer<T> getAnalyzer(Model model, ImgPlus<T> img, int frame,
		int channel)
	{
		return new SpotAnalyzer<T>() {

			@Override
			public boolean checkInput() {
				return true;
			}

			@Override
			public boolean process() {
				// TODO decide whether or not to put default value 0
				return true;
			}

			@Override
			public String getErrorMessage() {
				return null;
			}

			@Override
			public long getProcessingTime() {
				return 0;
			}};
	}

}
