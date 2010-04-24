package com.team3.socialnews.server.vote;

import java.util.Random;

import com.google.inject.Inject;
import com.team3.socialnews.server.admin.SpirzConfigRepository;

public class LinkPredator {
	
	private Random random = new Random();
	private SpirzConfigRepository config;
	
	@Inject
	public LinkPredator(SpirzConfigRepository config){
		this.config = config;
	}

	/**
	 * Returns the n-th oldest link to prey upon (i.e. damped),
	 * based on the total number of older links. 
	 * Assumptions: -links are ordered from oldest down.
	 * - numberOfLinks is equal or lower than the predator territory size 
	 */
	public int getPreyIndex(int numberOfOlderLinks){
		// scale a random number of an exponential density distribution within the number of older links 
		int preyIndex = (int) (Math.floor(numberOfOlderLinks * getNextExpRandom()/config.get().getPredatorTerritory()));
		if(preyIndex > numberOfOlderLinks-1){
			return random.nextInt(preyIndex); // Make sure we don't return anything larger than the max number of possible preys.
						// This happens when survivalParameter is large versus territory. The "tail end" of the distribution
						// gets redistributed at random, leading to a more unpredictable damping behavior.
		}
		return preyIndex; 
	}
	
	/**
	 * Answers the question: "what percentage of the time do the (oldestRange, 0 to 1) 
	 * percent oldest links get selected for damping"
	 */
	public double getOldestHitProbability(double oldestRange){
		double b = config.get().getSurvivalParameter();
		double x = config.get().getPredatorTerritory();
		double exp = -x*oldestRange/b;
		double result = 1 - Math.exp(exp);
		return result;
	}
	
	/**
	 * Answers the question: "what percentage of the time do the (newestRange, 0 to 1) 
	 * percent newest links get selected for damping"
	 */
	public double getNewestHitProbability(double newestRange){
		double b = config.get().getSurvivalParameter();
		double x = config.get().getPredatorTerritory();
		double oldestRange = 1 - newestRange;
		double exp = -x*oldestRange/b;
		double upUntilStartOfNewestCDF = 1 - Math.exp(exp);
		double upUntilTerritoryEndCDF = 1 - Math.exp(-x/b); // Value should be very close to 1.0, unless the survival parameter gets too large
															// See getTerritoryOverflowProbability().
		double result = upUntilTerritoryEndCDF - upUntilStartOfNewestCDF;
		return result;
	}
	
	/**
	 * Answers the question: "how much of the tail of the distribution of links gets lost because
	 * the territory is too small for the survival parameter". This is important because, in getPreyIndex, the clipped
	 * tail gets redistributed at random, leading to more unpredictable behavior. 
	 * In short, you cannot use a high survivalParameter value and a too low territory value together without
	 * "breaking" the algorithm.
	 */
	public double getTerritoryOverflowProbability(){
		double b = config.get().getSurvivalParameter();
		double x = config.get().getPredatorTerritory();
		double exp = -x/b;
		double upUntilTerritoryEndCDF = 1 - Math.exp(exp);
		return 1 - upUntilTerritoryEndCDF; // the "lost tail" probability
	}
	
	/**
	 * Get the next exponentially distributed random number following the technique discussed here:
	 * http://stackoverflow.com/questions/2106503/pseudorandom-number-generator-exponential-distribution
	 * Here b = 1/k is the survival parameter (see Alternative parameterization at http://en.wikipedia.org/wiki/Exponential_distribution).
	 * A high b will mean less chance that the very oldest links get hit. The objective is to dampen mostly links which are the oldest.
	 */
	private double getNextExpRandom(){
		double b = this.config.get().getSurvivalParameter();
		double nextExp = - b * Math.log(1 - random.nextDouble());	// From exponential cdf through inversion method.
																	// Will return a double from 0.0 to infinity, with a 
																	// probability density that follows the exponential distribution
		return nextExp;
	}

	public int getTerritory(){
		return config.get().getPredatorTerritory();		
	}
}
