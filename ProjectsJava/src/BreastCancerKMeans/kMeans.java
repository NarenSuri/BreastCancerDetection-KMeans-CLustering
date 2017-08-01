package BreastCancerKMeans;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
@SuppressWarnings("unused")
public class kMeans {
	
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
		try {
			
			reader = new BufferedReader(new FileReader(file));
		      
		     // Count the number of records in csv file
		    for(recordCount = 0; reader.readLine( ) != null; recordCount++);
		      
		      // Close and then re-open the file now that we know its length
		    reader.close();
		      
			reader = new BufferedReader(new FileReader(file));
		    
			cancerData = new data[recordCount];
			attrValues = null;
		     
			for(int i=0; i< recordCount; i++){ 
				line=reader.readLine();
				splitLine = line.split( "," );
				
				// The first entry in the parts array is the scn, the rest
		        // are attribute values of the instance
		        if((attrValues == null) || (attrValues.length != 
		                                   (splitLine.length - 1)))
		        	attrValues = new Double[splitLine.length - 1];
		        for(int j = 0; j < attrValues.length; j++)
		        	attrValues[j] = Double.parseDouble(splitLine[j + 1]);
		        
		        // Finally, create the Gene in the array
		        cancerData[i] = new data(splitLine[0], attrValues);
			}
			
			 // close the file
		    reader.close();
		      
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	  
	/*
	public void getInitialKRandomSeeds(){
		//clusters= new cluster[k];
		List<data> kRandomPoints = getKRandomPoints();
		for (int i = 0; i < k; i++){
			//clusters.get(i).addElement(kRandomPoints.get(i));
			//clusters[i] = new cluster();
			clusters[i].assignCentroid(kRandomPoints.get(i));
		
			//System.out.println("SCN values of random points "+ kRandomPoints.get(i).getscn()+":");
		  }
		
		 }
		*/
	
	public cluster[] getKRandomPoints() {
		List<data> kRandomPoints = new ArrayList<data>();

		boolean[] alreadyChosen = new boolean[cancerData.length];
		int size = cancerData.length;
		cluster[] clusters = new cluster[k];
		for (int i = 0; i < k; i++) {
			int index = -1, r = random.nextInt(size--) + 1;
			for (int j = 0; j < r; j++) {
				index++;
				while (alreadyChosen[index])
					index++;
			}
			kRandomPoints.add(cancerData[index]);
			
			clusters[i] = new cluster();
			clusters[i].assignCentroid(cancerData[index]);
			//clusters[i].addElement(cancerData[index]);
			//System.out.println("SCN values of centroid "+ clusters[i].getCentroid().getscn()+":");
		   alreadyChosen[index] = true;
		  }
		//centroidArray.set(0,clusters);
		  return clusters;
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
		
		/*
		//for(int iter=1;iter<iterNum;iter++){
			cluster[] clusterTemp = new cluster[k];
			for(int i=0;i<k;i++){
				//clusterTemp[i]=(centroidArray[iter-1])[i];
				data dataTemp = (centroidArray[iter-1])[i].getMean();
				clusterTemp[i] = new cluster();
				clusterTemp[i].assignCentroid(dataTemp);
				
			}
			//Assign data points to updated centroids
			clusterTemp = assignDataCentroids(clusterTemp);
			
			centroidArray[iter] = clusterTemp;
		*/
			/*
			try {
				centroidArray.set(iter, clusterTemp) ;
			} catch ( IndexOutOfBoundsException e ) {
				centroidArray.add(iter, clusterTemp);
			}
			*/
			//centroidArray.add(iter,clusterTemp);
			
			/*
			for(data d:((centroidArray.get(iter))[0].getClusterElements()))
			{
				Double[] clusterValues1 = d.getAttributeValues();
				
				System.out.println(clusterValues1[0]);
				System.out.println("********************************");
			}
			*/
		//}
		
		
		
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
			

		
		
		/*
		
		//print given data(scn and attribute values)
		for(int i=0;i<cancerData.length;i++){
			attrValues = cancerData[i].getAttributeValues();
			System.out.print("Attribute values of "+ cancerData[i].getscn()+":");
			
			for(int j=0;j<attrValues.length;j++){
				System.out.println(attrValues[j]);
			}
			
		}
		
		*/
		
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
		//}
		
		
		/*
		for(int l=0;l<clustersData.size();l++){
			//print centroids
			for(int i=0;i<clustersData.get(l).length;i++){
				//System.out.println("Length of each cluster:"+i+":"+clustersData[i].getClusterElements().size());
				//clusterValues.addAll(clustersData[i].getClusterElements());
				System.out.println("Centroid scn:"+i+""+clustersData.get(i).getCentroid().getscn());
				//clusterValues1=clustersData[i].getCentroid().getAttributeValues();
				//data d=clustersData[i].getCentroid();
				System.out.println("Centroid cluster size:"+clustersData.get(i).getClusterElements().size());
				for(data d:clustersData[i].getClusterElements()){
					//data d = ;
					clusterValues1=d.getAttributeValues();
					
					System.out.println("scn of elements"+d.getscn());
					for(int j=0;j<clusterValues1.length;j++){
						System.out.println(clusterValues1[j]);
					}
				}
			}
		}
		
		*/
	}

	
	
}
