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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.board.vo.ReplyVO;


@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	public static final String COLLECTION_NAME = "article";
	public static final String COLLECTION_NAME2 = "comment";
	public static final String COLLECTION_NAME3 = "comment2";
	private static int NextArticleNO = 0;
	private static int ReplyNO = 0;
	
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
	public List selectAllcommentsList(int commentNO, int articleNO) throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();
		List<ReplyVO> commentsList = new ArrayList<ReplyVO>();
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO))
													.collection(COLLECTION_NAME2).document(String.valueOf(commentNO))
													.collection(COLLECTION_NAME3).orderBy("commentNO", Direction.ASCENDING).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for(QueryDocumentSnapshot document : documents) {
			commentsList.add(document.toObject(ReplyVO.class));
		}
		
		return commentsList;
	}
	
	@Override
	public List listComments(int articleNO) throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();
		List<ReplyVO> commentsList = new ArrayList<ReplyVO>();
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).collection(COLLECTION_NAME2).orderBy("commentNO", Direction.ASCENDING).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for(QueryDocumentSnapshot document : documents) {
			commentsList.add(document.toObject(ReplyVO.class));
		}
		
		return commentsList;
	}
	
	@Override
	public List searchArticlesList(Map pagingMap, String keyword) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		int pageNum = (Integer)pagingMap.get("pageNum");
		int art_cnt = 0; // 게시글 개수
		
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();		
		CollectionReference articleRef = firestore.collection(COLLECTION_NAME);
		Query search_articles = articleRef.orderBy("articleNO", Direction.DESCENDING);
		ApiFuture<QuerySnapshot> future = search_articles.get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		
		for(QueryDocumentSnapshot document : documents) {
			String title = (String)document.get("title");			
			if(title.contains(keyword)) {				
				if(pageNum > 1 && art_cnt >= (pageNum-1) * 10 && art_cnt < pageNum * 10) {
					articlesList.add(document.toObject(ArticleVO.class));
				} else if(pageNum <= 1) {
					if(art_cnt < 10) {
						articlesList.add(document.toObject(ArticleVO.class));
					}
				}
				art_cnt += 1;
			}
		}		
		
		return articlesList;
	}

