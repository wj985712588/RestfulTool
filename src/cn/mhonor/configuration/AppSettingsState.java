package cn.mhonor.configuration;

import cn.mhonor.beans.AppSetting;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author mhonor
 * @version 1.0
 */
@State(
        name = "core.configuration.AppSettingsState",
        storages = {
                @Storage("SdkSettingsPlugin.xml")
        }
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    private final AppSetting setting;

    public AppSettingsState() {
        this.setting = new AppSetting();
        this.setting.initValue();
    }

    public static AppSettingsState getInstance() {
        return ServiceManager.getService(AppSettingsState.class);
    }

    public boolean isModified(AppSetting setting) {
        if (setting == null) {
            return false;
        }
        return this.setting.isModified(setting);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @NotNull
    public AppSetting getAppSetting() {
        return this.setting;
    }

    public void setAppSetting(AppSetting setting) {
        this.setting.applySetting(setting);
    }
}
