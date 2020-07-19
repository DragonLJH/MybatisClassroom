package gupt.dragon.dao;

import java.util.List;

import gupt.dragon.common.Management;

public interface ManagementMapper {
	
	List<Management> selectAllByDate(String date);
	
	void creationManagement(Management management);
	
	void updateManagement(Management management);
	
	
}
