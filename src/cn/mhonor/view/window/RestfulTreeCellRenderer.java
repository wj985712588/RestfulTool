package cn.mhonor.view.window;

import cn.mhonor.beans.Request;
import cn.mhonor.view.icon.Icons;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author mhonor
 * @version 1.0
 */
public class RestfulTreeCellRenderer extends ColoredTreeCellRenderer {

    @Override
    public void customizeCellRenderer(
            @NotNull JTree tree, Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row, boolean hasFocus) {
        Object obj = ((DefaultMutableTreeNode) value).getUserObject();
        if (obj instanceof Request) {
            Request node = (Request) obj;

            setMethodTypeAndPath(node, selected);
        } else if (obj instanceof String) {
            append((String) obj, SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES);
        } else if (obj instanceof Integer) {
            setIcon(Icons.SERVICE);
            append(String.format("Find %s services", obj));
        }
    }

    private void setMethodTypeAndPath(@Nullable Request node, boolean selected) {
        if (node == null) {
            return;
        }
        if (selected) {
            setIcon(node.getSelectIcon());
        } else {
            setIcon(node.getIcon());
        }
        append(node.getPath());
    }
}
