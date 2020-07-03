package cn.mhonor.view.search;

import cn.mhonor.beans.Request;
import cn.mhonor.utils.RestUtil;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhonor
 * @version 1.0
 */
public class GotoRequestContributor implements ChooseByNameContributor {

    private final Module module;

    private List<RestServiceItem> itemList;

    public GotoRequestContributor(Module module) {
        this.module = module;
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<String> names;

        List<Request> requests;
        if (includeNonProjectItems && module != null) {
            requests = RestUtil.getAllRequestByModule(project, module);
        } else {
            requests = new ArrayList<>();
            RestUtil.getAllRequest(project).forEach((s, rs) -> requests.addAll(rs));
        }
        names = new ArrayList<>(requests.size());

        itemList = new ArrayList<>(requests.size());
        requests.stream().map(request -> new RestServiceItem(
                request.getPsiMethod(),
                request.getMethod(),
                request.getPath()
        )).forEach(restServiceItem -> {
            names.add(restServiceItem.getName());
            itemList.add(restServiceItem);
        });

        return names.toArray(new String[0]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<NavigationItem> list = new ArrayList<>();
        itemList.stream()
                .filter(item -> item.getName() != null && item.getName().equals(name))
                .forEach(list::add);

        return list.toArray(new NavigationItem[0]);
    }
}
