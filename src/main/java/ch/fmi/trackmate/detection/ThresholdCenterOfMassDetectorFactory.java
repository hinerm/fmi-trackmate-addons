
package ch.fmi.trackmate.detection;

import static fiji.plugin.trackmate.detection.DetectorKeys.DEFAULT_TARGET_CHANNEL;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_TARGET_CHANNEL;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_THRESHOLD;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import net.imagej.ImgPlus;
import net.imglib2.Interval;
import net.imglib2.RandomAccessible;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;

import org.jdom2.Element;
import org.scijava.Context;
import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.detection.LogDetectorFactory;
import fiji.plugin.trackmate.detection.SpotDetector;
import fiji.plugin.trackmate.detection.SpotDetectorFactory;
import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.util.TMUtils;
import ij.IJ;

@Plugin(type = SpotDetectorFactory.class)
public class ThresholdCenterOfMassDetectorFactory<T extends RealType<T> & NativeType<T>>
	extends LogDetectorFactory<T>
{

	private static final String THIS_INFO_TEXT = "Detector computing the center of mass of thresholded particles";
	private static final String THIS_DETECTOR_KEY = "ThresholdCenterDetector";
	private static final String THIS_NAME = "Threshold / Center of Mass Detector";
	private static final Object DEFAULT_THRESHOLD = 128;

	@Override
	public String getInfoText() {
		return THIS_INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	@Override
	public String getKey() {
		return THIS_DETECTOR_KEY;
	}

	@Override
	public String getName() {
		return THIS_NAME;
	}

	@Override
	public SpotDetector<T> getDetector(Interval interval, int frame) {
		double[] calibration = TMUtils.getSpatialCalibration(img);
		double threshold = (double) settings.get(KEY_THRESHOLD);
		RandomAccessible<T> imgFrame = prepareFrameImg(frame);
		final Context ctx = (Context) IJ.runPlugIn("org.scijava.Context", "");

		return new ThresholdCenterOfMassDetector<>(ctx, imgFrame, interval, calibration, threshold);
	}


	/*
	@Override
	public boolean setTarget(ImgPlus<T> img, Map<String, Object> settings) {
		this.img = img;
		this.settings = settings;
		return checkSettings(settings);
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public boolean unmarshall(Element element, Map<String, Object> lSettings) {
		// TODO Load settings
		return checkSettings(lSettings);
	}
	*/

	@Override
	public ConfigurationPanel getDetectorConfigurationPanel(Settings lSettings,
		Model model)
	{
		return new ThresholdCenterOfMassDetectorConfigPanel(lSettings, model, THIS_INFO_TEXT, THIS_NAME);
	}

	/*
	@Override
	public Map<String, Object> getDefaultSettings() {
		final Map<String, Object> lSettings = new HashMap<>();
		lSettings.put(KEY_TARGET_CHANNEL, DEFAULT_TARGET_CHANNEL);
		lSettings.put(KEY_THRESHOLD, DEFAULT_THRESHOLD);
		return lSettings;
	}

	@Override
	public boolean checkSettings(Map<String, Object> lSettings) {
		// TODO implement sanity check
		return lSettings != null;
	}
	*/

}
