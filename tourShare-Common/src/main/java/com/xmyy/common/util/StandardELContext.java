/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.el.*;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author zlp
 */
public class StandardELContext extends ELContext {

    private static final Logger logger = LoggerFactory.getLogger(StandardELContext.class);

    private static ExpressionFactory factory = ExpressionFactory.newInstance();

    protected static class ModelMap extends HashMap<String, Object> {
    }

    private final CompositeELResolver compositeELResolver = new CompositeELResolver();
    private final ELResolver modelResolver;

    private FunctionMapper functionResolver = new FunctionResolver(null, null);
    private VariableMapper variableResolver = new VariableResolver(null, null);

    public StandardELContext() {
        this.modelResolver = new ModelResolver(null);
        compositeELResolver.add(this.modelResolver);
        compositeELResolver.add(new MapELResolver());
        compositeELResolver.add(new ListELResolver());
        compositeELResolver.add(new ArrayELResolver());
        compositeELResolver.add(new BeanELResolver());
    }

    public StandardELContext(ELResolver modelResolver) {
        if (modelResolver == null) {
            this.modelResolver = new ModelResolver(null);
        } else {
            this.modelResolver = modelResolver;
        }
        compositeELResolver.add(this.modelResolver);
        compositeELResolver.add(new MapELResolver());
        compositeELResolver.add(new ListELResolver());
        compositeELResolver.add(new ArrayELResolver());
        compositeELResolver.add(new BeanELResolver());
    }

    public StandardELContext(Map<String, ?> variables) {
        this((ELResolver) null);
        ((ModelResolver) modelResolver).modelMap.putAll(variables);
    }

    @Override
    public ELResolver getELResolver() {
        return compositeELResolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return functionResolver;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variableResolver;
    }

    public void setFunctionResolver(FunctionMapper functionResolver) {
        this.functionResolver = functionResolver;
    }

    public void setVariableResolver(VariableMapper variableResolver) {
        this.variableResolver = variableResolver;
    }

    public static class FunctionResolver extends FunctionMapper {

        private final Map<String, Method> functionMap;
        private final List<String> functions;

        public FunctionResolver(Map<String, Method> functionMap, List<String> functions) {
            if (functionMap == null) {
                this.functionMap = new HashMap<String, Method>();
            } else {
                this.functionMap = functionMap;
            }
            if (functions == null) {
                this.functions = new ArrayList<String>();
            } else {
                this.functions = functions;
            }
        }

        @Override
        public Method resolveFunction(String prefix, String localName) {
            String key = prefix + ":" + localName;
            if (!functions.contains(key)) {
                synchronized (functions) {
                    if (!functions.contains(key)) {
                        functions.add(key);
                    }
                }
            }
            return functionMap.get(prefix + ":" + localName);
        }
    }

    public static class VariableResolver extends VariableMapper {

        private final Map<String, ValueExpression> variableMap;
        private final List<String> variables;

        public VariableResolver(Map<String, ValueExpression> variableMap, List<String> variables) {
            if (variableMap == null) {
                this.variableMap = new HashMap<String, ValueExpression>();
            } else {
                this.variableMap = variableMap;
            }
            if (variables == null) {
                this.variables = new ArrayList<String>();
            } else {
                this.variables = variables;
            }
        }

        @Override
        public ValueExpression resolveVariable(String variable) {
            if (!variables.contains(variable)) {
                synchronized (variables) {
                    if (!variables.contains(variable)) {
                        variables.add(variable);
                    }
                }
            }
            return variableMap.get(variable);
        }

        @Override
        public ValueExpression setVariable(String variable, ValueExpression expression) {
            return variableMap.put(variable, expression);
        }
    }

    private static class NotSureModelMap extends ModelMap {

        String prefix;
        final boolean throwExceptionIfPropertyNotFound;

        public NotSureModelMap(boolean throwExceptionIfPropertyNotFound) {
            this.throwExceptionIfPropertyNotFound = throwExceptionIfPropertyNotFound;
        }

