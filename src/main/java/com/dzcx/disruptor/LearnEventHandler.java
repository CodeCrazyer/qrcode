package com.dzcx.disruptor;

import com.lmax.disruptor.EventHandler;

public class LearnEventHandler implements EventHandler<LearnEvent> {

	public void onEvent(LearnEvent event, long arg1, boolean arg3) throws Exception {
		
		System.out.println(event.toString());
		
	}

}
