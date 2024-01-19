package lm.swith.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import lm.swith.user.model.SwithUser;

@Mapper
public interface UsersMapper {
	void insertUser(SwithUser swithUser);
	
	SwithUser loginUser(String email, String password);
	
	SwithUser findUserRole(String role);
	
	SwithUser secUser(String email);
	
	List<SwithUser> findUsersAll();
	

}