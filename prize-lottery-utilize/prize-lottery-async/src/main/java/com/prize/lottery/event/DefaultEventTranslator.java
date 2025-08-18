package com.prize.lottery.event;

public class DefaultEventTranslator<T extends Event<T>> implements EventTranslator<T> {

    /**
     * Translate a data representation into fields set in given event
     *
     * @param event    into which the data should be translated.
     * @param sequence that is assigned to event.
     * @param source   The first user specified argument to the translator
     */
    @Override
    public void translateTo(T event, long sequence, T source) {
        event.assign(source);
    }

}
