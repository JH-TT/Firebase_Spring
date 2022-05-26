package com.myspring.pro30.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberService {
//	 public List listMembers() throws DataAccessException;
	 public List listMembers() throws Exception;
//	 public int addMember(MemberVO memberVO) throws DataAccessException;
	 public void addMember(MemberVO memberVO) throws DataAccessException;
//	 public int removeMember(String id) throws DataAccessException;
	 public void removeMember(String id) throws DataAccessException;
	 public MemberVO login(MemberVO memberVO) throws Exception;
	 public boolean overlappedID(String id) throws Exception; // 아이디 중복 확인.
}
