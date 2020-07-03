package cn.mhonor.view.window;

import cn.mhonor.service.RestfulToolService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author mhonor
 * @version 1.0
 */
public class RestfulToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        RestfulToolService.getInstance(project).setupImpl(toolWindow);
    }
}
