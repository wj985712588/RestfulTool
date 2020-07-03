package cn.mhonor.service.topic;

/**
 * @author mhonor
 * @version 1.0
 */
public interface RestTopic<T> {

    /**
     * action
     *
     * @param data data
     */
    void action(T data);
}
