package com.myspring.pro30.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberDAO {
//	 public List selectAllMemberList() throws DataAccessException;
	 public List selectAllMemberList() throws Exception;
//	 public int insertMember(MemberVO memberVO) throws DataAccessException ;
	 public void insertMember(MemberVO memberVO) throws DataAccessException ;
//	 public int deleteMember(String id) throws DataAccessException;
	 public void deleteMember(String id) throws DataAccessException;
//	 public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
	 public MemberVO loginById(MemberVO memberVO) throws Exception;
	 public boolean checkID(String id) throws Exception;
}
