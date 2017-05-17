package com.dzcx.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class Test {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
//		ExecutorService executor = Executors.newSingleThreadExecutor();
//		ExecutorService executor = Executors.newFixedThreadPool(5);
		
		WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		
		EventFactory<LearnEvent> eventFactory = new LearnEventFactory();
		int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；
		        
		Disruptor<LearnEvent> disruptor = new Disruptor<LearnEvent>(eventFactory,
		                ringBufferSize, executor, ProducerType.MULTI, YIELDING_WAIT);
		        
//		LearnEventHandler eventHandler = new LearnEventHandler();
//		disruptor.handleEventsWith(eventHandler);
		WorkHandler<LearnEvent> workHandler = new LearnWorkHandler();
		WorkHandler<LearnEvent>[] pool = new LearnWorkHandler[100];
		for(int i = 0;i < 100;i++){
			pool[i] = workHandler;
		}
		disruptor.handleEventsWithWorkerPool(pool);
		disruptor.start();
		
		
		RingBuffer<LearnEvent> ringBuffer = disruptor.getRingBuffer();
		long sequence;//请求下一个事件序号；
		    
//		try {
//			LearnEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
////		    long data = getEventData();//获取要通过事件传递的业务数据；
//		    event.setMsg("hello disruptor");
//		} finally{
//		    ringBuffer.publish(sequence);//发布事件；
//		}
		for(int i = 0;i < 10; i++){
			sequence = ringBuffer.next();
			try{
				LearnEvent event = ringBuffer.get(sequence);
				event.setMsg("hello disruptor, i'm " + i);
			}finally{
				ringBuffer.publish(sequence);
			}
		}
		
		disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
		executor.shutdown();//关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
	}
}
