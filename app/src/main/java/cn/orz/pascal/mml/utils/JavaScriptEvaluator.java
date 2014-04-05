package cn.orz.pascal.mml.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

public class JavaScriptEvaluator {
    public JavaScriptEvaluator() {
    }

    public String eval(String script) {
        String result = null;
        Context context = Context.enter();
        context.setOptimizationLevel(-1); // use interpreter mode
        try {
            Scriptable globalScope = context.initStandardObjects();
            try {
                result = Context.toString(context.evaluateString(globalScope, script,
                        "<ColasScript>", 1, null));
            } catch (RhinoException jse) {
                result = jse.toString();
            }
        } finally {
            Context.exit();
        }
       return result;
    }
}