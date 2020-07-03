package cn.mhonor.beans;

import cn.mhonor.view.icon.IconTypeManager;
import cn.mhonor.view.icon.impl.DefaultIcon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author mhonor
 * @version 1.0
 */
public class AppSetting {

    /**
     * 默认初始：扫描service时是否扫描lib（与项目配置分开）
     */
    public boolean scanServicesWithLibraryDefault;

    /**
     * 图标的类型具体实现类的className
     */
    @NotNull
    public String iconTypeClass = "";

    public void initValue() {
        this.scanServicesWithLibraryDefault = false;
        this.iconTypeClass = IconTypeManager.formatClass(DefaultIcon.class);
    }

    public boolean isModified(@Nullable AppSetting setting) {
        if (setting == null) {
            return false;
        }
        return this.scanServicesWithLibraryDefault != setting.scanServicesWithLibraryDefault ||
                !this.iconTypeClass.equals(setting.iconTypeClass);
    }

    public void applySetting(@Nullable AppSetting setting) {
        if (setting == null) {
            return;
        }
        this.scanServicesWithLibraryDefault = setting.scanServicesWithLibraryDefault;
        this.iconTypeClass = setting.iconTypeClass;
    }
}
