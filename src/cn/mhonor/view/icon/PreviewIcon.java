package cn.mhonor.view.icon;

import com.intellij.ui.components.JBLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author mhonor
 * @version 1.0
 */
public class PreviewIcon extends JBLabel {

    public PreviewIcon(@NotNull String text, @NotNull Icon icon) {
        super(text, icon, CENTER);
    }
}
