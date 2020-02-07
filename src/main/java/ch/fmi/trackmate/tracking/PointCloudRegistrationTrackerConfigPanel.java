package ch.fmi.trackmate.tracking;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.gui.panels.components.JNumericTextField;


public class PointCloudRegistrationTrackerConfigPanel extends
	ConfigurationPanel
{

	
	private JNumericTextField minInliersTextField;
	private JNumericTextField frameIntervalTextField;
	private JCheckBox lowCoverageCheckbox;
	private JNumericTextField minFractionTextField;

	public PointCloudRegistrationTrackerConfigPanel() {
		initGui();
	}

	private void initGui() {
		add(new JLabel("Minimal number of inliers per compared pair:"));
		minInliersTextField = new JNumericTextField(PointCloudRegistrationTrackerFactory.DEFAULT_MIN_NUM_INLIERS);
		add(minInliersTextField);
		
		add(new JLabel("Frame interval range:"));
		frameIntervalTextField = new JNumericTextField(PointCloudRegistrationTrackerFactory.DEFAULT_FRAME_RANGE);
		add(frameIntervalTextField);

		//add(new JLabel("Discard pairs with low field-of-view coverage"));
		lowCoverageCheckbox = new JCheckBox("Discard pairs with low field-of-view coverage", PointCloudRegistrationTrackerFactory.DEFAULT_DISCARD_LOW_COVERAGE);
		add(lowCoverageCheckbox);

		add(new JLabel("Minimal fraction (of each dimension) covered by inliers within single frame:"));
		minFractionTextField = new JNumericTextField(PointCloudRegistrationTrackerFactory.DEFAULT_MIN_COVERAGE_FACTOR);
		add(minFractionTextField);
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		minInliersTextField.setText("" + settings.get(PointCloudRegistrationTrackerFactory.MIN_NUM_INLIERS));
		frameIntervalTextField.setText("" + settings.get(PointCloudRegistrationTrackerFactory.FRAME_RANGE));
		lowCoverageCheckbox.setSelected((boolean) settings.get(PointCloudRegistrationTrackerFactory.DISCARD_LOW_COVERAGE));
		minFractionTextField.setText("" + settings.get(PointCloudRegistrationTrackerFactory.MIN_COVERAGE_FACTOR));
	}

	@Override
	public Map<String, Object> getSettings() {
		Map<String, Object> map = new HashMap<>(5);
		map.put(PointCloudRegistrationTrackerFactory.MIN_NUM_INLIERS, (int) minInliersTextField.getValue());
		map.put(PointCloudRegistrationTrackerFactory.FRAME_RANGE, (int) frameIntervalTextField.getValue());
		map.put(PointCloudRegistrationTrackerFactory.DISCARD_LOW_COVERAGE, lowCoverageCheckbox.isSelected());
		map.put(PointCloudRegistrationTrackerFactory.MIN_COVERAGE_FACTOR, minFractionTextField.getValue());
		return map;
	}

	@Override
	public void clean() {
		// Nothing to do
	}

}
