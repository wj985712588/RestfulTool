package cn.mhonor.view.icon.impl;

import cn.mhonor.beans.HttpMethod;
import cn.mhonor.view.icon.IconType;
import cn.mhonor.view.icon.Icons;
import cn.mhonor.view.icon.PreviewIcon;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mhonor
 * @version 1.0
 */
public class DefaultIcon implements IconType {

    public static final Icon REQUEST = Icons.load("/icons/method/default/Request.png");
    public static final Icon GET = Icons.load("/icons/method/default/GET.png");
    public static final Icon POST = Icons.load("/icons/method/default/POST.png");
    public static final Icon DELETE = Icons.load("/icons/method/default/DELETE.png");
    public static final Icon PUT = Icons.load("/icons/method/default/PUT.png");
    public static final Icon PATCH = Icons.load("/icons/method/default/PATCH.png");
    public static final Icon HEAD = Icons.load("/icons/method/default/HEAD.png");

    private static final Map<HttpMethod, Icon> ICONS;

    static {
        ICONS = new HashMap<>(HttpMethod.values().length);
        ICONS.put(HttpMethod.REQUEST, REQUEST);
        ICONS.put(HttpMethod.GET, GET);
        ICONS.put(HttpMethod.POST, POST);
        ICONS.put(HttpMethod.DELETE, DELETE);
        ICONS.put(HttpMethod.PUT, PUT);
        ICONS.put(HttpMethod.PATCH, PATCH);
        ICONS.put(HttpMethod.HEAD, HEAD);
    }

    @NotNull
    @Override
    public Icon getDefaultIcon(HttpMethod method) {
        return ICONS.get(method);
    }

    @NotNull
    @Override
    public Icon getSelectIcon(HttpMethod method) {
        return this.getDefaultIcon(method);
    }

    @NotNull
    @Override
    public List<PreviewIcon> getDefaultIcons() {
        List<PreviewIcon> list = new ArrayList<>(ICONS.size());
        ICONS.forEach((method, icon) -> list.add(new PreviewIcon(method.name(), icon)));
        return list;
    }

    @NotNull
    @Override
    public List<PreviewIcon> getSelectIcons() {
        return this.getDefaultIcons();
    }

    @NotNull
    @Override
    public String toString() {
        return "Default";
    }
}
