package cn.mhonor.service.topic;

import cn.mhonor.beans.Request;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * @author mhonor
 * @version 1.0
 */
public interface ServiceTreeTopic extends RestTopic<Map<String, List<Request>>> {

    Topic<ServiceTreeTopic> TOPIC = Topic.create("RestTopic.ServiceTreeTopic", ServiceTreeTopic.class);

    /**
     * action
     *
     * @param data data
     */
    @Override
    void action(@NotNull Map<String, List<Request>> data);
}
