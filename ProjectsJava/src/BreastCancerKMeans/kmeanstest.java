package BreastCancerKMeans;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
@SuppressWarnings("unused")
public class kmeanstest {
	
	data[] cancerData;
	
	public  int k;
	public Double iterNum;
	Random random = new Random();
		
	ArrayList<cluster[]> centroidArray ;
	
	public kMeans(String file,int k1){
		BufferedReader reader;
		String line;
		String[] splitLine;
		Double[] attrValues;
		int recordCount;
		k=k1;
		iterNum=0.0000000000001;
		centroidArray = new ArrayList<cluster[]>();
		
		
		//Initial random centroid points from domain of data
		cluster[] initialRandCentroids = new cluster[k];
		initialRandCentroids=getKRandomPoints();
		
		//Assign data points to initial random centroids
		initialRandCentroids = assignDataCentroids(initialRandCentroids);
		
		//centroidArray[0] = initialRandCentroids;
		
		try {
			centroidArray.set(0, initialRandCentroids) ;
		} catch ( IndexOutOfBoundsException e ) {
			centroidArray.add(0, initialRandCentroids);
		}
		
	}
	
	public data[] getData() 
	  {
	    return cancerData; 
	  }
	
	public ArrayList<cluster[]> getClusters() 
	  {
		
	    return centroidArray;
	  }
	  
	
	public double calculateDistance(Double[] attrValues, Double[] doubles)
    {
        double Sum = 0.0;
        for(int i=0;i<attrValues.length;i++) {
           Sum = Sum + Math.pow((attrValues[i]-doubles[i]),2.0);
        }
        return Math.sqrt(Sum);
    }
	
	public cluster[] assignDataCentroids(cluster[] initialRandCentroids1){
		Double[] d;
		int min;
		for(int i=0;i<cancerData.length;i++){
			Double[] attrValues = cancerData[i].getAttributeValues();
			min = 0;
			d = new Double[initialRandCentroids1.length];
			for(int j=0;j<initialRandCentroids1.length;j++){
				
				//d[0]=0.0;
				d[j] = calculateDistance(attrValues,initialRandCentroids1[j].getCentroid().getAttributeValues());
				if(j!=0){
					if(d[j]<d[j-1]){
						min = j;
					}
				}
				
			}
			initialRandCentroids1[min].addElement(cancerData[i]);
		}
		return initialRandCentroids1;
		
	}
	
	public void updateCentroid(){
		int centroidArrayLength; 
		Double interCentroidDist;
		Double flag = 0.0;
		int iter = 1;
		do{
			
			cluster[] clusterTemp = new cluster[k];
			for(int i=0;i<k;i++){
				//clusterTemp[i]=(centroidArray[iter-1])[i];
				data dataTemp = (centroidArray.get(iter-1))[i].getMean();
				clusterTemp[i] = new cluster();
				clusterTemp[i].assignCentroid(dataTemp);
				
			}
			//Assign data points to updated centroids
			clusterTemp = assignDataCentroids(clusterTemp);
			
			//centroidArray.get(iter) = clusterTemp;
			
			
			try {
				centroidArray.set(iter, clusterTemp) ;
			} catch ( IndexOutOfBoundsException e ) {
				centroidArray.add(iter, clusterTemp);
			}
			
			centroidArrayLength = centroidArray.size();
			
			if (centroidArrayLength > 1){
				//for(int m=0; m<2; m++){
					Double [] minIntraCentroidDistance = new Double[k];
					for(int n=0;n<k;n++){
						Double[] intraCentroidDistance = new Double[k];
						for(int p=0;p<k;p++){
							intraCentroidDistance[p] = calculateDistance((centroidArray.get(centroidArrayLength-1))[n].getCentroid().getAttributeValues(),(centroidArray.get(centroidArrayLength-2))[p].getCentroid().getAttributeValues());
						}
						//to get min value
						Arrays.sort(intraCentroidDistance);

					     Double min =intraCentroidDistance[0];
					     minIntraCentroidDistance[n] = min;
						
					}
				//}
					Arrays.sort(minIntraCentroidDistance);

				     Double max =minIntraCentroidDistance[minIntraCentroidDistance.length-1];
					flag = max;
					
					System.out.println("Iteration:"+iter);
					iter++;
			}
		}while(flag > iterNum);
		
	}
	
	public static void main(String[] args) {
		int k=2;
		//String filePath = "E:\\Sem1-DataScience\\DataMining\\projectdatafiles\\breastCancerLessAtrib.csv";
		//String filePath = "E:\\Sem1-DataScience\\DataMining\\projectdatafiles\\BreastcancerFreqData.csv";
		String filePath = "E:\\Sem1-DataScience\\DataMining\\projectdatafiles\\breastCancer.csv";
		
		kMeans breastCancerKmeans = new kMeans(filePath,k);
		Double[] attrValues;
		data[] cancerData = breastCancerKmeans.getData();
		//breastCancerKmeans.getKRandomPoints();;
		
		//breastCancerKmeans.assignDataCentroids();
		breastCancerKmeans.updateCentroid();
			
			ArrayList<cluster[]> clustersData=breastCancerKmeans.getClusters();
			HashSet<data> clusterValues = new HashSet<data>();
			Double[] clusterValues1;
			

		//for(cluster[] l:clustersData){
			cluster[] l = new cluster[k];
			l = clustersData.get(clustersData.size()-1);
			for(int i=0;i<l.length;i++){
				//System.out.println("Length of each cluster:"+i+":"+clustersData[i].getClusterElements().size());
				//clusterValues.addAll(clustersData[i].getClusterElements());
				System.out.println("Centroid scn:"+i+""+l[i].getCentroid().getscn());
				//clusterValues1=clustersData[i].getCentroid().getAttributeValues();
				//data d=clustersData[i].getCentroid();
				System.out.println("Centroid cluster size:"+l[i].getClusterElements().size());
				
				for(data d:l[i].getClusterElements()){
					//data d = ;
					clusterValues1=d.getAttributeValues();
					
					System.out.println(d.getscn());
					//for(int j=0;j<clusterValues1.length;j++){
					//	System.out.println(clusterValues1[j]);
					//}
					
				}
				System.out.println("---------------------");
			}

	}

	
	
}
