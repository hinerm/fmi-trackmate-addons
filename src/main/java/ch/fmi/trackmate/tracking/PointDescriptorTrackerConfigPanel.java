package ch.fmi.trackmate.tracking;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import fiji.plugin.trackmate.gui.ConfigurationPanel;
import fiji.plugin.trackmate.gui.panels.components.JNumericTextField;

/**
 * {@link ConfigurationPanel} implementation for {@link PointDescriptorTrackerFactory}.
 * 
 * @author Jan Eglinger
 *
 */
public class PointDescriptorTrackerConfigPanel extends ConfigurationPanel {

	private JNumericTextField subsetSizeTextField;
	private JNumericTextField numNeighborsTextField;
	private JNumericTextField maxIntervalTextField;
	private JNumericTextField costThresholdTextField;
	private JNumericTextField maxDistanceTextField;
	private JCheckBox pruneCheckBox;

	public PointDescriptorTrackerConfigPanel() {
		initGui();
	}

	private void initGui() {
		add(new JLabel("Number of neighbors in subset"));
		subsetSizeTextField = new JNumericTextField(PointDescriptorTrackerFactory.SUBSET_NEIGHBORS);
		add(subsetSizeTextField);
		
		add(new JLabel("Number of neighbors to choose from"));
		numNeighborsTextField = new JNumericTextField(PointDescriptorTrackerFactory.NUM_NEIGHBORS);
		add(numNeighborsTextField);
		
		add(new JLabel("Maximum frame interval for matching"));
		maxIntervalTextField = new JNumericTextField(PointDescriptorTrackerFactory.MAX_INTERVAL);
		add(maxIntervalTextField);

		add(new JLabel("Cost (descriptor distance) threshold"));
		costThresholdTextField = new JNumericTextField(PointDescriptorTrackerFactory.COST_THRESHOLD);
		add(costThresholdTextField);

		add(new JLabel("Maximum linking distance"));
		maxDistanceTextField = new JNumericTextField(PointDescriptorTrackerFactory.MAX_LINKING_DISTANCE);
		add(maxDistanceTextField);

		add(new JLabel("Return pruned graph"));
		pruneCheckBox = new JCheckBox(PointDescriptorTrackerFactory.PRUNE_GRAPH);
		add(pruneCheckBox);
}

	@Override
	public void clean() {
		// Nothing to do
	}

	@Override
	public Map<String, Object> getSettings() {
		Map<String, Object> map = new HashMap<>(5);
		map.put(PointDescriptorTrackerFactory.SUBSET_NEIGHBORS, (int) subsetSizeTextField.getValue());
		map.put(PointDescriptorTrackerFactory.NUM_NEIGHBORS, (int) numNeighborsTextField.getValue());
		map.put(PointDescriptorTrackerFactory.MAX_INTERVAL, (int) maxIntervalTextField.getValue());
		map.put(PointDescriptorTrackerFactory.COST_THRESHOLD, (double) costThresholdTextField.getValue());
		map.put(PointDescriptorTrackerFactory.MAX_LINKING_DISTANCE, (double) maxDistanceTextField.getValue());
		map.put(PointDescriptorTrackerFactory.PRUNE_GRAPH, (boolean) pruneCheckBox.isSelected());
		return map;
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		subsetSizeTextField.setText("" + settings.get(PointDescriptorTrackerFactory.SUBSET_NEIGHBORS));
		numNeighborsTextField.setText("" + settings.get(PointDescriptorTrackerFactory.NUM_NEIGHBORS));
		maxIntervalTextField.setText("" + settings.get(PointDescriptorTrackerFactory.MAX_INTERVAL));
		costThresholdTextField.setText("" + settings.get(PointDescriptorTrackerFactory.COST_THRESHOLD));
		maxDistanceTextField.setText("" + settings.get(PointDescriptorTrackerFactory.MAX_LINKING_DISTANCE));
		pruneCheckBox.setSelected((boolean) settings.get(PointDescriptorTrackerFactory.PRUNE_GRAPH));
	}
}
