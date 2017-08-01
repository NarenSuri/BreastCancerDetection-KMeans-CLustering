package BreastCancerKMeans;

import java.util.ArrayList;
import java.util.HashSet;

public class cluster {
	HashSet<data> clusterElements;
	data centroid;
	ArrayList<Double> attrValues = new ArrayList<Double>();
	int attrValuesLength;
	
	public cluster(){
		 clusterElements = new HashSet<data>();

	}
	/*
	public cluster(ArrayList<data> clusterPoints){
		 clusterElements = new HashSet<data>();
		 for(data i:clusterPoints){
			 clusterElements.add(i);
		 }

	}
	*/
	
	public void addElement(data element){
		clusterElements.add(element);
	}
	
	public void assignCentroid(data element){
		centroid= element;
	}
	
	public data getCentroid(){
		return centroid;
	}
	
	public HashSet<data> getClusterElements(){
		
		return clusterElements;
		
	}
	
	public data getMean(){
		@SuppressWarnings("unused")
		ArrayList elementsArrList = new ArrayList();
		for(data elements:clusterElements){
			attrValuesLength = elements.getAttributeValues().length;
			 Double[] tempAttrValues = new Double[attrValuesLength];
			 tempAttrValues= elements.getAttributeValues();
			for(int j=0;j<tempAttrValues.length;j++){
				try {
					attrValues.set(j, (attrValues.get(j)+tempAttrValues[j])) ;
				} catch ( IndexOutOfBoundsException e ) {
					attrValues.add(j, tempAttrValues[j]);
				}
			}
		}
		for(int i=0;i<attrValues.size();i++){
			attrValues.set(i, (attrValues.get(i))/(clusterElements.size()));
		}
		Double []attrValues1 = new Double[attrValues.size()];
		attrValues.toArray(attrValues1);
		centroid = new data(attrValues1);
		return centroid;
		
	}
}
