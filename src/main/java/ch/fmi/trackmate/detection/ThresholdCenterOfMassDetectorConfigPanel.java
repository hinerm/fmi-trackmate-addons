
package ch.fmi.trackmate.detection;

import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_TARGET_CHANNEL;
import static fiji.plugin.trackmate.detection.DetectorKeys.KEY_THRESHOLD;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.gui.TrackMateWizard;
import fiji.plugin.trackmate.gui.panels.components.JNumericTextField;
import fiji.util.NumberParser;
import ij.ImagePlus;

public class ThresholdCenterOfMassDetectorConfigPanel extends
	ConfigurationPanel
{

	//private Settings settings;
	private Model model; // might be required for preview functionality
	private ImagePlus imp;
	private String infoText;
	private String detectorName;
	//private SpringLayout layout;
	private JLabel label1;
	private JLabel label2;
	private JSlider sliderChannel;
	private JLabel labelChannel;
	private JLabel labelThreshold;
	private JNumericTextField jTextFieldThreshold;

	public ThresholdCenterOfMassDetectorConfigPanel(final Settings settings,
		final Model model, final String infoText, final String detectorName)
	{
		//this.settings = settings;
		this.imp = settings.imp;
		this.model = model;
		this.infoText = infoText;
		this.detectorName = detectorName;
		initGUI();
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		int channelValue = (int) settings.get(KEY_TARGET_CHANNEL);
		sliderChannel.setValue(channelValue);
		jTextFieldThreshold.setText("" + settings.get(KEY_THRESHOLD));
	}

	@Override
	public Map<String, Object> getSettings() {
		final HashMap<String, Object> settingsMap = new HashMap<>(2);
		final int targetChannel = sliderChannel.getValue();
		final double threshold = NumberParser.parseDouble(jTextFieldThreshold
			.getText());
		settingsMap.put(KEY_TARGET_CHANNEL, targetChannel);
		settingsMap.put(KEY_THRESHOLD, threshold);
		return settingsMap;
	}

	@Override
	public void clean() {
		// No implementation needed
	}

	/* --- Private methods --- */

	private void initGUI() {
		/*
		this.setPreferredSize(new Dimension(300, 461));
		layout = new SpringLayout();
		setLayout(layout);
		*/
		label1 = new JLabel(detectorName);
		label1.setFont(TrackMateWizard.BIG_FONT);
		add(label1);

		label2 = new JLabel(infoText);
		label2.setFont(TrackMateWizard.FONT);
		label2.setPreferredSize(new Dimension(200, 300));
		add(label2);

		// add channel slider input
		// TODO add label?
		sliderChannel = new JSlider();
		sliderChannel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				labelChannel.setText("" + sliderChannel.getValue());
			}
		});
		add(sliderChannel);

		labelChannel = new JLabel("1");
		labelChannel.setHorizontalAlignment(SwingConstants.CENTER);
		labelChannel.setFont(TrackMateWizard.SMALL_FONT);
		add(labelChannel);

		// add number input for threshold
		labelThreshold = new JLabel();
		labelThreshold.setText("Threshold:");
		labelThreshold.setFont(TrackMateWizard.FONT);
		add(labelThreshold);

		jTextFieldThreshold = new JNumericTextField();
		jTextFieldThreshold.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldThreshold.setText("0");
		jTextFieldThreshold.setFont(TrackMateWizard.FONT);
		add(jTextFieldThreshold);

		// Deal with channels: the slider and channel labels are only
		// visible if we find more than one channel.
		final int n_channels = imp.getNChannels();
		sliderChannel.setMaximum(n_channels);
		sliderChannel.setMinimum(1);
		sliderChannel.setValue(imp.getChannel());

		if (n_channels <= 1) {
			labelChannel.setVisible(false);
			// lblSegmentInChannel.setVisible( false );
			sliderChannel.setVisible(false);
		}
		else {
			labelChannel.setVisible(true);
			// lblSegmentInChannel.setVisible( true );
			sliderChannel.setVisible(true);
		}
	}

}
