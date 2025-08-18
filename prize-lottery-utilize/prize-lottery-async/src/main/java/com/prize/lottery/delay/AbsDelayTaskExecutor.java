package com.prize.lottery.delay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbsDelayTaskExecutor<T extends DelayTask> implements InitializingBean, DisposableBean {

    private static final long DEFAULT_LOAD_UNTIL_TIME = TimeUnit.HOURS.toMillis(1);

    private final AtomicBoolean startState = new AtomicBoolean(false);
    private final Executor      executor;
    private final Thread        delayWorker;
    private final DelayQueue<T> delayQueue;

    public AbsDelayTaskExecutor(Executor executor) {
        this.executor    = executor;
        this.delayQueue  = new DelayQueue<>();
        this.delayWorker = new Thread(this::triggerDelayRequest);
    }

    private void triggerDelayRequest() {
        do {
            try {
                T request = this.delayQueue.poll(DEFAULT_LOAD_UNTIL_TIME, TimeUnit.MILLISECONDS);
                while (request != null) {
                    T command = request;
                    this.executor.execute(() -> this.doExecute(command));
                    request = this.delayQueue.take();
                }
            } catch (InterruptedException ignore) {
            }
        }
        while (this.startState.get());
    }

    public boolean hasTask() {
        return !this.delayQueue.isEmpty();
    }

    /**
     * 延迟执行任务
     *
     * @param task 延迟任务
     */
    public void delayExe(T task) {
        this.delayQueue.add(task);
    }

    /**
     * 批量延迟执行任务
     *
     * @param tasks 延迟任务集合
     */
    public void delayExe(Collection<T> tasks) {
        this.delayQueue.addAll(tasks);
    }

    /**
     * 直接异步执行
     *
     * @param request 异步任务
     */
    public void directExe(T request) {
        this.executor.execute(() -> this.doExecute(request));
    }

    /**
     * 实际处理执行任务
     */
    private void doExecute(T request) {
        try {
            this.executeRequest(request);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.startState.compareAndSet(false, true)) {
            log.info("延迟任务调度器[{}]启动...", this.getClass().getSimpleName());
            this.delayWorker.start();
        }
    }

    @Override
    public void destroy() throws Exception {
        this.startState.set(false);
        if (!this.delayWorker.isInterrupted()) {
            this.delayWorker.interrupt();
        }
    }

    public abstract void executeRequest(T request);

}
