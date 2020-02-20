package ch.fmi.trackmate.action;

import java.io.File;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.ExportException;
import org.scijava.ui.UIService;

import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.AbstractTMAction;
import fiji.plugin.trackmate.util.TMUtils;

public class ExportGraphAction extends AbstractTMAction {

	@Override
	public void execute(TrackMate trackmate) {
		logger.log("Exporting track graph to .dot file ...");
		SimpleDirectedWeightedGraph<StringBuffer, DefaultWeightedEdge> graph = trackmate.getModel().getTrackModel().copy(
			() -> new StringBuffer(), // supplier
			(spot, buffer) -> buffer.append(spot.ID()), // function
			null // mapping
		);
		DOTExporter<StringBuffer, DefaultWeightedEdge> exporter = new DOTExporter<>();
		UIService uiService = TMUtils.getContext().service(UIService.class);
		File file = uiService.chooseFile("DOT File to save the graph", null, "save");
		try {
			exporter.exportGraph(graph, file);
		}
		catch (ExportException exc) {
			logger.error("DOT export failed: " + exc);
			return;
		}
		logger.log("Successfully exported to " + file);
	}

}