        @Override
        public String toString() {
            Object obj = this.get(prefix);
            if (obj == null) {
                if (throwExceptionIfPropertyNotFound) {
                    throw new PropertyNotFoundException(prefix);
                }
                return null;
            }
            return obj.toString();
        }

    }

    private static class ModelResolver extends ELResolver {

        private final ModelMap modelMap;
        private boolean throwExceptionIfPropertyNotFound = true;

        public ModelResolver(ModelMap modelMap) {
            if (modelMap == null) {
                this.modelMap = new ModelMap();
            } else {
                this.modelMap = modelMap;
            }
        }

        public void setThrowExceptionIfPropertyNotFound(boolean throwExceptionIfPropertyNotFound) {
            this.throwExceptionIfPropertyNotFound = throwExceptionIfPropertyNotFound;
        }

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            base = (base == null) ? modelMap : base;
            if (base instanceof ModelMap) {
                context.setPropertyResolved(true);
                NotSureModelMap newmap = new NotSureModelMap(throwExceptionIfPropertyNotFound);
                String key = (base instanceof NotSureModelMap) ? (((NotSureModelMap) base).prefix + "." + property.toString()) : property.toString();
                for (Map.Entry<String, Object> entry : ((ModelMap) base).entrySet()) {
                    if (entry.getKey().equals(key)) {
                        if (!(base instanceof NotSureModelMap)) {
                            return entry.getValue();
                        }
                        newmap.put(entry.getKey(), entry.getValue());
                    } else if (entry.getKey().startsWith(key + ".")) {
                        newmap.put(entry.getKey(), entry.getValue());
                    }
                }
                if (!newmap.isEmpty()) {
                    newmap.prefix = key;
                    return newmap;
                }
                context.setPropertyResolved(false);
                return null;
            }
            return null;
        }