//	@Override
//	public int insertNewArticle(Map articleMap) throws Exception {
//		Firestore firestore = FirestoreClient.getFirestore();
//		Date date = new Date(System.currentTimeMillis());		
//		int articleno = NextArticleNO();
//		try {				
//			articleMap.put("articleNO", articleno);
//			articleMap.put("writeDate", date.toString());
//			
//			ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleno)).set(articleMap);					
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return articleno;
//	}
	
	@Override
	public int insertNewArticle(ArticleVO article) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		Date date = new Date(System.currentTimeMillis());		
		int articleno = NextArticleNO();
		try {
			article.setArticleNO(articleno);
			article.setWriteDate(date.toString());
			
			ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleno)).set(article);					
		}catch(Exception e) {
			e.printStackTrace();
		}
		return articleno;
	}
    
	@Override
	public void insertNewReply(Map ReplyMap, String articleNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		Date date = new Date(System.currentTimeMillis());
		int ReplyNO = NextCommentNO(articleNO);
		try {
			ReplyMap.put("writeDate", date.toString());
			ReplyMap.put("commentNO", ReplyNO);
			ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(articleNO).
											collection(COLLECTION_NAME2).document(String.valueOf(ReplyNO)).set(ReplyMap);
			
			ApiFuture<DocumentSnapshot> f = firestore.collection(COLLECTION_NAME).document(articleNO).get();
			DocumentSnapshot document = f.get();
			ArticleVO article = document.toObject(ArticleVO.class);
			article.addComment();
			ApiFuture<WriteResult> writeResult2 = firestore.collection(COLLECTION_NAME)
					  .document(articleNO)
					  .set(article);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertNewReply2(Map ReplyMap, String articleNO, String commentNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		Date date = new Date(System.currentTimeMillis());
		int ReplyNO = NextCommentNO2(articleNO, commentNO);
		try {
			ReplyMap.put("writeDate", date.toString());
			ReplyMap.put("commentNO", ReplyNO);
			ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(articleNO).
					collection(COLLECTION_NAME2).document(commentNO).collection(COLLECTION_NAME3).
					document(String.valueOf(ReplyNO)).set(ReplyMap);
			
			ApiFuture<DocumentSnapshot> f = firestore.collection(COLLECTION_NAME).document(articleNO).get();
			DocumentSnapshot document = f.get();
			ArticleVO article = document.toObject(ArticleVO.class);
			article.addComment();
			
			ApiFuture<DocumentSnapshot> f2 = firestore.collection(COLLECTION_NAME).document(articleNO)
														.collection(COLLECTION_NAME2).document(commentNO).get();
			DocumentSnapshot document2 = f2.get();
			ReplyVO com = document2.toObject(ReplyVO.class);
			com.addComment2();
			ApiFuture<WriteResult> writeResult2 = firestore.collection(COLLECTION_NAME).document(articleNO)
															.collection(COLLECTION_NAME2).document(commentNO)
															.set(com);
			
			ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME)
					.document(articleNO)
					.set(article);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int totalComment(String articleNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(articleNO).
											collection(COLLECTION_NAME2).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		
		return documents.size();
	}
	// 댓글번호
	public int NextCommentNO(String articleNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(articleNO).
											collection(COLLECTION_NAME2).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		int commentno = 0;
		for(QueryDocumentSnapshot document : documents) {
			ReplyVO reply = document.toObject(ReplyVO.class);
			if(commentno < reply.getcommentNO()) {
				commentno = reply.getcommentNO();
			}
		}
		return commentno + 1;
	}
	
	// 대댓글번호
	public int NextCommentNO2(String articleNO, String commentNO) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(articleNO).
				collection(COLLECTION_NAME2).document(commentNO).collection(COLLECTION_NAME3).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		int commentno = 0;
		for(QueryDocumentSnapshot document : documents) {
			ReplyVO reply = document.toObject(ReplyVO.class);
			if(commentno < reply.getcommentNO()) {
				commentno = reply.getcommentNO();
			}
		}
		return commentno + 1;
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
			ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).collection(COLLECTION_NAME2).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			// 삭제할 게시글의 모든 댓글 먼저 삭제하기
			for(QueryDocumentSnapshot document : documents) {
				ReplyVO com = document.toObject(ReplyVO.class);
				ApiFuture<QuerySnapshot> future2 = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO))
															.collection(COLLECTION_NAME2).document(String.valueOf(com.getcommentNO()))
															.collection(COLLECTION_NAME3).get();
				List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
				// 대댓글을 전부 없애준다.
				for(QueryDocumentSnapshot document2 : documents2) {
					document2.getReference().delete();
				}
				// 댓글 컬렉션을 없애준다.
				ApiFuture<WriteResult> writeResult2 = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).collection(COLLECTION_NAME2).document(String.valueOf(com.getcommentNO())).delete();
				document.getReference().delete();
			}
			// 모든 댓글 삭제 후 컬렉션 삭제하기
			ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteComment(int articleNO, int commentNO) throws DataAccessException {		
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).collection(COLLECTION_NAME2).document(String.valueOf(commentNO)).delete();
			ApiFuture<DocumentSnapshot> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO)).get();
			DocumentSnapshot document = future.get();
			ArticleVO article = document.toObject(ArticleVO.class);
			article.deleteComment();
			ApiFuture<WriteResult> writeResult2 = firestore.collection(COLLECTION_NAME)
														  .document(String.valueOf(articleNO))
														  .set(article);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteComment2(int articleNO, int commentNO, int commentNO2) throws DataAccessException {		
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> writeResult = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO))
															.collection(COLLECTION_NAME2).document(String.valueOf(commentNO))
															.collection(COLLECTION_NAME3).document(String.valueOf(commentNO2))
															.delete();
			ApiFuture<DocumentSnapshot> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO))
															.get();
			
			DocumentSnapshot document = future.get();
			ArticleVO article = document.toObject(ArticleVO.class);
			article.deleteComment();
			
			ApiFuture<DocumentSnapshot> future2 = firestore.collection(COLLECTION_NAME).document(String.valueOf(articleNO))
															.collection(COLLECTION_NAME2).document(String.valueOf(commentNO))
															.get();
			DocumentSnapshot document2 = future2.get();
			ReplyVO comment = document2.toObject(ReplyVO.class);
			comment.deleteComment2();
			// 댓글 대댓글 개수 수정
			ApiFuture<WriteResult> writeResult3 = firestore.collection(COLLECTION_NAME)
					.document(String.valueOf(articleNO))
					.collection(COLLECTION_NAME2)
					.document(String.valueOf(commentNO))
					.set(comment);
			// 게시글 댓글 개수 수정
			ApiFuture<WriteResult> writeResult2 = firestore.collection(COLLECTION_NAME)
					.document(String.valueOf(articleNO))
					.set(article);			
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
	
	public int totalArticles() throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();		
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		
		return documents.size();
	}
	
	public int NextArticleNO() throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();		
		ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		int articleno = 0;
		for(QueryDocumentSnapshot document : documents) {
			ArticleVO article = document.toObject(ArticleVO.class);
			
			if(articleno < article.getArticleNO()) {
				articleno = article.getArticleNO();
			}
		}
		return articleno + 1;
	}
	
	private int selectNewImageFileNO() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewImageFileNO");
	}

	@Override
	public int count_search_articles(String keyword) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		List<ArticleVO> articlesList = new ArrayList<ArticleVO>();		
		CollectionReference articleRef = firestore.collection(COLLECTION_NAME);
		Query search_articles = articleRef.orderBy("articleNO", Direction.DESCENDING);
		ApiFuture<QuerySnapshot> future = search_articles.get();
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		int size = 0;
		for(QueryDocumentSnapshot document : documents) {
			String title = (String)document.get("title");
			if(title.contains(keyword)) {
				articlesList.add(document.toObject(ArticleVO.class));
				size += 1;
			}
		}
		
		return size;
	}

}
