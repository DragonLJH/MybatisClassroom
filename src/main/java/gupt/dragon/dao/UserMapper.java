package gupt.dragon.dao;

import gupt.dragon.common.User;

public interface UserMapper {
	
	User retrieveUserByUserId(String jobNumber);
	
	void updateUser(User user);
	
	void resetUserPassword(User user);
	
	String retrievePowerByJobNumber(String jobNumber);
	
}
