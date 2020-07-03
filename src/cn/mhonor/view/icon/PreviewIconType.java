package cn.mhonor.view.icon;

import com.intellij.ui.components.JBLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author mhonor
 * @version 1.0
 */
public class PreviewIconType extends JPanel {

    public PreviewIconType(@NotNull IconType iconType) {
        this.setLayout(new BorderLayout());

        JBLabel iconTypeName = new JBLabel(iconType.toString());
        this.add(iconTypeName, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));

        JPanel defaultIconsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        List<PreviewIcon> defaultIcons = iconType.getSortDefaultIcons();
        defaultIcons.forEach(defaultIconsPanel::add);
        contentPanel.add(defaultIconsPanel);

        JPanel selectIconsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        List<PreviewIcon> selectIcons = iconType.getSortSelectIcons();
        selectIcons.forEach(selectIconsPanel::add);
        contentPanel.add(selectIconsPanel);

        this.add(contentPanel, BorderLayout.CENTER);
    }
}
