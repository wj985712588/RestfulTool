package cn.mhonor.view.icon;

import cn.mhonor.beans.HttpMethod;
import cn.mhonor.view.icon.impl.DefaultIcon;
import com.intellij.ui.IconManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author mhonor
 * @version 1.0
 */
public class Icons {

    public static final Icon SERVICE = load("/icons/service.png");

    @NotNull
    public static Icon load(@NotNull String path) {
        return IconManager.getInstance().getIcon(path, Icons.class);
    }

    /**
     * 获取方法对应的图标
     *
     * @param method 请求类型
     * @return icon
     */
    @NotNull
    public static Icon getMethodIcon(HttpMethod method) {
        return getMethodIcon(method, false);
    }

    public static Icon getMethodIcon(HttpMethod method, boolean selected) {
        return IconTypeManager.getInstance(DefaultIcon.class).getDefaultIcon(method);
    }
}
