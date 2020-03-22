package ch.fmi.trackmate.tracking;

import java.util.Map;

import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.tracking.sparselap.costfunction.CostFunction;
import mpicbg.pointdescriptor.SimplePointDescriptor;
import process.Particle;

public class DescriptorDistanceCostFunction implements CostFunction<Spot, Spot> {
	
	private Map<Integer, SimplePointDescriptor<Particle>> spotMapping;

	public DescriptorDistanceCostFunction(Map<Integer, SimplePointDescriptor<Particle>> spotMapping) {
		this.spotMapping = spotMapping;
	}

	@Override
	public double linkingCost(Spot s1, Spot s2) {
		SimplePointDescriptor<Particle> d1 = spotMapping.get(s1.ID());
		SimplePointDescriptor<Particle> d2 = spotMapping.get(s2.ID());
		// TODO check for null?
		return d1.descriptorDistance(d2);
	}

}
