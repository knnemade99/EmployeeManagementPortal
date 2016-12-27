package com.emp.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emp.dao.IndexDao;
import com.emp.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	private IndexDao indexDao;

	@Override
	@Transactional
	public ResponseEntity<String> lockEmployee(String username) {
		return indexDao.unlockEmployee(username);
	}
	
	
}
