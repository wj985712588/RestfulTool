package cn.mhonor.utils.convert;

import cn.hutool.json.JSONObject;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author mhonor
 * @version 1.0
 */
public class JsonConvert extends BaseConvert<Object> {

    public JsonConvert() {
    }

    public JsonConvert(@NotNull PsiMethod psiMethod) {
        super(psiMethod);
    }

    @Override
    public String formatString() {
        Map<String, Object> methodParams = parseMethodParams();
        return new JSONObject(methodParams).toStringPretty();
    }

    @Override
    public Map<String, Object> formatMap(@NotNull String paramsStr) {
        return new JSONObject(paramsStr);
    }
}
