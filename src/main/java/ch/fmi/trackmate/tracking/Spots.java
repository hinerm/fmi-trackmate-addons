package ch.fmi.trackmate.tracking;

import fiji.plugin.trackmate.Spot;
import mpicbg.imglib.algorithm.scalespace.DifferenceOfGaussianPeak;
import mpicbg.imglib.algorithm.scalespace.DifferenceOfGaussian.SpecialPoint;
import mpicbg.imglib.type.numeric.real.FloatType;

/**
 * Utility class to convert TrackMate {@code Spot}s to other point
 * representations.
 * 
 * @author Jan Eglinger
 */
public class Spots {
	private Spots() {
		// prevent instantiation of static utility class
	}

	/**
	 * Creates a new {@code DifferenceOfGaussianPeak} for a TrackMate {@code Spot}
	 * 
	 * @param spot
	 *            {@code Spot} to be converted
	 * @param realPos
	 *            empty array to hold the real coordinates of the spot
	 * @param pos
	 *            empty array to hold the integer coordinates of the spot
	 * @return a {@code DifferenceOfGaussianPeak} corresponding to the input
	 *         {@code Spot}
	 */
	public static DifferenceOfGaussianPeak<FloatType> createPeak(Spot spot, double[] realPos, int[] pos) {
		spot.localize(realPos);
		for (int i = 0; i < realPos.length; i++) {
			pos[i] = (int) realPos[i];
		}
		DifferenceOfGaussianPeak<FloatType> p = new DifferenceOfGaussianPeak<>(pos, new FloatType(), SpecialPoint.MAX);
		for (int d = 0; d < realPos.length; d++) {
			p.setSubPixelLocationOffset((float) (realPos[d] - pos[d]), d);
		}
		return p;
	}
}
