package com.dzcx.disruptor;

import com.lmax.disruptor.EventFactory;

public class LearnEventFactory implements EventFactory<LearnEvent> {

	public LearnEvent newInstance() {
		
		return new LearnEvent();
	}

}
