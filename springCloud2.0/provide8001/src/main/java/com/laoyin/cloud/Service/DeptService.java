package com.laoyin.cloud.Service;


import com.laoyin.cloud.entity.Dept;

import java.util.List;

public interface DeptService
{
	public boolean add(Dept dept);

	public Dept get(Long id );

	public Dept getOne(Long id );

	public List<Dept> list();


}
