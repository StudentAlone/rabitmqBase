package org.laoyin.cloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.laoyin.entity.Dept;

import java.util.List;

@Mapper
public interface DeptDao
{
	public boolean addDept(Dept dept);

	public Dept findById(Long id);

	public List<Dept> findAll();
}
