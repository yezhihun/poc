package com.yingu.poc.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yingu.poc.common.base.service.impl.AbstractBaseServiceImpl;
import com.yingu.poc.dao.DeductWaterDao;
import com.yingu.poc.pojo.DeductWater;
import com.yingu.poc.service.DeductWaterService;

@Service
public class DeductWaterServiceImpl extends AbstractBaseServiceImpl<DeductWater> implements DeductWaterService{

	@Autowired
	private DeductWaterDao deductWaterDao;
	
	@Override
	@PostConstruct
	public void init() {
		this.baseDao = deductWaterDao;
	}

}
