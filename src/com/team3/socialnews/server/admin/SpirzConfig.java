package com.team3.socialnews.server.admin;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class SpirzConfig implements Serializable {
	
	@SuppressWarnings("unused")
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private int dampKnob;
	
	@Persistent
	private double survivalParameter;
	// the higher, the more the very oldest links are spared
	// i.e. the flatter the exponential curve
	
	@Persistent
	private int predatorTerritory;
	// use to specify the maximum number of links that can be possible candidates for damping
		
	@Persistent
	private int minumumEnergy;
	
	public SpirzConfig(){	
	}
	
	/** The great damping knob (expressed in percentage points):
	 * - use value approaching 100 for full damping effect for each vote
	 * - use value approaching 0 for no damping (to let the hot page fill up)
	 * - use value above 100 to reduce the number of hot links (i.e. to empty out the hot page)
	 */
	public void setDampKnob(int dampKnob) {
		this.dampKnob = dampKnob;
	}

	public int getDampKnob() {
		return dampKnob;
	}

	public void setSurvivalParameter(double survivalParameter) {
		this.survivalParameter = survivalParameter;
	}

	public double getSurvivalParameter() {
		return survivalParameter;
	}

	/**
	 * Sets the maximum number of older links (relative to the one just voted on)
	 * the damp voting algorithm should consider as possible candidates.
	 * Its maximum value should be the size of the hot page, in number of links.
	 * Set a smaller value to make the damp operation cover less older links (i.e.
	 * to concentrate on damping only the very oldest links).
	 */
	public void setPredatorTerritory(int predatorTerritory) {
		this.predatorTerritory = predatorTerritory;
	}

	public int getPredatorTerritory() {
		return predatorTerritory;
	}

	public void setMinumumEnergy(int minumumEnergy) {
		this.minumumEnergy = minumumEnergy;
	}

	public int getMinumumEnergy() {
		return minumumEnergy;
	} 
}
