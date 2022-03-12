package com.myspring.pro30.member.service;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitialize {
	
	@PostConstruct
	public void initialize() {
		try {			
			FileInputStream serviceAccount = new FileInputStream("/C:/myJSP/workspace/pro30/src/main/resources/serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			  .build();

			FirebaseApp.initializeApp(options);
			System.out.println("성공");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
