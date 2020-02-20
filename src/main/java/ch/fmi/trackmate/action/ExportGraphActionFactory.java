package ch.fmi.trackmate.action;

import javax.swing.ImageIcon;

import org.scijava.plugin.Plugin;

import fiji.plugin.trackmate.action.TrackMateAction;
import fiji.plugin.trackmate.action.TrackMateActionFactory;
import fiji.plugin.trackmate.gui.TrackMateGUIController;
import fiji.plugin.trackmate.gui.TrackMateWizard;

@Plugin(type = TrackMateActionFactory.class)
public class ExportGraphActionFactory implements TrackMateActionFactory {

	private static final String INFO_TEXT = "<html>Export all tracks as .dot graph</html>";
	private static final String KEY = "EXPORT_GRAPH_ACTION";
	private static final String NAME = "Export track graph";

	public static final ImageIcon ICON = new ImageIcon(TrackMateWizard.class.getResource("images/page_save.png"));

	@Override
	public String getInfoText() {
		return INFO_TEXT;
	}

	@Override
	public ImageIcon getIcon() {
		return ICON;
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
	public TrackMateAction create(TrackMateGUIController controller) {
		return new ExportGraphAction();
	}

}
