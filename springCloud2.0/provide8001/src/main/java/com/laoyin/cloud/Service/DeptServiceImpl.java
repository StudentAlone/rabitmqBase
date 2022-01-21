package com.laoyin.cloud.Service;


import com.laoyin.cloud.dao.DeptDao;
import com.laoyin.cloud.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DeptServiceImpl implements DeptService
{
    Logger logger = Logger.getLogger("DeptServiceImpl");
	@Autowired
	private DeptDao dao;
	
	@Override
	public boolean add(Dept dept)
	{
		return dao.addDept(dept);
	}

	@Override
	public Dept get(Long id)
	{
        logger.info(String.valueOf(id));
		return dao.findById(id);
	}

	@Override
	public List<Dept> list()
	{
		return dao.findAll();
	}

}
