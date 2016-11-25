package com.yingu.poc.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yingu.poc.common.base.service.impl.AbstractBaseServiceImpl;
import com.yingu.poc.dao.OmLogDao;
import com.yingu.poc.pojo.OmLog;
import com.yingu.poc.service.OmLogService;

@Service
public class OmLogServiceImpl extends AbstractBaseServiceImpl<OmLog> implements OmLogService{

	@Autowired
	private OmLogDao omLogDao;
	
	@Override
	@PostConstruct
	public void init() {
		this.baseDao = omLogDao;
	}

}
