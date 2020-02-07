package ch.fmi.trackmate.detection;

import java.util.ArrayList;
import java.util.List;

import net.imagej.ops.OpService;
import net.imglib2.Interval;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealLocalizable;
import net.imglib2.RealPoint;
import net.imglib2.algorithm.labeling.ConnectedComponents.StructuringElement;
import net.imglib2.realtransform.Scale3D;
import net.imglib2.roi.Regions;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.type.NativeType;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.view.Views;

import org.scijava.Context;
import org.scijava.log.LogService;

import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.detection.SpotDetector;


public class ThresholdCenterOfMassDetector<T extends RealType<T> & NativeType<T>> implements SpotDetector<T> {

	private Context context;
	private RandomAccessible<T> img;
	private Interval interval;
	private double[] calibration;
	private double threshold;

	private List<Spot> spots;
	private String errorMessage;
	private long processingTime;

	public ThresholdCenterOfMassDetector(final Context context, final RandomAccessible<T> img, final Interval interval, final double[] calibration, final double threshold) {
		this.context = context;
		this.img = img;
		this.interval = interval;
		this.calibration = calibration;
		this.threshold = threshold;
	}

	@Override
	public List<Spot> getResult() {
		return spots;
	}

	@Override
	public boolean checkInput() {
		// always pass
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean process() {
		// Threshold img
		OpService opService = context.service(OpService.class);
		T thresholdValue = img.randomAccess().get().copy().createVariable();
		thresholdValue.setReal(threshold);
		context.service(LogService.class).info("threshold set: " + thresholdValue.getRealDouble());
		context.service(LogService.class).info("threshold type: " + thresholdValue.getClass());
		IterableInterval<BitType> thresholded = opService.threshold().apply(Views.iterable(Views.interval(img, interval)), thresholdValue);


		ImgLabeling labelImg = opService.labeling().cca((RandomAccessibleInterval<BitType>) thresholded, StructuringElement.EIGHT_CONNECTED);

		LabelRegions regions = new LabelRegions<>(labelImg);

		// Populate spots
		spots = new ArrayList<>();
		RealPoint scaledPosition = new RealPoint(3);
		for (LabelRegion<T> r : (Iterable<LabelRegion>) regions) {
			IterableInterval<T> sampled = Regions.sample(r, img);
			RealLocalizable centerOfMass = opService.geom().centerOfGravity(sampled);
			Scale3D scale = new Scale3D(calibration);
			scale.apply(centerOfMass, scaledPosition);
			DoubleType volume = opService.geom().size(r);
			double radius = Math.cbrt((volume.get() / Math.PI) * 0.75 * calibration[0] * calibration[1] * calibration[2]);
			spots.add(new Spot(scaledPosition, radius, volume.get()));
		}

		// Successful
		return true;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public long getProcessingTime() {
		return processingTime;
	}
}