        @Override
        public Class<?> getType(ELContext context, Object base, Object property) {
            Object obj = getValue(context, base, property);
            return (obj == null ? null : obj.getClass());
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {
            base = (base == null) ? modelMap : base;
            if (base instanceof ModelMap) {
                context.setPropertyResolved(true);
                ((ModelMap) base).put(property.toString(), value);
            }
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            base = (base == null) ? modelMap : base;
            if (base instanceof ModelMap) {
                context.setPropertyResolved(true);
            }
            return false;
        }

        @Override
        public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
            base = (base == null) ? modelMap : base;
            if (base instanceof ModelMap) {
                Iterator<?> itr = ((ModelMap) base).keySet().iterator();
                ArrayList<FeatureDescriptor> feats = new ArrayList<FeatureDescriptor>();
                Object key;
                FeatureDescriptor desc;
                while (itr.hasNext()) {
                    key = itr.next();
                    desc = new FeatureDescriptor();
                    desc.setDisplayName(key.toString());
                    desc.setExpert(false);
                    desc.setHidden(false);
                    desc.setName(key.toString());
                    desc.setPreferred(true);
                    desc.setValue(RESOLVABLE_AT_DESIGN_TIME, Boolean.FALSE);
                    desc.setValue(TYPE, key.getClass());
                    feats.add(desc);
                }
                return feats.iterator();
            }
            return null;
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext context, Object base) {
            if (base instanceof ModelMap) {
                return Object.class;
            }
            return null;
        }
    }

    private Map<String, Method> getFunctionMap() {
        if (!(functionResolver instanceof FunctionResolver)) {
            throw new IllegalArgumentException();
        }
        return ((FunctionResolver) functionResolver).functionMap;
    }

    private Map<String, ValueExpression> getVariableMap() {
        if (!(variableResolver instanceof VariableResolver)) {
            throw new IllegalArgumentException();
        }
        return ((VariableResolver) variableResolver).variableMap;
    }

    private ModelMap getModelMap() {
        if (!(modelResolver instanceof ModelResolver)) {
            throw new IllegalArgumentException();
        }
        return ((ModelResolver) modelResolver).modelMap;
    }

    public void setThrowExceptionIfPropertyNotFound(boolean throwExceptionIfPropertyNotFound) {
        if (!(modelResolver instanceof ModelResolver)) {
            throw new IllegalArgumentException();
        }
        ((ModelResolver) modelResolver).setThrowExceptionIfPropertyNotFound(throwExceptionIfPropertyNotFound);
    }

    public void setFunction(String prefix, Method method) {
        setFunction(prefix, method.getName(), method);
    }

    public void setFunction(String prefix, String localName, Method method) {
        Map<String, Method> functionMap = getFunctionMap();
        functionMap.put(prefix + ":" + localName, method);
    }

    public void setFunctions(String prefix, Class clazz) {
        Map<String, Method> functionMap = getFunctionMap();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            functionMap.put(prefix + ":" + method.getName(), method);
        }
    }

    public void setVariable(String variable, ValueExpression expression) {
        Map<String, ValueExpression> variableMap = getVariableMap();
        variableMap.put(variable, expression);
    }

    public ValueExpression createValueExpression(String expression) {
        try {
            return factory.createValueExpression(this, expression, Object.class);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public boolean containsKey(String key) {
        ModelMap modelMap = getModelMap();
        boolean result = modelMap.containsKey(key);
        if (!result) {
            key += ".";
            for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
                if (entry.getKey().startsWith(key)) {
                    return true;
                }
            }
        }
        return result;
    }

    public void put(String name, Object value) {
        ModelMap modelMap = getModelMap();
        modelMap.put(name, value);
    }

    public void putAll(Map<String, ?> map) {
        ModelMap modelMap = getModelMap();
        modelMap.putAll(map);
    }

    public void putAll(Properties properties) {
        ModelMap modelMap = getModelMap();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            modelMap.put(entry.getKey().toString(), entry.getValue());
        }
    }

    public void put(Object... params) {
        ModelMap modelMap = getModelMap();
        if (params.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < params.length; i += 2) {
            modelMap.put(params[i].toString(), params[i + 1]);
        }
    }

    public Object getValue(String expression) {
        ValueExpression valueExpression = factory.createValueExpression(this, expression, Object.class);
        Object obj = valueExpression.getValue(this);
        return obj;
    }

    public Object getValue(ValueExpression expression) {
        Object obj = expression.getValue(this);
        return obj;
    }

    public static Object getValue(ValueExpression expression, Map<String, ?> map) {
        StandardELContext ctx = new StandardELContext(map);
        try {
            return expression.getValue(ctx);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ELException(ex);
        }
    }

    public static Object getValue(ValueExpression expression, Object... params) {
        if (params.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        StandardELContext ctx = new StandardELContext();
        for (int i = 0; i < params.length; i += 2) {
            ctx.put(params[i].toString(), params[i + 1]);
        }
        try {
            return expression.getValue(ctx);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ELException(ex);
        }
    }

    public static Object getValue(String expression, Map<String, ?> modelMap) {
        StandardELContext ctx = new StandardELContext(modelMap);
        try {
            return ctx.getValue(expression);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ELException(ex);
        }
    }

    public static Object getValue(String expression, Object... params) {
        StandardELContext ctx = new StandardELContext();
        ctx.put(params);
        try {
            return ctx.getValue(expression);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new ELException(ex);
        }
    }

    public static class ValueExpressionWithVariables {

        public ValueExpression valueExpression;
        public String[] variables;
    }

    public static ValueExpressionWithVariables checkExpression(String expression) {
        StandardELContext ctx = new StandardELContext();
        try {
            ValueExpression valueExpression = factory.createValueExpression(ctx, expression, Object.class);
            ValueExpressionWithVariables expressionWithVariables = new ValueExpressionWithVariables();
            expressionWithVariables.valueExpression = valueExpression;
            expressionWithVariables.variables = ((VariableResolver) ctx.variableResolver).variables.toArray(new String[0]);
            return expressionWithVariables;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

}
