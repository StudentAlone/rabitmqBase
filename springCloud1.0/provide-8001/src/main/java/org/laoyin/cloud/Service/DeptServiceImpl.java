package org.laoyin.cloud.Service;

import org.apache.log4j.Logger;
import org.laoyin.cloud.dao.DeptDao;
import org.laoyin.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        logger.info(id);
		return dao.findById(id);
	}

	@Override
	public List<Dept> list()
	{
		return dao.findAll();
	}

}
