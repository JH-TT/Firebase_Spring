package com.myspring.pro30.board.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;


@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	public static final String COLLECTION_NAME = "article";

//	@Override
//	public List selectAllArticlesList() throws DataAccessException {
//		List<ArticleVO> articlesList = articlesList = sqlSession.selectList("mapper.board.selectAllArticlesList");
//		return articlesList;
//	}
	
//	@Override
//	public List selectAllArticlesList() throws Exception {
//		Firestore firestore = FirestoreClient.getFirestore();
//		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
//		CollectionReference articles = firestore.collection(COLLECTION_NAME);
//		Query query = articles.orderBy("articleNO", Direction.DESCENDING).limit(5);
//		ApiFuture<QuerySnapshot> future = query.get();
//		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//		for(QueryDocumentSnapshot document : documents) {
//			articlesList.add(document.toObject(ArticleVO.class));
//		}
//		
//		return articlesList;
//	}
	@Override
	public List selectAllArticlesList(Map pagingMap) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
		CollectionReference articles = firestore.collection(COLLECTION_NAME);
		List<QueryDocumentSnapshot> docs;
		int pageNum = (Integer)pagingMap.get("pageNum");
		if(pageNum > 1) {						
			Query firstPage = articles.orderBy("articleNO", Direction.DESCENDING).limit(10 * (pageNum-1));
			
			ApiFuture<QuerySnapshot> future = firstPage.get();
			docs = future.get(30, TimeUnit.SECONDS).getDocuments();
			
			QueryDocumentSnapshot lastDoc = docs.get(docs.size()-1);
			Query secondPage = articles.orderBy("articleNO", Direction.DESCENDING).startAfter(lastDoc).limit(10);
			
			future = secondPage.get();
			docs = future.get(30, TimeUnit.SECONDS).getDocuments();
		}else {
			Query query = articles.orderBy("articleNO", Direction.DESCENDING).limit(10);
			ApiFuture<QuerySnapshot> future = query.get();
			docs = future.get().getDocuments();
		}
		
		for(QueryDocumentSnapshot document : docs) {
			articlesList.add(document.toObject(ArticleVO.class));
		}
		
		return articlesList;
	}
	
	@Override
	public List searchArticlesList(Map pagingMap) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		int pageNum = (Integer)pagingMap.get("pageNum");
		String keyword = (String)pagingMap.get("title");
		
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();		
		CollectionReference articles = (CollectionReference) firestore.collection(COLLECTION_NAME).whereArrayContains("title", keyword);
		List<QueryDocumentSnapshot> docs;
		
		if(pageNum > 1) {						
			Query firstPage = articles.orderBy("articleNO", Direction.DESCENDING).limit(10 * (pageNum-1));
			
			ApiFuture<QuerySnapshot> future = firstPage.get();
			docs = future.get(30, TimeUnit.SECONDS).getDocuments();
			
			QueryDocumentSnapshot lastDoc = docs.get(docs.size()-1);
			Query secondPage = articles.orderBy("articleNO", Direction.DESCENDING).startAfter(lastDoc).limit(10);
			
			future = secondPage.get();
			docs = future.get(30, TimeUnit.SECONDS).getDocuments();
		}else {
			Query query = articles.orderBy("articleNO", Direction.DESCENDING).limit(10);
			ApiFuture<QuerySnapshot> future = query.get();
			docs = future.get().getDocuments();
		}
		
		for(QueryDocumentSnapshot document : docs) {
			articlesList.add(document.toObject(ArticleVO.class));
		}			
		
		return articlesList;
	}
	
//	@Override
//	public List searchArticlesList(String keyword) throws Exception {
//		Firestore firestore = FirestoreClient.getFirestore();
//		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();		
//		CollectionReference articleRef = firestore.collection(COLLECTION_NAME);
//		Query search_articles = articleRef.orderBy("articleNO", Direction.DESCENDING);
//		ApiFuture<QuerySnapshot> future = search_articles.get();
//		List<QueryDocumentSnapshot> documents = future.get().getDocuments();		
//		for(QueryDocumentSnapshot document : documents) {
//			String title = (String)document.get("title");
//			if(title.contains(keyword)) {
//				articlesList.add(document.toObject(ArticleVO.class));
//			}
//		}
//		
//		return articlesList;
//	}
	
	
//	@Override
//	public int insertNewArticle(Map articleMap) throws DataAccessException {
//		int articleNO = selectNewArticleNO();
//		articleMap.put("articleNO", articleNO);
//		sqlSession.insert("mapper.board.insertNewArticle",articleMap);
//		return articleNO;
//	}
	@Override
	public int insertNewArticle(Map articleMap) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		Date date = new Date(System.currentTimeMillis());		
		
		int articleNO = selectNewArticleNO();
		try {				
			articleMap.put("articleNO", articleNO);
			articleMap.put("writeDate", date.toString());
			
			ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).set(articleMap);					
		}catch(Exception e) {
			e.printStackTrace();
		}
		return articleNO;
	}
    
	@Override
	public void insertNewImage(Map articleMap) throws DataAccessException {
		List<ImageVO> imageFileList = (ArrayList)articleMap.get("imageFileList");
		int articleNO = (Integer)articleMap.get("articleNO");
		int imageFileNO = selectNewImageFileNO();
		if(imageFileList != null && imageFileList.size() != 0) {
			for(ImageVO imageVO : imageFileList){
				imageVO.setImageFileNO(++imageFileNO);
				imageVO.setArticleNO(articleNO);
			}
			sqlSession.insert("mapper.board.insertNewImage",imageFileList);
		}
	}
	
	@Override
	public ArticleVO selectArticle(int articleNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO));
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();		
		
		return document.toObject(ArticleVO.class);
	}

	@Override
	public void updateArticle(Map articleMap) throws DataAccessException {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME)
													  .document(String.valueOf(articleMap.get("articleNO")))
													  .set(articleMap, SetOptions.merge());
	}

	@Override
	public void deleteArticle(int articleNO) throws DataAccessException {		
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).delete(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List selectImageFileList(int articleNO) throws DataAccessException {
		List<ImageVO> imageFileList = null;
		imageFileList = sqlSession.selectList("mapper.board.selectImageFileList",articleNO);
		return imageFileList;
	}
	
//	private int selectNewArticleNO() throws DataAccessException {
//		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
//	}
	
	public int selectNewArticleNO() throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();		
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		
		return documents.size() + 1;
	}
	
	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}

	@Override
	public int count_search_articles(String title) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = (ApiFuture<QuerySnapshot>) firestore.collection(COLLECTION_NAME).whereArrayContains("title", title).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		
		return documents.size();
	}

}