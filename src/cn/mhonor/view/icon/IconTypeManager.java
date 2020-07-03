package cn.mhonor.view.icon;

import cn.mhonor.view.icon.impl.CuteIcon;
import cn.mhonor.view.icon.impl.DefaultIcon;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mhonor
 * @version 1.0
 */
public class IconTypeManager {

    private static final Map<Class<? extends IconType>, IconType> ICON_TYPES = new HashMap<>();

    private static final Class<? extends IconType> DEFAULT_ICON_CLASS = DefaultIcon.class;

    private IconTypeManager() {
        // Nothing
    }

    public static IconType getInstance(@NotNull String className) {
        return IconTypeManager.getInstance(IconTypeManager.formatName(className));
    }

    public static IconType getInstance(@NotNull Class<? extends IconType> clazz) {
        if (!ICON_TYPES.containsKey(DEFAULT_ICON_CLASS)) {
            ICON_TYPES.put(DEFAULT_ICON_CLASS, new DefaultIcon());
        }
        if (ICON_TYPES.containsKey(clazz)) {
            return ICON_TYPES.get(clazz);
        }
        try {
            IconType iconType = clazz.newInstance();
            ICON_TYPES.put(clazz, iconType);
            return iconType;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return ICON_TYPES.get(DEFAULT_ICON_CLASS);
        }
    }

    /**
     * 获取图标风格列表
     *
     * @return array
     */
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static IconType[] getIconTypes() {
        return new IconType[]{
                IconTypeManager.getInstance(DefaultIcon.class),
                IconTypeManager.getInstance(CuteIcon.class),
        };
    }

    @NotNull
    public static Class<? extends IconType> formatName(@NotNull String className) {
        try {
            //noinspection unchecked
            return (Class<? extends IconType>) Class.forName(className);
        } catch (ClassNotFoundException ignore) {
            return DefaultIcon.class;
        } catch (Exception e) {
            e.printStackTrace();
            return DefaultIcon.class;
        }
    }

    @NotNull
    @Contract(pure = true)
    public static String formatClass(@NotNull Class<? extends IconType> clazz) {
        return clazz.getName();
    }
}
