package ch.fmi.trackmate.tracking;

import java.util.Map;

import fiji.plugin.trackmate.Spot;
import mpicbg.pointdescriptor.SimplePointDescriptor;
import process.Particle;

public class DistanceConstrainedDescriptorDistanceCostFunction extends DescriptorDistanceCostFunction {

	private double squareDistanceThreshold;

	public DistanceConstrainedDescriptorDistanceCostFunction(Map<Integer, SimplePointDescriptor<Particle>> spotMapping,
			double distanceThreshold) {
		super(spotMapping);
		this.squareDistanceThreshold = distanceThreshold;
	}

	@Override
	public double linkingCost(Spot s1, Spot s2) {
		// return high cost when real distance above threshold
		if (s1.squareDistanceTo(s2) > squareDistanceThreshold) return Double.POSITIVE_INFINITY;
		// otherwise, return linking cost from superclass
		return super.linkingCost(s1, s2);
	}

}
