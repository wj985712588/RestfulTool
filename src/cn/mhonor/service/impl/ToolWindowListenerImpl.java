package cn.mhonor.service.impl;

import cn.mhonor.service.topic.RefreshServiceTreeTopic;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

/**
 * @author mhonor
 * @version 1.0
 */
public class ToolWindowListenerImpl implements ToolWindowManagerListener {

    private final Project project;

    public ToolWindowListenerImpl(Project project) {
        this.project = project;
    }

    @SuppressWarnings("MissingRecentApi")
    @Override
    public void toolWindowShown(@NotNull String id, @NotNull ToolWindow toolWindow) {
        String restfulTool = "RestfulTool";
        if (restfulTool.equals(id)) {
            MessageBus bus = project.getMessageBus();
            RefreshServiceTreeTopic publisher = bus.syncPublisher(RefreshServiceTreeTopic.TOPIC);
            publisher.refresh();
        }
    }
}
