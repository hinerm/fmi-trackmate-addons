package ch.fmi;

import java.util.ArrayList;
import java.util.Collections;
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

@Plugin(type = SpotAnalyzerFactory.class, priority = 0d)
public class MaxQualitySpotAnalyzerFactory<T extends RealType<T> & NativeType<T>> implements SpotAnalyzerFactory<T> {

	private static final String KEY = "HAS_MAX_QUALITY_IN_FRAME";
	public static final String HAS_MAX_QUALITY_IN_FRAME = "HAS_MAX_QUALITY_IN_FRAME";
	public static final List<String> FEATURES = new ArrayList<>(1);
	private static final Map<String, Boolean> IS_INT = new HashMap<>(1);
	public static final Map<String, String> FEATURE_NAMES = new HashMap<>(1);
	public static final Map<String, String> FEATURE_SHORT_NAMES = new HashMap<>(1);
	public static final Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap<>(1);

	private static final String NAME = "Has Max Quality in Frame";

	static {
		FEATURES.add(HAS_MAX_QUALITY_IN_FRAME);
		IS_INT.put(HAS_MAX_QUALITY_IN_FRAME, true);
		FEATURE_SHORT_NAMES.put(HAS_MAX_QUALITY_IN_FRAME, "Max Quality");
		FEATURE_NAMES.put(HAS_MAX_QUALITY_IN_FRAME, "Has max quality");
		FEATURE_DIMENSIONS.put(HAS_MAX_QUALITY_IN_FRAME, Dimension.NONE);
	}

	@Override
	public Map<String, Dimension> getFeatureDimensions() {
		return FEATURE_DIMENSIONS;
	}

	@Override
	public Map<String, String> getFeatureNames() {
		return FEATURE_NAMES;
	}

	@Override
	public Map<String, String> getFeatureShortNames() {
		return FEATURE_SHORT_NAMES;
	}

	@Override
	public List<String> getFeatures() {
		return FEATURES;
	}

	@Override
	public Map<String, Boolean> getIsIntFeature() {
		return Collections.unmodifiableMap(IS_INT);
	}

	@Override
	public boolean isManualFeature() {
		return false;
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public String getInfoText() {
		return "1 if this spot has the maximum quality in the frame, 0 otherwise";
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
	public SpotAnalyzer<T> getAnalyzer(Model model, ImgPlus<T> img, int frame, int channel) {
		return new MaxQualitySpotAnalyzer<>(model, frame);
	}

}
