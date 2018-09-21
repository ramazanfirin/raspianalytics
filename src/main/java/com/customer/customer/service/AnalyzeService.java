package com.customer.customer.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.customer.customer.model.ResultVM;

import Luxand.FSDK;
import Luxand.FSDK.FSDK_FaceTemplate;
import Luxand.FSDK.FSDK_Features;
import Luxand.FSDK.HImage;
import Luxand.FSDK.TFacePosition;
import Luxand.FSDK.TFaces;

@Service
public class AnalyzeService {
//String fileName = "/home/ramazan/face/Luxand_FaceSDK/demo/portrait-1122364_960_720.jpg";
	//String fileName = "/home/ramazan/git/gurkan/python/face.jpg";
	
	public AnalyzeService() throws Exception {
		super();
		initialize();
	}
	
	public static void main(String[] args) throws Exception {
		AnalyzeService captureService = new AnalyzeService();
		captureService.findAgeGender("/home/ramazan/git/gurkan/python/face.jpg");
	}

	//@PostConstruct
	public void initialize() {
		try {
            int r = FSDK.ActivateLibrary("FgsONyEWyINCBz0lbgccL7LMjLMsgAbHxwgdNLt0Q1j8UTmTgZyeaeCoXno1HBydmshM4ygfBO+6/qlKhFZAF5BvVaSKx7NaV0fIFPtkRie2h1DMmKIYa15N7qBP/DsclEoom67W6fXIyRo4yBPhemiu53rXVsqfvmMOuiu7KhQ=");
            if (r != FSDK.FSDKE_OK){
                 System.exit(r);
            }
		}catch(java.lang.UnsatisfiedLinkError e) {
          System.exit(1);
		} 
		
		 FSDK.Initialize();
		 
	}
	
	public ResultVM findAgeGender(String fileName) {
		 Long start = System.currentTimeMillis();
		 HImage imageHandle = new HImage();
		
		 ResultVM resultVM = null;
		 if (FSDK.LoadImageFromFileW(imageHandle, fileName) == FSDK.FSDKE_OK){
			 TFaces faceArray = new TFaces();
			 FSDK.TFacePosition.ByReference facePosition = new FSDK.TFacePosition.ByReference();
			 
			 if (FSDK.DetectMultipleFaces( imageHandle,faceArray) == FSDK.FSDKE_OK){
				 	for (int i = 0; i < faceArray.faces.length; i++) {
						TFacePosition position =faceArray.faces[i];
					  
						String [] AttributeValues = new String[1];
                        String [] AttributeValuesAge = new String[1];
					    
                        FSDK_Features.ByReference facialFeatures = new FSDK_Features.ByReference();
                        FSDK.DetectFacialFeaturesInRegion(imageHandle, position, facialFeatures);

                        int res =  FSDK.DetectFacialAttributeUsingFeatures(
					    		imageHandle,
					    		facialFeatures, 
					    		"Gender", 
					    		AttributeValues, 1024);
					    
					    int res2 =  FSDK.DetectFacialAttributeUsingFeatures(
					    		imageHandle,
					    		facialFeatures, 
					    		"Age", 
					    		AttributeValuesAge, 1024);
					    
					    String[] result = AttributeValues[0].split(";");
					    String gender;
					    String maleValue = result[0].split("=")[1].replaceAll(",",".");
					    String femaleValue = result[1].split("=")[1].replaceAll(",",".");
					    
					    if(Double.parseDouble(maleValue)>Double.parseDouble(femaleValue))
					    	gender = "MALE";
					    else
					    	gender = "FEMALE";
					    
					    String age = AttributeValuesAge[0];
					    
					    System.out.println(age);
					    System.out.println("male rate is"+result[0]);
					    System.out.println("female rate is"+result[1]);					
					    
					    FSDK_FaceTemplate.ByReference FaceTemplate = new FSDK_FaceTemplate.ByReference(); 
					    FSDK.GetFaceTemplate(imageHandle, FaceTemplate);
					    //byte[] ssdf= FaceTemplate.template;
					 
					    String a  =new String(FaceTemplate.template);
					    resultVM = new ResultVM(age, gender, a);
					    Long end = System.currentTimeMillis();
					    System.out.println("duration="+(end-start));
				 	}
				 
			 }
		 }
		 
		 Long end = System.currentTimeMillis();
		 resultVM.setDuration(end-start);
		 return resultVM;
	}
	
	public void logging() {
		
	}
}
