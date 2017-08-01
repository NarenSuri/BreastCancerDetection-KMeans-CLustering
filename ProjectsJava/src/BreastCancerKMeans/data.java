package BreastCancerKMeans;

import java.util.*;

@SuppressWarnings("unused")
public class data {
	public String scn;
	public Double[] attrValues;
	public int[] attrRanks;
	
	// Constructor; sets up instance fields
	  public data(String name, Double[] attrVals) 
	  {
	    scn = name;
	    attrValues = new Double[attrVals.length];
	    for(int i = 0; i < attrVals.length; i++)
	    	attrValues[i] = attrVals[i];
	    attrRanks = new int[attrValues.length];
	    for(int i = 0; i < attrValues.length; i++)
	      for(int j = 0; j < attrValues.length; j++)
	        if(attrValues[j] < attrValues[i])
	        	attrRanks[i]++; 
	  }
	  
	// Constructor; sets up instance fields
		  public data(Double[] attrVals) 
		  {
		    attrValues = new Double[attrVals.length];
		    for(int i = 0; i < attrVals.length; i++)
		    	attrValues[i] = attrVals[i];
		    attrRanks = new int[attrValues.length];
		    for(int i = 0; i < attrValues.length; i++)
		      for(int j = 0; j < attrValues.length; j++)
		        if(attrValues[j] < attrValues[i])
		        	attrRanks[i]++; 
		  }
		  
	  public String getscn(){
		return scn;
		  
	  }
	  public Double[] getAttributeValues(){
		  return attrValues;
	  }
}
