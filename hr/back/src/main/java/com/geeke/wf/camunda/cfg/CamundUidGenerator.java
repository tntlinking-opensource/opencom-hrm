package com.geeke.wf.camunda.cfg;

import org.camunda.bpm.engine.impl.cfg.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.fsg.uid.UidGenerator;

@Component
public class CamundUidGenerator implements IdGenerator{

	@Autowired
	private UidGenerator uidGenerator;
	
	@Override
	public String getNextId() {
		return Long.toString(uidGenerator.getUID());
	}

}
