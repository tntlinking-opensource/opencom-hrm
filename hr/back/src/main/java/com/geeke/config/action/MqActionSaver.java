package com.geeke.config.action;

import com.geeke.config.rocketmq.MqProduce;
import org.springframework.beans.factory.annotation.Autowired;

import com.geeke.common.IActionSaver;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;

/**
 * 日志发送到消息队列，通过消息队列入库
 * @author lys
 * version: 2021/11/16
 */
public class MqActionSaver implements IActionSaver {
    /**
    * 消息队列
    */
   @Autowired
   private MqProduce mqProduce;

	@Override
	public void saveAction(Action action) {
		if(action == null) {
			return;
		}
		action.preInsert();
		for (ActionRecycle actionRecycle : action.getActionRecycleList()){
			actionRecycle.preInsert();
		}
		mqProduce.produceActionLog(action);
	}

}
