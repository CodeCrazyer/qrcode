package com.dzcx.disruptor;

import com.lmax.disruptor.WorkHandler;

public class LearnWorkHandler implements WorkHandler<LearnEvent> {

	public void onEvent(final LearnEvent event) throws Exception {
		
		System.out.println(event.toString());
	}

}
