package com.hatsnake.billina.repository;

import org.apache.ibatis.annotations.Mapper;

import com.hatsnake.billina.domain.Users;

@Mapper
public interface UserMapper {
	public Users findUserByEmail(String email) throws Exception;
	public Users findUserById(Long id) throws Exception;
	public int insertUser(Users user) throws Exception;
	public int updateUser(Users user) throws Exception;
}
