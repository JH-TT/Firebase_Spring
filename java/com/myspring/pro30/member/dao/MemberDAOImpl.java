package com.myspring.pro30.member.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.threeten.bp.LocalDate;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.myspring.pro30.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	public static final String COLLECTION_NAME = "user";

	@Override
	public List selectAllMemberList() throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		List<MemberVO> membersList = new ArrayList<MemberVO>();
		List<QueryDocumentSnapshot> documents = null;
		
		ApiFuture<QuerySnapshot> future = firestore.collection("community").document("community").collection(COLLECTION_NAME).get();		
		documents = future.get().getDocuments();
		for(QueryDocumentSnapshot document : documents) {
			membersList.add(document.toObject(MemberVO.class));
		}
		
		return membersList;
	}
	
	@Override
	public void insertMember(MemberVO membervo) throws DataAccessException {
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> apiFuture = firestore.collection("community").document("community").collection(COLLECTION_NAME).document(membervo.getId()).set(membervo);
			
			// 중복확인용 아이디 추가하기
			DocumentReference docRef = firestore.collection("datas").document("ID");
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();
			Map<String,Object> IDMap = document.getData();
			IDMap.put(membervo.getId(), "id");
			ApiFuture<WriteResult> apiFuture2 = firestore.collection("datas").document("ID").set(IDMap);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	@Override
	public void deleteMember(String id) throws DataAccessException {
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> writeResult = firestore.collection("community").document("community").collection(COLLECTION_NAME).document(id).delete();
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	@Override
	public MemberVO loginById(MemberVO memberVO) throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference docRef = firestore.collection("community").document("community").collection(COLLECTION_NAME).document(memberVO.getId());
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		MemberVO vo = document.toObject(MemberVO.class);
		return vo;
	}

	@Override
	public boolean checkID(String id) throws Exception {
		boolean result = false;
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference docRef = firestore.collection("datas").document("ID");
			ApiFuture<DocumentSnapshot> future = docRef.get();
			DocumentSnapshot document = future.get();
			
			if(document.get(id) != null) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
