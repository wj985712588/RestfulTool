package cn.mhonor.service.impl;

import cn.mhonor.service.RestfulToolService;
import cn.mhonor.view.window.frame.RightToolWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author mhonor
 * @version 1.0
 */
public class RestfulToolServiceImpl implements RestfulToolService {

    private final Project project;

    public RestfulToolServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public void setupImpl(@NotNull ToolWindow toolWindow) {
        RightToolWindow view = new RightToolWindow(project);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(view, "", false);

        toolWindow.getContentManager().addContent(content);
    }
}
